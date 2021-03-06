package me.dkflab.duels.listeners;

import me.dkflab.duels.Duels;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class Move implements Listener {

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        if (e.getPlayer() == Duels.p1 || e.getPlayer() == Duels.p2) {
            if (Duels.started&&Duels.countdown) {
                e.setCancelled(true);
            }
        }
    }
}
