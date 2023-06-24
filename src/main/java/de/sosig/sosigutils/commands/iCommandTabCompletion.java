package de.sosig.sosigutils.commands;

import de.sosig.sosigutils.SosigUtils;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class iCommandTabCompletion implements TabCompleter {
    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

        if (!(commandSender instanceof Player)) return null;
        Player player = (Player) commandSender;
        SosigUtils main = SosigUtils.getMain();
        List<String> args = new ArrayList<>();

        //for (ItemStack item : Material.values().){
        //    args.add(String.valueOf(item));
        //}

        for (Material item : Material.values()){
            args.add(String.valueOf(item));
        }
        return args;

        //return null;
    }
}
