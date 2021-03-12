package com.github.archessmn.NewChatHook.DiscordBot.Commands;

import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

public class CommandHandler {
    public static class OnCommand implements MessageCreateListener {
        //private static Main plugin;
        //public OnCommand(Main instance) {
        //    plugin = instance;
        //}
        @Override
        public void onMessageCreate(MessageCreateEvent event) {
            String message = event.getMessageContent();
            String[] args = message.split(" ");
            if (args[0].startsWith("/")) {
                String command = args[0];

                switch (command) {
                    case "/help":
                        new CommandHelp().execute(event);
                    case "/ping":
                        new CommandPing().execute(event);
                    default:
                        new CommandHelp().execute(event);
                }
            }
        }
    }
}
