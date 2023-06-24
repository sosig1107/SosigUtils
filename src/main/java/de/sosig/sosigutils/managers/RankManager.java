package de.sosig.sosigutils.managers;

import de.sosig.sosigutils.SosigUtils;
import de.sosig.sosigutils.storage.Rank;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;

import java.util.UUID;

public class RankManager {

    public static void setRank(Rank rank, Player player){
        SosigUtils main = SosigUtils.getMain();
        FileConfiguration configuration = main.getConfig();
        String uuid = player.getUniqueId().toString();

        if (configuration.contains(uuid)){
            removePermissions(player);
        }
        configuration.set(uuid, rank.name());
        main.saveConfig();
        setPermissions(player);

        NametagManager.setNametag(player);
        NametagManager.newTag(player);
    }

    public static Rank getRank(Player player){
        SosigUtils main = SosigUtils.getMain();
        FileConfiguration configuration = main.getConfig();

        return Rank.valueOf(configuration.getString(player.getUniqueId().toString()));
    }

    public static void setPermissions(Player player){
        SosigUtils main = SosigUtils.getMain();
        FileConfiguration configuration = main.getConfig();
        UUID uuid = player.getUniqueId();
        Rank rank = getRank(player);

        PermissionAttachment attachment = player.addAttachment(main);
        SosigUtils.getPerms().put(uuid, attachment);

        for(String perm : rank.getPermissions()){
            attachment.setPermission(perm, true);
        }
    }
    public static void removePermissions(Player player){
        SosigUtils main = SosigUtils.getMain();
        FileConfiguration configuration = main.getConfig();
        UUID uuid = player.getUniqueId();
        Rank rank = getRank(player);

        for (String perm : rank.getPermissions()){
            SosigUtils.getPerms().get(uuid).unsetPermission(perm);
        }
    }

}
