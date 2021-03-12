package com.github.archessmn.NewChatHook;

import com.github.archessmn.NewChatHook.DiscordBot.BotMain;
import com.github.archessmn.NewChatHook.DiscordBot.Commands.RegisterChatChannel;
import com.github.archessmn.NewChatHook.DiscordBot.Events.OtherListeners;
import com.github.archessmn.NewChatHook.Events.PlayerChat;
import com.github.archessmn.NewChatHook.Events.PlayerJoin;
import com.github.archessmn.NewChatHook.Events.PlayerQuit;
import com.github.archessmn.NewChatHook.Storage.Players;
import net.byteflux.libby.BukkitLibraryManager;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.embed.Embed;
import org.javacord.api.entity.message.embed.EmbedBuilder;

import java.awt.*;
import java.util.Optional;

public class Main extends JavaPlugin {

    private DiscordApi api;

    @Override
    public void onEnable() {
          ///////////////////////////
         // Download Dependencies //
        ///////////////////////////
        super.onEnable();
        BukkitLibraryManager manager = new BukkitLibraryManager(this);
        manager.addMavenCentral();
        manager.fromGeneratedResource(this.getResource("AzimDP.json")).forEach(library->{
            try {
                manager.loadLibrary(library);
            }catch(RuntimeException e) {
                getLogger().info("Skipping download of\""+library+"\", it either doesnt exist or has no .jar file");
            }
        });

          ////////////////////////
         // Manage Discord Bot //
        ////////////////////////
        new BotMain(this).setApi();



          //////////////////////////////////
         // Register Commands and Events //
        //////////////////////////////////
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new PlayerChat(this), this);
        pm.registerEvents(new PlayerJoin(this), this);
        pm.registerEvents(new PlayerQuit(this), this);



          ////////////////////
         // Manage Storage //
        ////////////////////
        this.getConfig().options().copyDefaults(true);
        this.getConfig().addDefault("DiscordBot.TOKEN", null);
        this.getConfig().addDefault("DiscordBot.InfoChannel", null);
        this.saveConfig();
        this.reloadConfig();

        Players.setup();
        Players.get().options().copyDefaults(true);
        Players.save();
        Players.reload();
    }

    @Override
    public void onDisable() {
        super.onDisable();
        getLogger().info("Disabling " + getName() + ".");
        new BotMain(this).onPluginDisable(api);
        api.disconnect();
    }

    public void setDiscordApi(DiscordApi api) {
        this.api = api;
        if (this.api == null) {
            getLogger().warning("§4The Discord API is null.");
        } else {
            Optional<TextChannel> OInfoChannel = api.getTextChannelById(this.getConfig().getString("DiscordBot.InfoChannel"));
            if (OInfoChannel.isPresent()) {
                TextChannel InfoChannel = OInfoChannel.get();
                EmbedBuilder StartEmbed = new EmbedBuilder()
                        .setTitle(":white_check_mark: Bot has started.")
                        .addField("Server:", getServer().getName() + ".")
                        .setAuthor(api.getYourself())
                        .setColor(Color.GREEN);
                InfoChannel.sendMessage(StartEmbed);
            }
        }
    }
    public DiscordApi getDiscordApi() { return this.api; }
}
