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
    List<Player> players;
    Vector<Pair> pairs;

    @Override
    public void run() {
        players = new ArrayList<>(Bukkit.getOnlinePlayers());

        Collections.shuffle(players);


    }

    private static class Pair {
        final private Player a;
        final private Player b;

        public Pair(Player a, Player b) {
            this.a = a;
            this.b = b;
        }

        public void swap() {
            Location locA = a.getLocation();
            Location locB = b.getLocation();

            a.teleport(locB);
            b.teleport(locA);
        }

        public void showText() {

        }
    }

}
