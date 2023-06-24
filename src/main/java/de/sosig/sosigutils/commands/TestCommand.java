package de.sosig.sosigutils.commands;

import com.sun.java.swing.action.OkAction;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class TestCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (commandSender instanceof Player){
            Player player = (Player) commandSender;
            if (player.hasPermission("test.use")){
                player.sendMessage("§aTEST");
            }else player.sendMessage("§cKeine Berechtigung dazu.");
        }

        return true;
    }
}
