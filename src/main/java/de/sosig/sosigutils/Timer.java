package de.sosig.sosigutils;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;

import org.bukkit.entity.Player;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class Timer {
    private ScheduledExecutorService executorService = Executors.newScheduledThreadPool(0);
    public boolean paused;
    public int elapsedTime;

    public void start(Player player) {
        executorService.scheduleAtFixedRate(() -> {
            if (!paused){
                elapsedTime += 1;
            }
            int weeks = (elapsedTime / 604800);
            int days = (elapsedTime / 86400) % 7;
            int hours = (elapsedTime / 3600) % 24;
            int minutes = (elapsedTime / 60) % 60;
            int seconds = (elapsedTime) % 60;

            //StringBuilder message = new StringBuilder("§d§lTimer: §f");
            StringBuilder message = new StringBuilder("§d§l");
            if (weeks > 0){
                message.append(String.format("%02d", weeks)).append("w ");
            }
            if (weeks > 0 || days > 0){
                message.append(String.format("%02d", days)).append("d ");
            }
            if (days > 0 || weeks > 0 || hours > 0){
                message.append(String.format("%02d", hours)).append("h ");
            }
            if (hours > 0 || days > 0 || weeks > 0 || minutes > 0){
                message.append(String.format("%02d", minutes)).append("m ");
            }
            //if (seconds > 0){
            //    message.append(String.format("%02d", seconds)).append("s ");
            //}
            message.append(String.format("%02d", seconds)).append("s ");
            if (paused){
                message.append(" §5§l(pause)");
            }
            IsendActIonBar(player, message.toString());
        }, 0, 1, TimeUnit.SECONDS);
    }

    public void stop(){
        executorService.shutdownNow();
    }

    public void pause(){
        this.paused = true;
    }
    public void resume(){
        this.paused = false;
    }
    public void set(int inputTime) {
        elapsedTime = inputTime;
    }

    private void IsendActIonBar(Player player, String message) {
        player.sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(message));
    }
}
