package de.sosig.sosigutils.commands;

import de.sosig.sosigutils.SosigUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class cantkillmeCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (!(commandSender instanceof Player)) return false;
        Player player = (Player) commandSender;
        SosigUtils main = SosigUtils.getMain();

        if (player.isOp()){
            main.cantkillme = !main.cantkillme;
            player.sendMessage("ckm = §e" + main.cantkillme);
        }

        return false;
    }
}
