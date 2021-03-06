package me.dkflab.duels;

import me.dkflab.duels.managers.MessageManager;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Commands implements CommandExecutor {
    private Duels plugin;
    public Commands(Duels i) {
        plugin = i;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("duels")) {
            if (sender instanceof Player) {
                Player p = (Player) sender;
                if (args.length == 0) {
                    MessageManager.error(sender, "Incorrect usage. /duels <join|leave>");
                    return true;
                }
                if (args[0].equalsIgnoreCase("join")) {
                    if (Duels.p1!=null&&Duels.p2!=null) {
                        MessageManager.error(p, "The arena is full at the moment!");
                        return true;
                    }
                    if (p == Duels.p1||p == Duels.p2) {
                        MessageManager.error(p, "You are already in the queue!");
                        return true;
                    }
                    // check if someone is in queue
                    if (Duels.p1 == null) {
                        Duels.p1 = p;
                        MessageManager.success(sender, "Put in queue! Waiting for a challenger.");
                        plugin.startMessage();
                    } else {
                        Duels.p2 = p;
                        plugin.startDuel();
                    }
                    return true;
                }
                if (args[0].equalsIgnoreCase("leave")) {
                    if (Duels.p1==p) {
                        if (Duels.started) {
                            MessageManager.error(sender, "You cannot quit when in-game!");
                            return true;
                        }
                        Duels.streak.put(p, null);
                        p.teleport(new Location(Duels.p1.getWorld(), -9.5,72,266.5));
                        MessageManager.success(sender, "Removed from queue.");
                        Duels.p1 = null;
                        if (Duels.p2 != null) {
                            Duels.p1 = Duels.p2;
                            Duels.p2 = null;
                        }
                        return true;
                    }
                    if (Duels.p2==p) {
                        if (Duels.started) {
                            MessageManager.error(sender, "You cannot quit when in-game!");
                            return true;
                        }
                        Duels.p2 = null;
                        Duels.streak.put(p, null);
                        p.teleport(new Location(Duels.p1.getWorld(), -9.5,72,266.5));
                        MessageManager.success(p, "Removed from queue.");
                        return true;
                    }
                    MessageManager.error(p, "You aren't in the queue!");
                    return true;
                }
                if (args[0].equalsIgnoreCase("help")) {
                    MessageManager.info(sender, "Duels Help");
                    MessageManager.info(sender, "/duels join - Join the queue");
                    MessageManager.info(sender, "/duels leave - Leave the queue");
                    return true;
                }
                MessageManager.error(sender, "Incorrect usage. /duels <join|leave>");
            } else {
                MessageManager.error(sender, "You must be a player to run this command.");
            }
        }
        return true;
    }
}
