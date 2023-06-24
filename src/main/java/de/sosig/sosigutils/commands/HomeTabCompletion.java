package de.sosig.sosigutils.commands;

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
import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class HomeTabCompletion implements TabCompleter {
    File file = new File("plugins/SosigUtil", "homes.yml");
    FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
    public static String[] homes = new String[]{};
    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

        if (!(commandSender instanceof Player)) return null;
        Player player = (Player) commandSender;

        try {
            cfg.load(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InvalidConfigurationException e) {
            throw new RuntimeException(e);
        }

        List<String> args = new ArrayList<>();
        if (strings.length == 1) {
            if (cfg.contains(player.getName() + " - " + player.getUniqueId() + ".")){
                args.addAll(Objects.requireNonNull(cfg.getConfigurationSection(player.getName() + " - " + player.getUniqueId() + "."))
                        .getKeys(false));

            }else {args.add("[noch keine homes]");}
                return args;
            //args.addAll((Collection<? extends String>) cfg.getConfigurationSection(p.getName() + " - " + p.getUniqueId() + "."));
        }
        return null;
    }
    //    if (!(commandSender instanceof Player)) return null;
    //    Player p = (Player) commandSender;
//
    //    try {
    //        cfg.load(file);
    //    } catch (IOException e) {
    //        throw new RuntimeException(e);
    //    } catch (InvalidConfigurationException e) {
    //        throw new RuntimeException(e);
    //    }
//
    //    List<String> args = new ArrayList<>();
    //    if (strings.length == 1){
    //        for(String key : cfg.getKeys(false)) {
    //            if (key.contains(p.getName() + " - " + p.getUniqueId() + ".")){
    //                args.add()
    //            }
    //            String world = getConfig().getString(key + ".World");
    //            if(Bukkit.getWorld(world) != null)) {
    //                World w = Bukkit.getWorld(world);
    //                Location loc = new Location(world, x, y, z);
    //            } else {
    //                // invalid world
    //            }
    //        }
    //        args.add("help");
    //        args.add("reload");
    //        args.add("server");
    //        return args;
    //    }
    //    if (strings.length == 1){
    //        args.add("help");
    //        args.add("reload");
    //        args.add("server");
    //        return args;
    //    }
    //    else if (strings.length == 2 && strings[0].equals("server")){
    //        //Bukkit.broadcastMessage("'" + strings[0] + "'" + "---" + "'" + strings[1] + "'");
    //        args.add("close");
    //        args.add("open");
    //        return args;
    //    }
//
    //    return null;
    //}
}
