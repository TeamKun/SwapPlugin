package net.kunmc.lab.swapplugin.timers;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

public class PluginTimer extends BukkitRunnable {
    private final long startTime; // millisecond
    private List<Player> players;
    private int interval; // second

    public PluginTimer(int interval) {
        startTime = System.currentTimeMillis();
        this.interval = interval;
    }

    @Override
    public void run() {

        players = new ArrayList<>(Bukkit.getOnlinePlayers());

        Collections.shuffle(players);
        players.forEach(
                p -> {
                    p.sendMessage("current time: " + System.currentTimeMillis());
                }
        );
    }

    public void changeInterval(int interval) {
        this.interval = interval;
    }

}
