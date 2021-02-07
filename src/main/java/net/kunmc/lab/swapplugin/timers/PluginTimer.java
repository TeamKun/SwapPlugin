package net.kunmc.lab.swapplugin.timers;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

public class PluginTimer extends BukkitRunnable {
    private long startTime; // ミリ秒
    private Vector<Pair> pairs;
    private int interval; // 周期（秒）

    public PluginTimer(int interval) {
        if (interval < 0) {
            interval = 120;
        }
        this.interval = interval;
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
            pairs.forEach(Pair::teleport);
            init();
        }
        // 十秒前
        else if (remainingTime < 10) {
            pairs.forEach(pair -> pair.notice(remainingTime));
        }

    }

    public void changeInterval(int interval) {
        if (interval < 0) {
            interval = 120;
        }
        this.interval = interval;
    }

    private void init() {
        List<Player> players = new ArrayList<>(Bukkit.getOnlinePlayers());
        Collections.shuffle(players);

        pairs = new Vector<>();

        for (int i = 0; i < players.size(); i++) {
            Player dst;

            try {
                dst = players.get(i + 1);
            } catch (IndexOutOfBoundsException e) {
                dst = players.get(0);
            }

            Player src = players.get(i);

            pairs.add(new Pair(src, dst));
        }

        startTime = System.currentTimeMillis();
    }

    private static class Pair {
        Player src;
        Player dst;

        Pair(Player src, Player dst) {
            this.src = src;
            this.dst = dst;
        }

        public void teleport() {
            src.teleport(dst.getLocation());
        }

        public void notice(int time) {
            src.sendMessage("残り" + time + "秒で" + dst.getName() + "にTPします。");
        }

    }

}
