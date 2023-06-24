package de.sosig.sosigutils.commands;

import de.sosig.sosigutils.SosigUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class tpacceptCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (!(commandSender instanceof Player)) return false;
        Player player = (Player) commandSender;
        SosigUtils main = SosigUtils.getMain();

        if (strings.length == 0){
            if (main.getTpa().containsKey(player)){
                Player player1 = main.getTpa().get(player);
                //player.sendMessage("player1: " + player1.getName());
                if (player1 != null && player1.isOnline()){
                    player1.teleport(player.getLocation());
                    //player1.sendMessage("Er");
                    main.getTpa().remove(player);
                }else {
                    player.sendMessage("§cSpieler nicht gefunden");
                    main.getTpa().remove(player);
                }
            }else {
                player.sendMessage("§cGlaube hier muss hin dass es keine ausstehenden tpa's gibt Shruge");
            }
        }

        return false;
    }
}
