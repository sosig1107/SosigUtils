package de.sosig.sosigutils.listeners;

import de.sosig.sosigutils.SosigUtils;
import de.sosig.sosigutils.managers.NametagManager;
import de.sosig.sosigutils.managers.RankManager;
import de.sosig.sosigutils.storage.Rank;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.*;

public class RankListeners implements Listener {

    public void onJoin(PlayerJoinEvent e){
        SosigUtils main = SosigUtils.getMain();
        FileConfiguration configuration = main.getConfig();
        Player player = e.getPlayer();
        String uuid = player.getUniqueId().toString();

        if (!configuration.contains(uuid)){
            RankManager.setRank(Rank.PLAYER, player);
        }else {
            RankManager.removePermissions(player);
            RankManager.setPermissions(player);
        }
        NametagManager.setNametag(player);
        NametagManager.newTag(player);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e){
        NametagManager.removeTag(e.getPlayer());
    }
    @EventHandler
    public void onWhisper(PlayerChatEvent e){

    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e){
        Player player = e.getPlayer();
        String message = e.getMessage();
        String name = player.getName();

        e.setCancelled(true);
        Bukkit.broadcastMessage(RankManager.getRank(player).getPrefix() + ChatColor.WHITE + name + ": " + ChatColor.WHITE + message);
    }

}
