package me.dkflab.duels.managers;

import me.dkflab.duels.utils.Utils;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class MessageManager {

    public static void error(CommandSender sender, String error) {
        sender.sendMessage(Utils.text("&f&l[ERROR] &r&c" + error));
    }

    public static void success(CommandSender sender, String msg) {
        sender.sendMessage(Utils.text("&f&l[Duels] &r&a" + msg));
    }

    public static void info(CommandSender sender, String msg) {
        sender.sendMessage(Utils.text("&f&l[Duels] &r&b" + msg));
    }
}
