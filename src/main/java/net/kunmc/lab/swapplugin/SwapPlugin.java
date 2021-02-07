package net.kunmc.lab.swapplugin;

import net.kunmc.lab.swapplugin.commands.SwapCommand;
import org.bukkit.plugin.java.JavaPlugin;

public final class SwapPlugin extends JavaPlugin {
    public static SwapPlugin instance;

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
