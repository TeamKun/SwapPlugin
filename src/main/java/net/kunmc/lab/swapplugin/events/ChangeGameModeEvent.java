package net.kunmc.lab.swapplugin.events;

import net.kunmc.lab.swapplugin.SwapPlugin;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerGameModeChangeEvent;

public class ChangeGameModeEvent implements Listener {

    @EventHandler
    public void onChangeGameMode(PlayerGameModeChangeEvent event) {
        // スワップカウントダウンが開始してなければ関係ない。
        if (SwapPlugin.timer == null) {
            return;
        }

        switch (event.getNewGameMode()) {
            case CREATIVE:
            case SPECTATOR:
                SwapPlugin.timer.players.remove(event.getPlayer());
        }
    }

}
