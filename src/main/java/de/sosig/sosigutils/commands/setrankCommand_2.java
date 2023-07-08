package de.sosig.sosigutils.commands;

import de.sosig.sosigutils.SosigUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;

public class setrankCommand_2 implements CommandExecutor {
    File file = new File("plugins/SosigUtil", "ranks.yml");
    FileConfiguration rankCfg = YamlConfiguration.loadConfiguration(file);
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
                                //main.t_owner.addEntry(onlinePlayer.getName());
                                main.ranks.remove(onlinePlayer);
                                main.ranks.put(onlinePlayer, strings[1]);
                                player.performCommand("lp user " + strings[0] + " group set " + strings[1]);
                                rankCfg.set(onlinePlayer.getName(), "owner");
                                try {
                                    rankCfg.save(file);
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                                main.updateRanks();
                                return true;
                            case "twitch":
                                //main.t_twitch.addEntry(onlinePlayer.getName());
                                main.ranks.remove(onlinePlayer);
                                main.ranks.put(onlinePlayer, strings[1]);
                                player.performCommand("lp user " + strings[0] + " group set " + strings[1]);
                                rankCfg.set(onlinePlayer.getName(), "twitch");
                                try {
                                    rankCfg.save(file);
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                                main.updateRanks();
                                return true;
                            case "mod":
                                //main.t_mod.addEntry(onlinePlayer.getName());
                                main.ranks.remove(onlinePlayer);
                                main.ranks.put(onlinePlayer, strings[1]);
                                player.performCommand("lp user " + strings[0] + " group set " + strings[1]);
                                rankCfg.set(onlinePlayer.getName(), "mod");
                                try {
                                    rankCfg.save(file);
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                                main.updateRanks();
                                return true;
                            case "boss":
                                //main.t_mod.addEntry(onlinePlayer.getName());
                                main.ranks.remove(onlinePlayer);
                                main.ranks.put(onlinePlayer, strings[1]);
                                player.performCommand("lp user " + strings[0] + " group set " + strings[1]);
                                rankCfg.set(onlinePlayer.getName(), "boss");
                                try {
                                    rankCfg.save(file);
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                                main.updateRanks();
                                return true;
                            case "player":
                                //main.t_player.addEntry(onlinePlayer.getName());
                                main.ranks.remove(onlinePlayer);
                                main.ranks.put(onlinePlayer, strings[1]);
                                player.performCommand("lp user " + strings[0] + " group set default");
                                rankCfg.set(onlinePlayer.getName(), "player");
                                try {
                                    rankCfg.save(file);
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                                main.updateRanks();
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
