package de.sosig.sosigutils.commands;

import de.sosig.sosigutils.SosigUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class setrankCommand_2 implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (!(commandSender instanceof Player)) return false;
        Player player = (Player) commandSender;
        SosigUtils main = SosigUtils.getMain();

        if (strings.length == 2){
            if (player.isOp()){
                for (Player onlinePlayer : Bukkit.getOnlinePlayers()){
                    if (onlinePlayer.getName().equals(strings[0])){
                        switch (strings[1]){
                            case "owner":
                                main.t_owner.addEntry(onlinePlayer.getName());
                                return true;
                            case "twitch":
                                main.t_twitch.addEntry(onlinePlayer.getName());
                                return true;
                            case "mod":
                                main.t_mod.addEntry(onlinePlayer.getName());
                                return true;
                            case "player":
                                main.t_player.addEntry(onlinePlayer.getName());
                                return true;
                            default:
                                player.sendMessage("§cDiesen Rang gibt es nicht.");
                                return true;
                        }
                    }
                }
            }

        }

        return false;
    }
}
