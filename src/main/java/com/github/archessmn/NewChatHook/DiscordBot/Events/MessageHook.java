package com.github.archessmn.NewChatHook.DiscordBot.Events;

import com.github.archessmn.NewChatHook.Main;
import org.bukkit.configuration.file.FileConfiguration;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

public class MessageHook {
    private static Main plugin;
    public MessageHook(Main instance) {
        plugin = instance;
    }
    FileConfiguration config = plugin.getConfig();
    public static class Message implements MessageCreateListener {
        private static Main plugin;

        public Message(Main instance) {
            plugin = instance;
        }

        @Override
        public void onMessageCreate(MessageCreateEvent event) {
            if (event.getChannel().getIdAsString().equals(plugin.getConfig().getString("DiscordBot.ChatChannel"))) {
                User user = event.getMessageAuthor().asUser().orElse(null);
                Server server = event.getServer().orElse(null);
                assert user != null;
                assert server != null;
                if (!user.isBot()) {
                    plugin.getServer().broadcastMessage("<" + user.getDisplayName(server) + "> " + event.getMessageContent());
                }
            }
        }
    }
}
