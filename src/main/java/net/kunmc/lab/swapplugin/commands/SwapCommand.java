package net.kunmc.lab.swapplugin.commands;

import net.kunmc.lab.swapplugin.SwapPlugin;
import net.kunmc.lab.swapplugin.timers.PluginTimer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class SwapCommand implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("swapplugin.swap")) {
            sender.sendMessage(ChatColor.RED + "権限がないお^^");
            return true;
        }
        if (args.length == 0) {
            sender.sendMessage(ChatColor.RED + "引数が間違ってるンゴね～^^");
            return true;
        }

        switch (args[0].toLowerCase()) {
            case "start":
                return onStart(sender, Arrays.copyOfRange(args, 1, args.length));
            case "interval":
                return onInterval(sender, Arrays.copyOfRange(args, 1, args.length));
            case "end":
                return onEnd(sender, Arrays.copyOfRange(args, 1, args.length));
            default:
                sender.sendMessage(ChatColor.RED + "引数が間違ってるンゴね～^^");
                return true;
        }
    }

    private boolean onStart(CommandSender sender, String[] args) {
        if (args.length == 0) {
            sender.sendMessage(ChatColor.RED + "引数が違うンゴね～^^  使用方法：/swap start <interval(数字)>");
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
            sender.sendMessage(ChatColor.RED + "引数が違うンゴね～^^  使用方法：/swap start <interval(数字)>");
            return true;
        }

        Bukkit.getOnlinePlayers().forEach(p -> p.sendMessage(
                ChatColor.GREEN + "==================================================\n" +
                ChatColor.WHITE + "スワップカウントダウンが開始されました\n" +
                ChatColor.GOLD + interval + ChatColor.WHITE + "秒後にスワップが実行されるンゴ～\n" +
                ChatColor.GREEN + "=================================================="
        ));

        SwapPlugin.timer = new PluginTimer(interval);
        SwapPlugin.timer.runTaskTimer(SwapPlugin.instance, 0, 1);

        return true;
    }

    private boolean onInterval(CommandSender sender, String[] args) {
        if (args.length == 0) {
            sender.sendMessage(ChatColor.RED + "引数が違うンゴね～^^　使用方法：/swap interval <interval(数字)>");
            return true;
        }
        if (SwapPlugin.timer == null) {
            sender.sendMessage(ChatColor.RED + "まだ開始されていません。開始するには「/swap start」と入力してください。");
            return true;
        }

        int interval;
        try {
            interval = Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
            sender.sendMessage(ChatColor.RED + "引数が違うンゴね～^^　使用方法：/swap interval <interval(数字)>");
            return true;
        }

        SwapPlugin.timer.setInterval(interval);
        Bukkit.getOnlinePlayers().forEach(p -> p.sendMessage(
                ChatColor.WHITE + "スワップの間隔が" + ChatColor.GOLD + interval + ChatColor.WHITE + "秒に変更されました"));

        return true;
    }

    private boolean onEnd(CommandSender sender, String[] args) {
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
        List<String> result;

        switch (args.length) {
            case 1:
                result = Arrays.asList("start", "interval", "end");
                break;
            case 2:
                switch (args[0].toLowerCase()) {
                    case "start":
                    case "interval":
                        return Collections.singletonList("<interval(数字)>");
                    default:
                        return Collections.emptyList();
                }
            default:
                return Collections.emptyList();
        }

        String last = args[args.length - 1].toLowerCase();
        return result.stream()
                .filter(opt -> opt.startsWith(last))
                .collect(Collectors.toList());
    }

}
