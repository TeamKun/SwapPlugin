package net.kunmc.lab.swapplugin.timers;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

public class PluginTimer extends BukkitRunnable {
    private long startTime; // ミリ秒
    private int interval; // 周期（秒）
    private List<Player> players;

    public PluginTimer(int interval) {
        setInterval(interval);
        init();
    }

    @Override
    public void run() {
        // 経過時間（秒）
        int elapsedTime = (int) ((System.currentTimeMillis() - startTime) / 1000L);
        // 残り時間（秒）
        int remainingTime = interval - elapsedTime;

        // ぽっぽ～( ^)o(^ )
        if (remainingTime < 0) {
            teleport();
            init();
        }
        // 十秒前
        else if (remainingTime < 10) {
            if (players == null) {
                players = new ArrayList<>(Bukkit.getOnlinePlayers());
                Collections.shuffle(players);
            }
            notice(remainingTime);
        }

    }

    public void setInterval(int interval) {
        if (interval < 0) {
            interval = 120;
        }
        this.interval = interval;
    }

    private void init() {
        startTime = System.currentTimeMillis();
        players = null;
    }

    private void teleport() {
        Vector<Location> locations = new Vector<>();

        players.forEach(p -> locations.add(p.getLocation()));

        for (int i = 0; i < players.size(); i++) {
            players.get(i).teleport(locations.get(getTargetIndex(i)));
        }
    }

    private void notice(int remainingTime) {
        for (int i = 0; i < players.size(); i++) {
            players.get(i).sendTitle("",
                    "残り" + ChatColor.GOLD + remainingTime + ChatColor.WHITE + "秒で" + ChatColor.GREEN + players.get(getTargetIndex(i)).getName() + ChatColor.WHITE + "にTPします。",
                    2, 0, 5);
        }
    }

    private int getTargetIndex(int index) {
        if (index + 1 >= players.size()) {
            return 0;
        }

        return index + 1;
    }

}
