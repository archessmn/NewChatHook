package com.github.archessmn.NewChatHook;

import net.byteflux.libby.BukkitLibraryManager;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.javacord.api.DiscordApi;

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

          //////////////////////////////////
         // Register Commands and Events //
        //////////////////////////////////
        PluginManager pm = getServer().getPluginManager();



          ////////////////////
         // Manage Storage //
        ////////////////////
        this.getConfig().addDefault("DiscordBot.TOKEN", null);
        this.reloadConfig();
        this.saveConfig();
    }

    @Override
    public void onDisable() {
        super.onDisable();
        getLogger().info("Disabling " + getName() + ".");
    }

    public void setDiscordApi(DiscordApi api) {
        this.api = api;
    }
}
