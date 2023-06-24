package de.sosig.sosigutils.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SetrankTabCompletion implements TabCompleter {
    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

        if (!(commandSender instanceof Player)) return null;
        Player p = (Player) commandSender;
        List<String> args = new ArrayList<>();

        if (strings.length == 1){
            for (Player targ : Bukkit.getOnlinePlayers()){
                args.add(targ.getName());
            }
            return args;
        }
        else if (strings.length == 2){
            //Bukkit.broadcastMessage("'" + strings[0] + "'" + "---" + "'" + strings[1] + "'");
            args.add("owner");
            args.add("admin");
            args.add("moderator");
            args.add("twitch");
            args.add("youtube");
            args.add("vip");
            args.add("player");
            return args;
        }
        return null;
        }
    }