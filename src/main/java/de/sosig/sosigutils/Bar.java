package de.sosig.sosigutils;

import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;

public class Bar {

    private int taskID = -1;
    private SosigUtils plugin;
    public BossBar bar;
    public Bar(SosigUtils plugin){
        this.plugin = plugin;
    }

    public void addPlayer(Player player){
        bar.addPlayer(player);
    }
    public BossBar getBar(){
        return bar;
    }
    public void createBar(String title, double progress){
        bar = Bukkit.createBossBar("§fStarting Title", BarColor.PINK, BarStyle.SOLID);
        bar.setVisible(true);
        //edit(title, progress);
        cast();
    }
    public void edit(String title, double progress){
        bar.setTitle(title);
        bar.setProgress(progress);
        //bar.setVisible(true);
    }

    public void cast(){
        taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable(){

            int count = -1;
            double progress = 1.0;
            double time = 1.0/60; //nach 60 sekunden wird die bossbar leer sein
            // für 5 pandas dann 1.0/5 machen ig
            @Override
            public void run() {
                bar.setProgress(progress);
                switch (count){
                    case -1:
                        break;
                    case 0:
                        bar.setColor(BarColor.PINK);
                        bar.setTitle("§d§lTwitch.tv/sosig808");
                        break;
                    case 1:
                        bar.setColor(BarColor.RED);
                        bar.setTitle("§d§lYouTube.com/@NoticeTheSosig");
                        break;
                    default:
                        bar.setColor(BarColor.RED);
                        bar.setTitle("§4§lDefault Case");
                }
                progress = progress - time;
                if (progress <= 0){
                    count++;
                    progress = 1.0;
                }
            }
        }, 0, 20);
    }


}
