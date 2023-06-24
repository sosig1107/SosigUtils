package de.sosig.sosigutils.commands;

import de.sosig.sosigutils.SosigUtils;
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

public class DelhomeCommand implements CommandExecutor {
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
            throw new RuntimeException(e);
        } catch (InvalidConfigurationException e) {
            throw new RuntimeException(e);
        }
        if (strings.length == 1){
            if (cfg.contains(p.getName() + " - " + p.getUniqueId() + "." + strings[0])){
                cfg.set(p.getName() + " - " + p.getUniqueId() + "." + strings[0], null);
                try {
                    cfg.save(file);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                p.sendMessage("§eDein Home §c" + strings[0] + "§e wurde gelöscht.");
            }else p.sendMessage(SosigUtils.prefix + "§cHome existiert nicht");
        }else {
            p.sendMessage(SosigUtils.prefix + "§c/delhome [name]");
        }
        return false;
    }
}
