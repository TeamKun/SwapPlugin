package net.kunmc.lab.swapplugin.commands;

import net.kunmc.lab.swapplugin.SwapPlugin;
import net.kunmc.lab.swapplugin.timers.PluginTimer;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SwapCommand implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            return true;
        }

        switch (args[0].toLowerCase()) {
            case "start":
                return onStart(sender, command, label, Arrays.copyOfRange(args, 1, args.length));
            case "interval":
                return onInterval(sender, command, label, Arrays.copyOfRange(args, 1, args.length));
            case "end":
                return onEnd(sender, command, label, Arrays.copyOfRange(args, 1, args.length));
            default:
                return false;
        }
    }

    private boolean onStart(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            sender.sendMessage(ChatColor.RED + "引数が違うンゴね～^^　使用方法：/swap start <interval(数字)>");
            return true;
        }
        if (SwapPlugin.timer != null) {
            sender.sendMessage(ChatColor.RED + "既に開始しています。終了するには「/swap end」と入力してください。");
            return true;
        }

        int interval;

        try {
            interval = Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
            sender.sendMessage(ChatColor.RED + "引数が違うンゴね～^^　使用方法：/swap start <interval(数字)>");
            return true;
        }

        SwapPlugin.timer = new PluginTimer(interval);
        SwapPlugin.timer.runTaskTimer(SwapPlugin.instance, 0, 20);

        return true;
    }

    private boolean onInterval(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            sender.sendMessage(ChatColor.RED + "引数が違うンゴね～^^　使用方法：/swap interval <interval(数字)>");
            return true;
        }
        if (SwapPlugin.timer == null) {
            sender.sendMessage(ChatColor.RED + "まだ開始されていません。開始するには「/swap start」と入力してください。");
            return true;
        }

        try {
            SwapPlugin.timer.changeInterval(Integer.parseInt(args[0]));
        } catch (NumberFormatException e) {
            sender.sendMessage(ChatColor.RED + "引数が違うンゴね～^^　使用方法：/swap interval <interval(数字)>");
            return true;
        }

        return true;
    }

    private boolean onEnd(CommandSender sender, Command command, String label, String[] args) {
        if (SwapPlugin.timer == null) {
            sender.sendMessage(ChatColor.RED + "既に終了しています。開始するには「/swap start」と入力してください。");
            return true;
        }

        SwapPlugin.timer.cancel();
        SwapPlugin.timer = null;

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        System.out.println(args.length);

        if (args.length == 1) {
            return Arrays.asList("start", "interval", "end");
        }

        if (args.length == 2) {
            switch (args[0].toLowerCase()) {
                case "start":
                case "interval":
                    return Collections.singletonList("<interval(数字)>");
            }
        }

        return null;
    }

}
