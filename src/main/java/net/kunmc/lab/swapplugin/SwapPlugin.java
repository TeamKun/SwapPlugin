package net.kunmc.lab.swapplugin;

import net.kunmc.lab.swapplugin.commands.SwapCommand;
import net.kunmc.lab.swapplugin.timers.PluginTimer;
import org.bukkit.plugin.java.JavaPlugin;

public final class SwapPlugin extends JavaPlugin {
    public static SwapPlugin instance;
    public static PluginTimer timer;

    @Override
    public void onEnable() {
        instance = this;

        SwapCommand command = new SwapCommand();
        getServer().getPluginCommand("swap").setExecutor(command);
        getServer().getPluginCommand("swap").setTabCompleter(command);
    }

    @Override
    public void onDisable() {
    }
}
