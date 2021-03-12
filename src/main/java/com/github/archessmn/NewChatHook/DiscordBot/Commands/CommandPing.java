package com.github.archessmn.NewChatHook.DiscordBot.Commands;

import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;

public class CommandPing {
    public void execute(MessageCreateEvent event) {
        TextChannel channel = event.getChannel();
        EmbedBuilder PingEmbed = new EmbedBuilder()
                .setTitle("Pong :ping_pong:");
        channel.sendMessage(PingEmbed);
    }
}