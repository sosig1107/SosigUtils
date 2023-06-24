package de.sosig.sosigutils.commands;

import de.sosig.sosigutils.managers.RankManager;
import de.sosig.sosigutils.storage.Rank;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class SetRankCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

        if (commandSender instanceof Player){
            Player player = (Player) commandSender;

            if (strings.length == 2){
                if (Bukkit.getPlayer(strings[0]) != null){
                    Player target = Bukkit.getPlayer(strings[0]);
                    switch (strings[1]){
                        case "owner":
                            RankManager.setRank(Rank.OWNER, target);
                            target.sendMessage("§eDu hast jetzt den Rang: §c§lOwner");
                            return true;
                        case "admin":
                            RankManager.setRank(Rank.ADMIN, target);
                            target.sendMessage("§eDu hast jetzt den Rang: §4§lAdmin");
                            return true;
                        case "moderator":
                            RankManager.setRank(Rank.MODERATOR, target);
                            target.sendMessage("§eDu hast jetzt den Rang: §a§lModerator");
                            return true;
                        case "twitch":
                            RankManager.setRank(Rank.TWITCH, target);
                            target.sendMessage("§eDu hast jetzt den Rang: §d§lTwitch");
                            return true;
                        case "youtube":
                            RankManager.setRank(Rank.YOUTUBE, target);
                            target.sendMessage("§eDu hast jetzt den Rang: §4§lYouTube");
                            return true;
                        case "vip":
                            RankManager.setRank(Rank.VIP, target);
                            target.sendMessage("§eDu hast jetzt den Rang: §6§VIP");
                            return true;
                        case "player":
                            RankManager.setRank(Rank.PLAYER, target);
                            target.sendMessage("§eDu hast jetzt den Rang: §7§lPlayer");
                            return true;
                        default:
                            player.sendMessage("§cRang nicht gefunden.");
                    }
                }else player.sendMessage("§cSpieler nicht gefunden.");

            }else player.sendMessage("§c/setrank [player] [rank]");
        }

        return true;
    }
}
