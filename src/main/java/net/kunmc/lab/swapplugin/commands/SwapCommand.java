package net.kunmc.lab.swapplugin.commands;

import net.kunmc.lab.swapplugin.SwapPlugin;
import net.kunmc.lab.swapplugin.timers.PluginTimer;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.List;

public class SwapCommand implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            return true;
        }

        switch (args[0].toLowerCase()) {
            case "start":
                sender.sendMessage("SWAP START");
                break;
            case "end":
                sender.sendMessage("SWAP END");
                break;
            default:
                return false;
        }

        long period;

        try {
            period = Long.parseLong(args[0]);
        } catch (NumberFormatException e) {
            sender.sendMessage(ChatColor.RED + "コマンドの引数が間違っているようです。使用方法：/swap start <period(数字)>");
            return true;
        }

        new PluginTimer().runTaskTimer(SwapPlugin.instance, 0, 20);

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return null;
    }

}
