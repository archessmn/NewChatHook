package com.github.archessmn.NewChatHook.DiscordBot.Commands;

import com.github.archessmn.NewChatHook.Main;
import com.github.archessmn.NewChatHook.Util;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.MessageAuthor;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

import java.util.Objects;
import java.util.Optional;

public class RegisterChatChannel {
    private static Main plugin;
    public RegisterChatChannel(Main instance) {
        plugin = instance;
    }
    public static class RegisterChatChannelCommand implements MessageCreateListener {
        @Override
        public void onMessageCreate(MessageCreateEvent event) {
            if (Util.isDiscordCommand(event.getMessageContent())) {
                String command = Util.getDiscordCommand(event.getMessageContent());
                if (event.getMessageContent().startsWith("/chat")) {
                    TextChannel channel = event.getChannel();
                    channel.sendMessage("Command recieved.");
                    Optional<Server> Oserver = event.getServer();

                    MessageAuthor author = event.getMessageAuthor();
                    User user = author.asUser().orElse(null);




                    if (Oserver.isPresent()) {
                        Server server = Oserver.get();
                        plugin.getConfig().addDefault("DiscordBot.Servers." + server.getIdAsString() + ".ChatChannel", channel.getIdAsString());
                        if (Objects.equals(plugin.getConfig().getString("DiscordBot.Servers." + server.getIdAsString() + ".ChatChannel"), channel.getIdAsString())) {
                            channel.sendMessage(":white_check_mark: Successfully registered <#"
                                    + channel.getIdAsString()
                                    + "> as the chat channel for this server.");
                        } else {
                            plugin.getConfig().set("DiscordBot.Servers." + server.getIdAsString() + ".ChatChannel", channel.getIdAsString());
                            channel.sendMessage(":white_check_mark: Replaced a previously registered channel with <#"
                                    + channel.getIdAsString()
                                    + "> as the chat channel for this server.");
                        }
                    } else {
                        channel.sendMessage(":octagonal_sign: This command cannot be run through direct messages.");
                    }
                    plugin.saveConfig();
                }
            }
        }
    }
}
