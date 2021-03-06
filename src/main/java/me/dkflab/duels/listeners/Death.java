package me.dkflab.duels.listeners;

import me.dkflab.duels.Duels;
import me.dkflab.duels.managers.MessageManager;
import me.dkflab.duels.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class Death implements Listener {

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        Player player = e.getEntity();
        if (player == Duels.p1 || player == Duels.p2) {
            e.setCancelled(true);
            player.setHealth(20);
            Duels.started = false;
            if (player == Duels.p1) {
                // p1 lost
                win(Duels.p2, Duels.p1);
                Duels.streak.put(Duels.p1, null);
                Duels.p1 = null;
                Duels.p1 = Duels.p2;
                Duels.p2 = null;
            } else {
                // p1 won
                win(Duels.p1, Duels.p2);
                Duels.streak.put(Duels.p2, null);
                Duels.p2 = null;
            }
        }
    }

    private void win (Player winner, Player loser) {
        winner.sendTitle(Utils.text("&6&lYou Win!"), Utils.text("&7Good job!"), 20, 40, 20);
        loser.sendTitle(Utils.text("&c&lYou've lost ;("), Utils.text("&7Better luck next time."), 20, 40, 20);
        // STREAKS
        if (Duels.streak.get(winner) == null) {
            Duels.streak.put(winner, 1);
        } else {
            int streak = Duels.streak.get(winner);
            Duels.streak.put(winner, streak+1);
        }
        if (Duels.streak.get(winner) == 1) {
            Duels.econ.depositPlayer(winner,250);
        } else {
            Duels.econ.depositPlayer(winner,(Duels.streak.get(winner)*0.75)*250);
        }
        MessageManager.info(loser, "You've been removed from the arena automatically.");
        MessageManager.info(loser, "If you wish to rejoin, run /duels join");
        // teleport out of arena
        loser.teleport(new Location(loser.getWorld(), -9.5,72,266.5));
        // message about streak
        MessageManager.info(winner, "If you wish to leave the arena (losing your streak),");
        MessageManager.info(winner, "type /duels leave");
        for (Player all : Bukkit.getOnlinePlayers()) {
            MessageManager.info(all, winner.getName() + " is on a " + Duels.streak.get(winner) +" game win streak!");
        }
    }
}
