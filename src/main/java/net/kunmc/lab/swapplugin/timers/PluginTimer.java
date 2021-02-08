package net.kunmc.lab.swapplugin.timers;

import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Collections;
import java.util.List;
import java.util.Vector;
import java.util.stream.Collectors;

public class PluginTimer extends BukkitRunnable {
    private long startTime; // ミリ秒
    private int interval; // 周期（秒）
    private List<Player> players;
    private int prevRemainingTime;

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
        if (remainingTime <= 0) {
            teleport();
            init();
            return;
        }
        // 十秒前
        else if (remainingTime <= 10) {
            if (players == null) {
                players = Bukkit.getOnlinePlayers().stream().parallel()
                        .filter(p -> p.getGameMode() == GameMode.SURVIVAL)
                        .filter(p -> !p.isDead())
                        .collect(Collectors.toList());
                Collections.shuffle(players);
            }
            notice(remainingTime);
        }

        if (remainingTime <= 3) {
            if (prevRemainingTime == remainingTime) {
                return;
            }
            players.forEach(p -> p.playSound(p.getLocation(), Sound.BLOCK_BELL_USE, 1.0f, 1.0f));
            prevRemainingTime = remainingTime;
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
            Player player = players.get(i);

            player.teleport(locations.get(getNextIndex(i)));
            player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1.0f, 1.0f);
        }
    }

    private void notice(int remainingTime) {
        for (int i = 0; i < players.size(); i++) {
            players.get(i).sendTitle("",
                    "残り" + ChatColor.GOLD + remainingTime + ChatColor.WHITE + "秒で" + ChatColor.GREEN + players.get(getNextIndex(i)).getName() + ChatColor.WHITE + "にTPします。",
                    0, 10, 0);
            players.get(i).sendActionBar(ChatColor.GREEN + players.get(getPrevIndex(i)).getName() + ChatColor.WHITE + "がTPしてきます。");
        }
    }

    private int getNextIndex(int index) {
        if (index + 1 >= players.size()) {
            return 0;
        }

        return index + 1;
    }

    private int getPrevIndex(int index) {
        if (index == 0) {
            return players.size() - 1;
        }

        return index - 1;
    }

}
