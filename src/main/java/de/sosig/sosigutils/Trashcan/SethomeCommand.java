package de.sosig.sosigutils.Trashcan;

import de.sosig.sosigutils.SosigUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;

public class SethomeCommand implements CommandExecutor{
    private SosigUtils plugin;
    File file = new File("plugins/SosigUtil", "homes.yml");
    FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (!(commandSender instanceof Player)) return true;
        Player p = (Player) commandSender;
        
        if (strings.length == 1) {
            if (!cfg.contains(strings[0])){
                String world = p.getWorld().getName();
                double x = p.getLocation().getX();
                double y = p.getLocation().getY();
                double z = p.getLocation().getZ();
                float yaw = p.getLocation().getYaw();
                float pitch = p.getLocation().getPitch();

                cfg.set(p.getName() + "." + strings[0] + ".world", world);
                cfg.set(p.getName() + "." + strings[0] + ".x", x);
                cfg.set(p.getName() + "." + strings[0] + ".y", y);
                cfg.set(p.getName() + "." + strings[0] + ".z", z);
                cfg.set(p.getName() + "." + strings[0] + ".yaw", yaw);
                cfg.set(p.getName() + "." + strings[0] + ".pitch", pitch);
                try {
                    cfg.save(file);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                p.sendMessage("§eHome §c" + strings[0] + "§e wurde erstellt.");
            }else {
                p.sendMessage("§cHome existiert bereits");
            }
        }else {
            p.sendMessage("§c/sethome [name]");
        }
        return false;
    }
}
