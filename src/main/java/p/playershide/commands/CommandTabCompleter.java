package p.playershide.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;
public class CommandTabCompleter implements TabCompleter {
    private static final String[] COMMANDS = {"toggle", "help", "reload"};

    @Override
    public List<String> onTabComplete(CommandSender sender, Command mmd, String label, String[] args) {
        final List<String> completions = new ArrayList<>();

        if (args.length == 1) {
            StringUtil.copyPartialMatches(args[0], Arrays.asList(COMMANDS), completions);
        } else if (args.length == 2 && (args[0].equalsIgnoreCase("toggle"))) {
            for (Player p : Bukkit.getOnlinePlayers()) {
                completions.add(p.getName());
            }
        }
        Collections.sort(completions);
        return completions;
    }
}