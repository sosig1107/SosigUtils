package de.sosig.sosigutils.events;

import de.sosig.sosigutils.SosigUtils;
import de.sosig.sosigutils.Timer;
import de.sosig.sosigutils.commands.TimerCommand;
import de.sosig.sosigutils.managers.NametagManager;
import de.sosig.sosigutils.managers.RankManager;
import de.sosig.sosigutils.storage.Rank;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scoreboard.Team;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class JoinEvent implements Listener {
    File rankFile = new File("plugins/SosigUtil", "ranks.yml");
    FileConfiguration rankCfg = YamlConfiguration.loadConfiguration(rankFile);

    public JoinEvent() throws IOException {
    }
    File file = new File("plugins/SosigUtil", "timer.yml");
    FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        e.setJoinMessage("§7[§e§l+§7] §e" + e.getPlayer().getName());
        Player player = e.getPlayer();
        SosigUtils main = SosigUtils.getMain();

        player.setPlayerListHeaderFooter("\n          §d§lTwitch.tv/sosig808          \n", "\n        §dping §7| §f" + player.spigot().getPing() + "ms        \n");
        player.setScoreboard(SosigUtils.getMain().sb);

        //Bukkit.broadcastMessage(player.getName());

        if(!player.hasPlayedBefore()) {
            player.setDisplayName(main.playerRankTabPrefix + player.getName());
            player.setPlayerListName(main.playerRankTabPrefix + player.getName());
            Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "lp user " + player.getName() + " group set default");
            //SosigUtils.getMain().t_player.addEntry(player.getName());
        }

        //if (player.getName().equals("NoticeTheSosig")){
        //    SosigUtils.getMain().t_owner.addEntry(player.getName());
            //player.setPlayerListName("§d§lTwitch §7| §f" + player.getName());
            //RankManager.setRank(Rank.VIP, player);
            //NametagManager.setNametag(player);
            //NametagManager.newTag(player);
        //}
        //NametagManager.setNametag(player);
        //NametagManager.newTag(player);

        main.updateRanks();


        //if (player.getName() == "NoticeTheSosig"){
        //    player.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
        //    Team team = player.getScoreboard().registerNewTeam("twitch");
        //    //team.setPrefix(rank.getPrefix() + ChatColor.WHITE + " | ");
        //    team.setPrefix("§d§lTwitch §7| §f");
//
        //    for (Player target : Bukkit.getOnlinePlayers()){
        //        if (player.getUniqueId() != target.getUniqueId()){
        //            player.getScoreboard().getTeam("§d§lTwitch §7| §f").addEntry((target.getName()));
        //        }
        //    }
//
        //}

        //Timer timer = TimerCommand.timers.get(e.getPlayer().getUniqueId());

        try {
            cfg.load(file);
        } catch (IOException u) {
            e.getPlayer().sendMessage("§cEs ist ein fehler aufgetreten");
            throw new RuntimeException(u);
        } catch (InvalidConfigurationException u) {
            e.getPlayer().sendMessage("§cEs ist ein fehler aufgetreten");
            throw new RuntimeException(u);
        }


        if (cfg.contains(player.getName() + " - " + player.getUniqueId() + "." + "time")){

            Timer timer = new Timer();
            timer.start(player);
            timer.pause();
            try
            {
                int newTime = Integer.parseInt(cfg.get(player.getName() + " - " + player.getUniqueId() + "." + "time").toString());
                timer.set(newTime);
                player.sendMessage("§dTimer wurde auf §e" + newTime + " §dsekunden gesetzt.");

                TextComponent txt = new TextComponent();
                txt.setText("§e> Timer fortsetzen");
                txt.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/timer resume"));
                player.sendMessage(txt);

            } catch (NumberFormatException ex)
            {
                throw new NumberFormatException();
            }
            //player.sendMessage("§dTimer wurde wiederhergestellt");
            TimerCommand.timers.put(player.getUniqueId(), timer);
        }//else p.sendMessage(SosigUtils.prefix + "§cTimer existiert nicht");

        //try
        //{
        //    int value = Integer.parseInt(String.valueOf(cfg.getConfigurationSection(String.valueOf(e.getPlayer().getName() + "." + "time: "))));
        //    Bukkit.getLogger().info("§4§cTimer State: §6" + value);
        //    Bukkit.getLogger().info("§3§cREAD FROM FILE: §6" + cfg.getConfigurationSection(String.valueOf(e.getPlayer().getName() + "." + "time: ")));
        //    timer.set(value);
        //} catch (NumberFormatException ex)
        //{
        //    Bukkit.getLogger().info("§4§cTimer State: §6");
        //    Bukkit.getLogger().info("§3§cREAD FROM FILE: §6" + cfg.getConfigurationSection(String.valueOf(e.getPlayer().getName() + "." + "time: ")));
        //    Bukkit.getLogger().info(ex.toString());
        //    Bukkit.getLogger().info("FEHLER.");
        //}
        }

        //cfg.set(e.getPlayer().getName().toString(), timer.elapsedTime);
        //cfg.getConfigurationSection(p.getName() + " - " + p.getUniqueId() + ".";

}
