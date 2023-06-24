package de.sosig.sosigutils.commands;

import de.sosig.sosigutils.SosigUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class ChallengeCommandTabCompletion implements TabCompleter {
    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (!(commandSender instanceof Player)) return null;
        Player p = (Player) commandSender;
        List<String> args = new ArrayList<>();

        if (strings.length == 1){
            args.add("bossbar");
            return args;
        }
        else if (strings.length == 2 && strings[0].equals("bossbar")){
            args.add("show");
            args.add("hide");
            args.add("setname");
            args.add("setprogress");
            return args;
        }
        return null;
    }
}