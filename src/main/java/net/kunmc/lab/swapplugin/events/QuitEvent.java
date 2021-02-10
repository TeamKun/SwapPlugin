package net.kunmc.lab.swapplugin.events;

import net.kunmc.lab.swapplugin.SwapPlugin;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class QuitEvent implements Listener {

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        // スワップカウントダウンが開始してなければ関係ない。
        if (SwapPlugin.timer == null) {
            return;
        }

        SwapPlugin.timer.players.remove(event.getPlayer());
    }

}
