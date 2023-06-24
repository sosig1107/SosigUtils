package de.sosig.sosigutils.events;

import de.sosig.sosigutils.SosigUtils;
import de.sosig.sosigutils.Timer;
import de.sosig.sosigutils.commands.TimerCommand;
import de.sosig.sosigutils.managers.NametagManager;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import javax.imageio.IIOException;
import java.io.File;
import java.io.IOException;

public class QuitEvent implements Listener {

    File file = new File("plugins/SosigUtil", "timer.yml");
    FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);

    public QuitEvent() throws IOException {
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent e){
        //NametagManager.removeTag(e.getPlayer());
        e.setQuitMessage("§7[§e§l-§7] §e" + e.getPlayer().getName());
        if (cfg.contains(e.getPlayer().getName() + " - " + e.getPlayer().getUniqueId() + "." + "time")){
            Timer timer = TimerCommand.timers.get(e.getPlayer().getUniqueId());
            timer.pause();
            try {
                cfg.load(file);
            } catch (IOException u) {
                e.getPlayer().sendMessage("§cEs ist ein fehler aufgetreten");
                throw new RuntimeException(u);
            } catch (InvalidConfigurationException u) {
                e.getPlayer().sendMessage("§cEs ist ein fehler aufgetreten");
                throw new RuntimeException(u);
            }
            //cfg.set(e.getPlayer().getName(), timer.elapsedTime);
            //cfg.set(e.getPlayer().getName() + "." + "time: ", timer.elapsedTime);
            Player p = e.getPlayer();
            cfg.set(p.getName() + " - " + p.getUniqueId() + "." + "time", timer.elapsedTime);
            try {
                cfg.save(file);
            } catch (IOException r) {
                throw new RuntimeException(r);
            }

        }
    }
}