package de.sosig.sosigutils;

import de.sosig.sosigutils.commands.*;
import de.sosig.sosigutils.events.JoinEvent;
import de.sosig.sosigutils.events.QuitEvent;
import de.sosig.sosigutils.listeners.RankListeners;
import jdk.tools.jmod.Main;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public final class SosigUtils extends JavaPlugin implements Listener {
    private static HashMap<UUID, PermissionAttachment> perms = new HashMap<>();

    public static String prefix = "§d§l[Sosig Utils] §e";
    public static String noPerm = prefix + "§cDafür fehlt die die berechtigung: §3§l";

    public static File timerFile = new File("plugins/SosigUtil", "timer.yml");
    private static FileConfiguration timerFilecfg = YamlConfiguration.loadConfiguration(timerFile);

    public static FileConfiguration getTimerFileCfg() throws IOException {
        if (!timerFile.exists()) {
            //timerFile.createNewFile();
            timerFile.getParentFile().mkdirs();
            //FileOutputStream oFile = new FileOutputStream(timerFile, false);
        }
        try {
            timerFilecfg.load(timerFile);
        } catch (IOException e) {
            //p.sendMessage("§cEs ist ein fehler aufgetreten (vermutlich existiert die Datei homes.yml nicht und ich bin zu faul das zu coden :)");
            timerFile.getParentFile().mkdirs();
            timerFile.createNewFile();
            //throw new RuntimeException(e);
        } catch (InvalidConfigurationException e) {
            //p.sendMessage("§cEs ist ein fehler aufgetreten (vermutlich existiert die Datei homes.yml nicht und ich bin zu faul das zu coden :)");
            throw new RuntimeException(e);
        }
        return timerFilecfg;
    }

    private static SosigUtils main;

    public static HashMap<UUID, PermissionAttachment> getPerms() {
        return perms;
    }

    public static SosigUtils getMain() {
        return main;
    }
    public boolean cantkillme = false;

    private HashMap<Player, Player> tpa = new HashMap<Player, Player>();
    public HashMap<Player, String> ranks = new HashMap<Player, String>();

    File rankFile = new File("plugins/SosigUtil", "ranks.yml");
    FileConfiguration rankCfg = YamlConfiguration.loadConfiguration(rankFile);


    public HashMap<Player, Player> getTpa() {
        return tpa;
    }

    public Scoreboard sb;
    //public Team t_owner;
    //public Team t_mod;
    //public Team t_twitch;
    //public Team t_player;

    public String twitchRankTabPrefix = "§d§lTwitch §7| §f";
    public String twitchRankChatPrefix = "§7[§d§lTwitch§7] §f"; //NoticeTheSosig: hi
    public String modRankTabPrefix = "§a§lMod §7| §f";
    public String modRankChatPrefix = "§7[§a§lMod§7] §f"; //NoticeTheSosig: hi
    public String bossRankTabPrefix = "§5§lBoss §7| §f";
    public String bossRankChatPrefix = "§7[§5§lBoss§7] §f"; //NoticeTheSosig: hi
    public String playerRankTabPrefix = "§7§lPlayer §7| §f";
    public String playerRankChatPrefix = "§7[§7§lPlayer§7] §f"; //NoticeTheSosig: hi

    @Override
    public void onEnable() {
        // Plugin startup logic

        getServer().getPluginManager().registerEvents(this, this);


        sb = Bukkit.getScoreboardManager().getNewScoreboard();
        //t_owner = sb.registerNewTeam("a");
        //t_twitch = sb.registerNewTeam("b");
        //t_mod = sb.registerNewTeam("c");
        //t_player = sb.registerNewTeam("d");
        //t_owner.setPrefix("§c§lOwner §7| §f");
        //t_twitch.setPrefix("§d§lTwitch §7| §f");
        //t_mod.setPrefix("§a§lMod §7| §f");
        //t_player.setPrefix("§7§lPlayer §7| §f");
        //you can add as many as you want

        main = this;
        getServer().getPluginManager().registerEvents(this, this);
        perms.clear();
        try {
            getTimerFileCfg();
        } catch (IOException e) {
            getLogger().info("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
            throw new RuntimeException(e);
        }
        PluginManager pluginManager = Bukkit.getPluginManager();
        getLogger().info(prefix + "Plugin geladen.");
        getCommand("home").setExecutor(new HomeCommand());
        getCommand("home").setTabCompleter(new HomeTabCompletion());

        getCommand("h").setExecutor(new HomeCommand());
        getCommand("h").setTabCompleter(new HomeTabCompletion());

        getCommand("sethome").setExecutor(new SethomeCommand());
        getCommand("delhome").setExecutor(new DelhomeCommand());
        getCommand("delhome").setTabCompleter(new HomeTabCompletion());
        getCommand("dupe").setExecutor(new DupeCommand());

        getCommand("tpa").setExecutor(new tpaCommand());
        getCommand("tpaccept").setExecutor(new tpacceptCommand());

        getCommand("timer").setExecutor(new TimerCommand());
        getCommand("timer").setTabCompleter(new TimerTabCompletion());

        getCommand("setrank").setExecutor(new setrankCommand_2());
        getCommand("setrank").setTabCompleter(new setrankTabCompletion_2());

        getCommand("challenge").setExecutor(new ChallengeCommand());
        getCommand("challenge").setTabCompleter(new ChallengeCommandTabCompletion());

        getCommand("cantkillme").setExecutor(new cantkillmeCommand());

        updateRanks();

            //getCommand("anvil").setExecutor(new anvilCommand());

            //getCommand("i").setExecutor(new iCommand());
            //getCommand("i").setTabCompleter(new iCommandTabCompletion());

            Bukkit.getScheduler().runTaskTimer(this, () -> {
                //try {
                //    rankCfg.load(rankFile);
                //} catch (IOException e) {
                //    throw new RuntimeException(e);
                //} catch (InvalidConfigurationException e) {
                //    throw new RuntimeException(e);
                //}
                    //updateRanks();
                for (Player pl : Bukkit.getOnlinePlayers()) {
                    pl.setPlayerListHeaderFooter("\n          §d§lTwitch.tv/sosig808          \n", "\n        §dping §7| §f" + pl.spigot().getPing() + "ms        \n");
                    pl.setScoreboard(sb);

                }
            }, 0, 20); // 20 Ticks entsprechen 1 Sekunde

            try {
                pluginManager.registerEvents(new JoinEvent(), this);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            try {
                pluginManager.registerEvents(new QuitEvent(), this);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
        public void updateRanks (){
            try {
                rankCfg.load(rankFile);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (InvalidConfigurationException e) {
                throw new RuntimeException(e);
            }
            for (Player pl : Bukkit.getOnlinePlayers()) {
                if (rankCfg.contains(pl.getName())) {
                    ranks.put(pl, rankCfg.getString(pl.getName()));
                }
                switch (main.ranks.get(pl)){
                    case "owner":
                        pl.setDisplayName("§cOwner §f" + pl.getName());
                        pl.setPlayerListName("§cOwner §f" + pl.getName());
                        Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "lp user " + pl.getName() + " group set owner");
                        return;
                    case "twitch":
                        pl.setDisplayName(twitchRankTabPrefix + pl.getName());
                        pl.setPlayerListName(twitchRankTabPrefix + pl.getName());
                        Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "lp user " + pl.getName() + " group set twitch");
                        return;
                    case "boss":
                        pl.setDisplayName(bossRankTabPrefix + pl.getName());
                        pl.setPlayerListName(bossRankTabPrefix + pl.getName());
                        Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "lp user " + pl.getName() + " group set boss");
                        return;
                    case "mod":
                        pl.setDisplayName(modRankTabPrefix + pl.getName());
                        pl.setPlayerListName(modRankTabPrefix + pl.getName());
                        Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "lp user " + pl.getName() + " group set mod");
                        return;
                    case "player":
                        pl.setDisplayName(playerRankTabPrefix + pl.getName());
                        pl.setPlayerListName(playerRankTabPrefix + pl.getName());
                        Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "lp user " + pl.getName() + " group set default");
                        return;
                    default:
                        return;
                }

            }
        }
        File file = new File("plugins/SosigUtil", "timer.yml");
        FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            if (player.getName().equals("NoticeTheSosig") && cantkillme) {
                double originalDamage = event.getDamage();
                double reducedDamage = originalDamage * 0.45; // 1% des ursprünglichen Schadens
                event.setDamage(reducedDamage);
            }
        }
    }
    //@EventHandler
    //public void onPlayerLogin(PlayerLoginEvent event) {
    //    Player l = event.getPlayer();
    //    switch (main.ranks.get(pl)){
    //        case "owner":
    //            main.t_owner.addEntry(pl.getName());
    //            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + pl + " group set owner");
    //            return;
    //        case "twitch":
    //            main.t_twitch.addEntry(pl.getName());
    //            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + pl + " group set twitch");
    //            return;
    //        case "mod":
    //            main.t_mod.addEntry(pl.getName());
    //            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + pl + " group set mod");
    //            return;
    //        case "player":
    //            main.t_player.addEntry(pl.getName());
    //            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + pl + " group set default");
    //            return;
    //        default:
    //            return;
    //    }
    //}
    //@EventHandler
    //public void onChat(PlayerChatEvent event) {
    //    Player player = event.getPlayer();
    //    event.setCancelled(true);
