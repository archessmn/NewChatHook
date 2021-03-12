package com.github.archessmn.NewChatHook;

import com.github.archessmn.NewChatHook.Storage.Players;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.Locale;
import java.util.UUID;

public class Util {
    private static Main plugin;
    public Util(Main instance) {
        plugin = instance;
    }
    public static Boolean isDiscordCommand(String inargs) {
        String[] args = inargs.split(" ");
        return args[0].startsWith("/");
    }

    public static String getDiscordCommand(String inargs) {
        String[] args = inargs.split(" ");
        if (args[0].startsWith("/")) {
            String command = args[0];
            command = command.toLowerCase();
            return command.replaceFirst("/", "");
        } else {
            return "null";
        }
    }

    public static void playerDataSetup(Player player) {
        FileConfiguration pl = Players.get();
        UUID uuid = player.getUniqueId();
        if (pl.get(String.valueOf(uuid)) != null) {

            pl.addDefault(uuid + ".name", player.getName());
            pl.addDefault(uuid + ".discordID", null);
            pl.addDefault(uuid + ".linkingID", null);
        }
    }
}
