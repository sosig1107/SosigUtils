package de.sosig.sosigutils.commands;

import de.sosig.sosigutils.SosigUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

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
        try {
            cfg.load(file);
        } catch (IOException e) {
            p.sendMessage("§cEs ist ein fehler aufgetreten (vermutlich existiert die Datei homes.yml nicht und ich bin zu faul das zu coden :)");
            throw new RuntimeException(e);
        } catch (InvalidConfigurationException e) {
            p.sendMessage("§cEs ist ein fehler aufgetreten (vermutlich existiert die Datei homes.yml nicht und ich bin zu faul das zu coden :)");
            throw new RuntimeException(e);
        }
        if (strings.length == 0 && cfg.contains(p.getName() + " - " + p.getUniqueId() + "." + "home")){
            p.teleport(Objects.requireNonNull(cfg.getLocation(p.getName() + " - " + p.getUniqueId() + "." + "home")));
            p.sendMessage(SosigUtils.prefix + "§eDu wurdest zu deinem home §c" + "home" + "§e teleportiert.");
        }
        if (strings.length == 1){
            if (cfg.contains(p.getName() + " - " + p.getUniqueId() + "." + strings[0])){
                p.teleport(Objects.requireNonNull(cfg.getLocation(p.getName() + " - " + p.getUniqueId() + "." + strings[0])));
                p.sendMessage(SosigUtils.prefix + "§eDu wurdest zu deinem home §c" + strings[0] + "§e teleportiert.");
            }else p.sendMessage(SosigUtils.prefix + "§cHome existiert nicht");
        }else {
            p.sendMessage("§c/home [name]");
        }
        return false;
    }
}
