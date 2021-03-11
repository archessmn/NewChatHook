package com.github.archessmn.NewChatHook.Events;

import com.github.archessmn.NewChatHook.Main;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.channel.TextChannel;

public class PlayerChat implements Listener {
    public static Main plugin;

    public PlayerChat(Main instance) {
        plugin = instance;
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        DiscordApi api = plugin.getDiscordApi();

        TextChannel channel = api.getTextChannelById(plugin.getConfig().getString("DiscordBot.ChatChannel")).orElse(null);

        //event.setFormat(player.getDisplayName() + ": " + event.getMessage());
        assert channel != null;
        channel.sendMessage("<" + player.getDisplayName() + "> " + event.getMessage());
    }
}
