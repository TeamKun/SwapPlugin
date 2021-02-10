package net.kunmc.lab.swapplugin;

import net.kunmc.lab.swapplugin.commands.SwapCommand;
import net.kunmc.lab.swapplugin.events.ChangeGameModeEvent;
import net.kunmc.lab.swapplugin.events.DeathEvent;
import net.kunmc.lab.swapplugin.events.QuitEvent;
import net.kunmc.lab.swapplugin.timers.PluginTimer;
import org.bukkit.plugin.java.JavaPlugin;

public final class SwapPlugin extends JavaPlugin {
    public static SwapPlugin instance;
    public static PluginTimer timer;

    @Override
    @SuppressWarnings("all")
    public void onEnable() {
        instance = this;

        SwapCommand command = new SwapCommand();
        getServer().getPluginCommand("swap").setExecutor(command);
        getServer().getPluginCommand("swap").setTabCompleter(command);
        getServer().getPluginManager().registerEvents(new ChangeGameModeEvent(), this);
        getServer().getPluginManager().registerEvents(new DeathEvent(), this);
        getServer().getPluginManager().registerEvents(new QuitEvent(), this);
    }

    @Override
    public void onDisable() {
    }

}
