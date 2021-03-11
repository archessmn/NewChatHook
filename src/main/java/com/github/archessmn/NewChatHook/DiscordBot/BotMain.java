package com.github.archessmn.NewChatHook.DiscordBot;

import com.github.archessmn.NewChatHook.DiscordBot.Commands.RegisterChatChannel;
import com.github.archessmn.NewChatHook.DiscordBot.Events.MessageHook;
import com.github.archessmn.NewChatHook.DiscordBot.Events.OtherListeners;
import com.github.archessmn.NewChatHook.Main;
import org.bukkit.Bukkit;
import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.embed.EmbedBuilder;

import java.awt.*;
import java.util.Optional;

import static org.bukkit.Bukkit.getLogger;

public class BotMain {
    private DiscordApi api;
    public static Main plugin;

    public BotMain(Main instance) {
        plugin = instance;
    }

    public void setApi() {
        if (!(plugin.getConfig().getString("DiscordBot.TOKEN") == null)) {
            new DiscordApiBuilder()
                    .setToken(plugin.getConfig().getString("DiscordBot.TOKEN"))
                    .login()
                    .thenAccept(this::onConnectToDiscord)
                    .exceptionally(error -> {
                        plugin.getLogger().warning("Failed to connect to Discord! Disabling " + plugin.getName() + "!");
                        plugin.getLogger().warning(String.valueOf(error));
                        plugin.getPluginLoader().disablePlugin(plugin);
                        return null;
                    });
        } else {
            plugin.getLogger().warning("You must provide a Bot token for this plugin to work.");
        }
    }

    private void onConnectToDiscord(DiscordApi discordApi) {
        this.api = discordApi;
        plugin.setDiscordApi(discordApi);
        getLogger().info("Connected as " + api.getYourself().getDiscriminatedName());
        getLogger().info("Invite URL: " + api.createBotInvite());

        //api.addListener(new RegisterChatChannel.RegisterChatChannelCommand());
        //api.addListener(new OtherListeners.Listener());
        api.addListener(new MessageHook.Message(plugin));
    }

    public void onPluginDisable(DiscordApi api) {
        Optional<TextChannel> OInfoChannel = api.getTextChannelById(plugin.getConfig().getString("DiscordBot.InfoChannel"));
        if (OInfoChannel.isPresent()) {
            TextChannel InfoChannel = OInfoChannel.get();
            EmbedBuilder StartEmbed = new EmbedBuilder()
                    .setTitle(":octagonal_sign: Bot has stopped.")
                    .addField("Server:", "SMP")
                    .setAuthor(api.getYourself())
                    .setColor(Color.RED);
            InfoChannel.sendMessage(StartEmbed);
        }
        api.disconnect();
        getLogger().info("Disconnected bot.");
    }
}