//
    //    switch (main.ranks.get(player)){
    //        case "owner":
    //            return;
    //        case "twitch":
    //            Bukkit.broadcastMessage(twitchRankChatPrefix + player.getName() + ":" + event.getMessage());
    //            return;
    //        case "boss":
    //            Bukkit.broadcastMessage(bossRankChatPrefix + player.getName() + ":" + event.getMessage());
    //            return;
    //        case "mod":
    //            Bukkit.broadcastMessage(modRankChatPrefix + player.getName() + ":" + event.getMessage());
    //            return;
    //        case "player":
    //            Bukkit.broadcastMessage(playerRankChatPrefix + player.getName() + ":" + event.getMessage());
    //            return;
    //        default:
    //            return;
    //    }
    //}
        @Override
        public void onDisable () {
            // Plugin shutdown logic
            try {
                cfg.load(file);
            } catch (IOException u) {
                getLogger().info("§cEs ist ein fehler aufgetreten");
                throw new RuntimeException(u);
            } catch (InvalidConfigurationException u) {
                getLogger().info("§cEs ist ein fehler aufgetreten");
                throw new RuntimeException(u);
            }
            for (Player player : Bukkit.getOnlinePlayers()) {
                if (cfg.contains(player.getName() + " - " + player.getUniqueId() + "." + "time")) {
                    Timer timer = TimerCommand.timers.get(player.getUniqueId());
                    timer.pause();
                    cfg.set(player.getName() + " - " + player.getUniqueId() + "." + "time", timer.elapsedTime);
                    try {
                        cfg.save(file);
                    } catch (IOException r) {
                        throw new RuntimeException(r);
                    }
                    TimerCommand.timers.put(player.getUniqueId(), timer);
                }//else p.sendMessage(SosigUtils.prefix + "§cTimer existiert nicht");
            }
            main = null;
            perms.clear();
        }


    }