package com.github.archessmn.NewChatHook.Events;

import com.github.archessmn.NewChatHook.Main;
import com.github.archessmn.NewChatHook.Storage.Players;
import com.github.archessmn.NewChatHook.Util;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.channel.TextChannel;

import java.util.Objects;

public class PlayerJoin implements Listener {
    public static Main plugin;

    public PlayerJoin(Main instance) {
        plugin = instance;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        DiscordApi api = plugin.getDiscordApi();
        TextChannel channel = api.getTextChannelById(plugin.getConfig().getString("DiscordBot.ChatChannel")).orElse(null);
        assert channel != null;
        Objects.requireNonNull(channel.asServerTextChannel().orElse(null)).updateTopic("There are "
                + plugin.getServer().getOnlinePlayers().size()
                + "/" + plugin.getServer().getMaxPlayers() + " players online.");
        channel.sendMessage(event.getPlayer().getDisplayName() + " joined the game");

        if (!Objects.equals(Players.get().getString(event.getPlayer().getUniqueId() + ".mcName"), event.getPlayer().getName())) {
            Util.playerDataSetup(event.getPlayer());
        }


    }
}
