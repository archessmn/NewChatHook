package com.github.archessmn.NewChatHook.Commands;

import com.github.archessmn.NewChatHook.Storage.Players;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.*;
import net.md_5.bungee.chat.ComponentSerializer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class CommandLink implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        Player player = (Player) sender;
        String linkingID = generateLinkingID(player.getUniqueId());

        Players.get().addDefault(player.getUniqueId() + ".linkingID", linkingID);

        TextComponent msg = new TextComponent(ChatColor.GOLD + linkingID);
        msg.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(ChatColor.GRAY
                + "Run " + ChatColor.LIGHT_PURPLE + "/link " + linkingID + ChatColor.GRAY + " in one of the discord channels for this server to link your accounts.\n" +
                "Click to copy the command.").create()));
        msg.setClickEvent(new ClickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD, "/link " + linkingID));

        /*BaseComponent[] msg = ComponentSerializer.parse("{'text':'/link "+linkingID+", 'color':'gold', 'clickEvent':{" +
                "'action':'copy_to_clipboard', 'value':'/link "+linkingID+"' +"+
                "} 'hoverEvent':{'action':'show_text', 'value':{'text':'Copy to clipboard', 'color':'light_purple'}}}");
        player.sendMessage(ChatColor.GREEN + "✓ " + ChatColor.GRAY + "| Generated your link id:" + msg);*/


        player.sendMessage(ChatColor.GREEN +""+ ChatColor.BOLD + "✓ " + ChatColor.GRAY + "| Generated your link id:");
        player.spigot().sendMessage(msg);
        Players.save();
        return true;
    }

    public String generateLinkingID(UUID uuid) {
        StringBuilder linkingID = new StringBuilder();
        String characters = "abcdefghijklmnopqrstuvwxyz1234567890";
        int pos = 0;
        for (int i = 0; i < 9; i++) {
            pos = characters.indexOf(uuid.toString().charAt(i));
            pos = (pos + 8);
            while (pos > 35) {
                pos = pos - 35;
            }
            linkingID.append(characters.charAt(pos));
        }

        return linkingID.toString();
    }
}
