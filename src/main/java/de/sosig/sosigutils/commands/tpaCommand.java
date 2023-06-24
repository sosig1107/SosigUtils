package de.sosig.sosigutils.commands;

import de.sosig.sosigutils.SosigUtils;
import net.kyori.adventure.text.ComponentBuilder;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.HashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class tpaCommand implements CommandExecutor {

    private ScheduledExecutorService executorService = Executors.newScheduledThreadPool(0);
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

        if (!(commandSender instanceof Player)) return false;
        Player requester = (Player) commandSender;
        SosigUtils main = SosigUtils.getMain();





        if (strings.length == 1){
            Player player2 = Bukkit.getPlayer(strings[0]);
            if (player2 != null && player2.isOnline()){
                if (!requester.getName().equals(player2.getName())) {
                    main.getTpa().put(player2, requester);

                    Bukkit.getScheduler().runTaskLater(SosigUtils.getMain(), () -> {
                        main.getTpa().remove(player2);
                        requester.sendMessage("§ctpa ist abgelaufen");
                    }, 200);

                    //executorService.scheduleAtFixedRate(() -> {
                    //    main.getTpa().remove(player2);
                    //    requester.sendMessage("§ctpa ist abgelaufen");
                    //    executorService.shutdownNow();
                    //}, 10, 0, TimeUnit.SECONDS);
                    requester.sendMessage("§eDie tpa wurde versendet und läuft in 10 sekunden ab.");
                    TextComponent txt = new TextComponent();
                    txt.setText("§e> clicke hier um die tpa von §a" + requester.getName() + "§e anzunehmen");
                    txt.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/tpaccept"));
                    player2.sendMessage(txt);
                    //if (!main.getTpa().containsKey(player2)){
                    //    main.getTpa().put(player2, requester);
                    //    executorService.scheduleAtFixedRate(() -> {
                    //        main.getTpa().remove(player2);
                    //        requester.sendMessage("§ctpa ist abgelaufen");
                    //        executorService.shutdownNow();
                    //    }, 10, 0, TimeUnit.SECONDS);
                    //    requester.sendMessage("§eDie tpa wurde versendet und läuft in 10 sekunden ab.");
                    //    TextComponent txt = new TextComponent();
                    //    txt.setText("§e> clicke hier um die tpa von §a" + requester.getName() + "§e anzunehmen");
                    //    //txt.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT)); //"clicke zum akzeptieren"
                    //    txt.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/tpaccept"));
                    //    player2.sendMessage(txt);
                    //    //p.sendMessage();
                    //}else {
                    //    requester.sendMessage("§cIch weiß nicht... was muss hier für ne nachricht hin, das tutorial ist auf spanisch???");
                    //}
                }else {
                    requester.sendMessage("§cnaja... kannst dich ja nicht zu dir selber teleportieren vom ding her...");
                }
            }else {
                requester.sendMessage("§cSpieler nicht gefunden");
            }
        }else {
            requester.sendMessage("§c/tpa [spieler]");
        }

        return false;
    }
}
