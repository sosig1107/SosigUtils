package de.sosig.sosigutils.Trashcan;

import de.sosig.sosigutils.SosigUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.File;

public class HomeCommand implements CommandExecutor {
    private SosigUtils plugin;
    //public HomeCommand(SosigUtils info){
    //    this.plugin = info;
    //}
    File file = new File("plugins/SosigUtil", "homes.yml");
    FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (!(commandSender instanceof Player)) return false;
        Player p = (Player) commandSender;
        if (strings.length == 1){

            String world = cfg.getString(p.getName() + "." + strings[0] + ".world");
            double x = cfg.getDouble(p.getName() + "." + strings[0] + ".x");
            double y = cfg.getDouble(p.getName() + "." + strings[0] + ".y");
            double z = cfg.getDouble(p.getName() + "." + strings[0] + ".z");
            double yaw = cfg.getDouble(p.getName() + "." + strings[0] + ".yaw");
            double pitch = cfg.getDouble(p.getName() + "." + strings[0] + ".pitch");

            Location loc = new Location(Bukkit.getWorld(world), x, y, z);
            loc.setPitch((float) pitch);
            loc.setYaw((float) yaw);

            p.teleport(loc);
            p.sendMessage("§eDu wurdest zu deinem home §c" + strings[0] + "§e teleportiert.");

        }else {
            p.sendMessage("§c/home [name]");
        }
        return false;
    }
}
