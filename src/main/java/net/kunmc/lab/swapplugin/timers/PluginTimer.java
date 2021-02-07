package net.kunmc.lab.swapplugin.timers;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.Vector;
import java.util.stream.Collectors;

public class PluginTimer extends BukkitRunnable {
    private long startTime; // ミリ秒
    private int interval; // 周期（秒）
    private List<UUID> players;

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
            teleport();
            init();
        }
        // 十秒前
        else if (remainingTime < 10) {
            notice(remainingTime);
        }

    }

    public void changeInterval(int interval) {
        if (interval < 0) {
            interval = 120;
        }
        this.interval = interval;
    }

    private void init() {
        players = Bukkit.getOnlinePlayers().stream().map(Player::getUniqueId).collect(Collectors.toList());
        Collections.shuffle(players);
        startTime = System.currentTimeMillis();
    }

    private void teleport() {
        Vector<Location> locations = new Vector<>();

        players.stream().map(Bukkit::getPlayer).filter(Objects::nonNull).forEach(p -> locations.add(p.getLocation()));

        for (int i = 0; i < players.size(); i++) {
            Player player = Bukkit.getPlayer(players.get(i));
            if (player == null)
                continue;
            player.teleport(locations.get(getTargetIndex(i)));
        }
    }

    private void notice(int remainingTime) {
        for (int i = 0; i < players.size(); i++) {
            Player player = Bukkit.getPlayer(players.get(i));
            if (player == null)
                continue;
            player.sendMessage("残り" + remainingTime + "秒で" + players.get(getTargetIndex(i)) + "にTPします。");
        }
    }

    private int getTargetIndex(int index) {
        if (index + 1 >= players.size()) {
            return 0;
        }

        return index + 1;
    }

}
