package com.github.archessmn.NewChatHook.Storage;

import com.github.archessmn.NewChatHook.Main;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class Players {
    Main plugin;

    public Players(Main instance) {
        plugin = instance;
    }

    private static File nameFile;
    private static FileConfiguration nameYml;

    public static void setup() {
        nameFile = new File(Bukkit.getServer().getPluginManager().getPlugin("NewChatHook").getDataFolder(), "players.yml");

        if (!nameFile.exists()) {
            try{
                nameFile.createNewFile();
            }catch (IOException e) {
                //oh no
            }
        }
        nameYml = YamlConfiguration.loadConfiguration(nameFile);
    }

    public static FileConfiguration get() {
        return nameYml;
    }

    public static void save() {
        try {
            nameYml.save(nameFile);
        }catch (IOException e) {
            System.out.println("Couldn't save players yml file!");
        }
    }

    public static void reload() {
        nameYml = YamlConfiguration.loadConfiguration(nameFile);
    }
}
