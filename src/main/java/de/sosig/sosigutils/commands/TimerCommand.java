package de.sosig.sosigutils.commands;

import com.google.common.collect.Maps;
import de.sosig.sosigutils.Timer;
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
import java.util.Map;
import java.util.UUID;

public class TimerCommand implements CommandExecutor {

    public static Map<UUID, Timer> timers = Maps.newHashMap();
    File file = new File("plugins/SosigUtil", "timer.yml");
    FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

        if (!(commandSender instanceof Player)) return false;

        Player player = (Player) commandSender;

        if (strings.length == 1 && strings[0].equalsIgnoreCase("start")){
            if (!timers.containsKey(player.getUniqueId())){
                Timer timer = new Timer();
                timer.start(player);
                player.sendMessage("§dTimer wurde gestartet");
                timers.put(player.getUniqueId(), timer);
                return true;
            }else {
                player.sendMessage("§cDu kannst keinen neuen Timer starten während ein anderer läuft.");
            }
        }
        if (strings.length == 1 && strings[0].equalsIgnoreCase("stop")){
            if (timers.containsKey(player.getUniqueId())){
                Timer timer = timers.get(player.getUniqueId());

                cfg.set(player.getName() + " - " + player.getUniqueId() + "." + "time", timer.elapsedTime);
                try {
                    cfg.save(file);
                } catch (IOException r) {
                    throw new RuntimeException(r);
                }

                timer.stop();
                player.sendMessage("§dTimer wurde gestoppt");
                return true;
            }else {
                player.sendMessage("§c Es gibt keinen timer");
            }
        }
        if (strings.length == 1 && strings[0].equalsIgnoreCase("pause")){
            if (timers.containsKey(player.getUniqueId())){
                Timer timer = timers.get(player.getUniqueId());
                timer.pause();
                player.sendMessage("§dTimer wurde pausiert");
                return true;
            }else {
                player.sendMessage("§c Es gibt keinen timer");
            }
        }
        if (strings.length == 1 && strings[0].equalsIgnoreCase("resume")){
            if (timers.containsKey(player.getUniqueId())){
                Timer timer = timers.get(player.getUniqueId());
                timer.resume();
                player.sendMessage("§dTimer wurde fortgesetzt");
                return true;
            }else {
                player.sendMessage("§c Es gibt keinen timer");
            }
        }
        if (strings.length == 1 && strings[0].equalsIgnoreCase("get")){
            if (timers.containsKey(player.getUniqueId())){
                Timer timer = timers.get(player.getUniqueId());
                player.sendMessage("§dTimer ist bei §f" + timer.elapsedTime + " §dSekunden");
                return true;
            }else {
                player.sendMessage("§c Es gibt keinen timer");
            }
        }
        if (strings.length == 2 && strings[0].equalsIgnoreCase("set")){

            if (timers.containsKey(player.getUniqueId())){
                Timer timer = timers.get(player.getUniqueId());
                int newTime;
                try
                {
                    newTime = Integer.parseInt(strings[1]);
                    timer.set(newTime);
                    player.sendMessage("§dTimer wurde auf §e" + newTime + " §dsekunden gesetzt.");
                    return true;
                } catch (NumberFormatException ex)
                {
                    player.sendMessage("§c/timer set [sekunden]");
                    return false;
                }
            }else {
                player.sendMessage("§c Es gibt keinen timer");
            }
        }
        player.sendMessage("c /timer < start / stop / pause / resume >");

        return false;
    }
}
