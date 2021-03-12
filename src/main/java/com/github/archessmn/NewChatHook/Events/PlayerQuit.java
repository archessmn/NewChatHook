package com.github.archessmn.NewChatHook.Events;

import com.github.archessmn.NewChatHook.Main;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.channel.TextChannel;

import java.util.Objects;

public class PlayerQuit implements Listener {
    public static Main plugin;

    public PlayerQuit(Main instance) {
        plugin = instance;
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        DiscordApi api = plugin.getDiscordApi();
        TextChannel channel = api.getTextChannelById(plugin.getConfig().getString("DiscordBot.ChatChannel")).orElse(null);
        assert channel != null;
        Objects.requireNonNull(channel.asServerTextChannel().orElse(null)).updateTopic("There are "
                + (plugin.getServer().getOnlinePlayers().size() - 1)
                + "/" + plugin.getServer().getMaxPlayers() + " players online.");
        channel.sendMessage(event.getPlayer().getDisplayName() + " left the game");
    }
}
