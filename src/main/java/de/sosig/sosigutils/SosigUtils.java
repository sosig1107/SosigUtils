package de.sosig.sosigutils;

import de.sosig.sosigutils.commands.*;
import de.sosig.sosigutils.events.JoinEvent;
import de.sosig.sosigutils.events.QuitEvent;
import de.sosig.sosigutils.listeners.RankListeners;
import jdk.tools.jmod.Main;
import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
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

public final class SosigUtils extends JavaPlugin {
    private static HashMap<UUID, PermissionAttachment> perms = new HashMap<>();

    public static String prefix = "§d§l[Sosig Utils] §e";
    public static String noPerm = prefix + "§cDafür fehlt die die berechtigung: §3§l";

    public static File timerFile = new File("plugins/SosigUtil", "timer.yml");
    private static FileConfiguration timerFilecfg = YamlConfiguration.loadConfiguration(timerFile);
    public static FileConfiguration getTimerFileCfg() throws IOException {
        if (!timerFile.exists()){
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
    public static HashMap<UUID, PermissionAttachment> getPerms(){
        return perms;
    }
    public static SosigUtils getMain(){
        return main;
    }
    private HashMap<Player, Player> tpa = new HashMap<Player, Player>();



    public HashMap<Player, Player> getTpa(){
        return tpa;
    }
    public Scoreboard sb;
    public Team t_owner;
    public Team t_mod;
    public Team t_twitch;
    public Team t_player;

    @Override
    public void onEnable() {
        // Plugin startup logic


        sb = Bukkit.getScoreboardManager().getNewScoreboard();
        t_owner = sb.registerNewTeam("a");
        t_twitch = sb.registerNewTeam("b");
        t_mod = sb.registerNewTeam("c");
        t_player = sb.registerNewTeam("d");
        t_owner.setPrefix("§c§lOwner §7| §f");
        t_twitch.setPrefix("§d§lTwitch §7| §f");
        t_mod.setPrefix("§a§lMod §7| §f");
        t_player.setPrefix("§7§lPlayer §7| §f");
        //you can add as many as you want

        main = this;
        perms.clear();
        try {
            getTimerFileCfg();
        } catch (IOException e) {
            getLogger().info("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
            throw new RuntimeException(e);
        }
        PluginManager pluginManager = Bukkit.getPluginManager();
        getLogger().info( prefix + "Plugin geladen.");
        getCommand("home").setExecutor(new HomeCommand());
        getCommand("home").setTabCompleter(new HomeTabCompletion());
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

        //getCommand("anvil").setExecutor(new anvilCommand());

        //getCommand("i").setExecutor(new iCommand());
        //getCommand("i").setTabCompleter(new iCommandTabCompletion());


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
        //getConfig().options().copyDefaults(true);
        //saveConfig();

        //getServer().getPluginManager().registerEvents(new RankListeners(), this);
        //getCommand("setrank").setExecutor(new SetRankCommand());
        //getCommand("setrank").setTabCompleter(new SetrankTabCompletion());
        //getCommand("test").setExecutor(new TestCommand());

    }
    File file = new File("plugins/SosigUtil", "timer.yml");
    FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
    @Override
    public void onDisable() {
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
        for (Player player : Bukkit.getOnlinePlayers()){
            if (cfg.contains(player.getName() + " - " + player.getUniqueId() + "." + "time")){
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
