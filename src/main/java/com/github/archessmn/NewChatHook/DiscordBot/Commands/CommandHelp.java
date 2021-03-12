package com.github.archessmn.NewChatHook.DiscordBot.Commands;

import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;

public class CommandHelp {
    public void execute(MessageCreateEvent event) {
        TextChannel channel = event.getChannel();
        EmbedBuilder HelpEmbed = new EmbedBuilder()
                .setTitle("Help")
                .addField("Status", "This command is being worked on, activate it using /help.");
        channel.sendMessage(HelpEmbed);
    }

}
