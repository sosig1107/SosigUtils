package de.sosig.sosigutils.commands;

import de.sosig.sosigutils.SosigUtils;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import sun.security.krb5.internal.CredentialsUtil;

import java.io.File;
import java.io.IOException;

public class SethomeCommand implements CommandExecutor{
    File file = new File("plugins/SosigUtil", "homes.yml");
    FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (!(commandSender instanceof Player)) return true;
        Player p = (Player) commandSender;
        Location location = p.getLocation();
        if (strings.length == 1){
            cfg.set(p.getName() + " - " + p.getUniqueId() + "." + strings[0], location);
            p.sendMessage("§eHome §c" + strings[0] + "§e wurde erstellt.");
        }else p.sendMessage("§c/sethome [name]");
        try {
            cfg.save(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return true;
        //cfg.set(p.getUniqueId().toString(), location);
    }
}
