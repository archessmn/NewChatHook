package com.github.archessmn.NewChatHook.DiscordBot.Events;

import com.github.archessmn.NewChatHook.Main;
import org.javacord.api.event.channel.ChannelEvent;
import org.javacord.api.listener.channel.ChannelAttachableListener;

public class OtherListeners {
    private static Main plugin;
    public OtherListeners(Main instance) {
        plugin = instance;
    }
    public static class Listener implements ChannelAttachableListener {
        //@Override
        public void onServerListener(ChannelEvent event) {
            //ok
        }
    }
}
