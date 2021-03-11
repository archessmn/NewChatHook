package com.github.archessmn.NewChatHook;

import java.util.Locale;

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
}
