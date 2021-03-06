package me.dkflab.duels;

import me.dkflab.duels.listeners.Death;
import me.dkflab.duels.listeners.Move;
import me.dkflab.duels.managers.MessageManager;
import me.dkflab.duels.utils.Utils;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

public final class Duels extends JavaPlugin {

    public static Economy econ = null;
    @Override
    public void onEnable() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            System.out.println("CRITICAL ERROR: NO VAULT FOUND!");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        econ = getServer().getServicesManager().getRegistration(Economy.class).getProvider();
        //
        registerEvents();
        getCommand("duels").setExecutor(new Commands(this));
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    private void registerEvents() {
        getServer().getPluginManager().registerEvents(new Death(), this);
        getServer().getPluginManager().registerEvents(new Move(), this);
    }
    public static HashMap<Player, Integer> streak = new HashMap<>();
    public static Player p1;
    public static Player p2;
    private int taskID;
    public static boolean started = false;
    public static boolean countdown = false;
    public void startMessage() {
        this.taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
            @Override
            public void run() {
                if (p1!=null&&p2!=null) {
                    cancelTask(taskID);
                    // starting duel will be taken care of by Commands.java
                } else {
                    if (p1==null&&p2==null) {
                        cancelTask(taskID);
                        return;
                    }
                    for (Player all : Bukkit.getOnlinePlayers()) {
                        MessageManager.info(all, p1.getName() + " is awaiting a duel!");
                        MessageManager.info(all, "Challenge him with /duels join");
                    }
                }
            }
        }, 0L, 30*20L); //0 Tick initial delay, 20 Tick (1 Second) between repeats
    }

    private void cancelTask(int taskid) {
        Bukkit.getScheduler().cancelTask(taskid);
    }

    public void startDuel() {
        // tp to arena
        p1.teleport(new Location(p1.getWorld(), -9.5, 51, 292.5,0,0));
        p2.teleport(new Location(p2.getWorld(), -9.5, 51, 312.5, -180,0));
        countdown = true;
        started = true;
        title("&c3");
        Bukkit.getScheduler ().runTaskLater (this, () -> title("&e2"), 20);
        Bukkit.getScheduler ().runTaskLater (this, () -> title("&21"), 40);
        Bukkit.getScheduler ().runTaskLater (this, () -> title("&a&lGo!"), 60);
        Bukkit.getScheduler ().runTaskLater (this, () -> stopCD(), 60);
    }

    private void stopCD() {
        countdown = false;
    }

    private void title(String title) {
        title = Utils.text(title);
        p1.sendTitle(title, "", 0,20,0);
        p2.sendTitle(title, "", 0,20,0);
    }
}
