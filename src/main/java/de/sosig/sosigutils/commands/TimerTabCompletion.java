package de.sosig.sosigutils.commands;

import de.sosig.sosigutils.managers.RankManager;
import de.sosig.sosigutils.storage.Rank;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TimerTabCompletion implements TabCompleter {
    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

        if (!(commandSender instanceof Player)) return null;
        Player p = (Player) commandSender;

        List<String> args = new ArrayList<>();
        if (strings.length == 1) {
            args.add("start");
            args.add("stop");
            args.add("pause");
            args.add("resume");
            args.add("set");
            args.add("get");
            return args;
        }
        return null;
    }
}
