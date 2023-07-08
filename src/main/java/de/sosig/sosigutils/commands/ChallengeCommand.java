package de.sosig.sosigutils.commands;

import de.sosig.sosigutils.SosigUtils;
import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ChallengeCommand implements CommandExecutor {
    //float MAX_PROGRESS = 1.0F
    //public Bar bar;
    public org.bukkit.boss.BossBar bar;

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

        if (!(commandSender instanceof Player)) return false;
        Player player = (Player) commandSender;
        SosigUtils main = SosigUtils.getMain();
        //bar = new Bar(main);

        if (strings.length == 3){
            if (strings[0].equals("bossbar")){
                if (strings[1].equals("create")){
                    bar = Bukkit.createBossBar("§fStarting Title", BarColor.PINK, BarStyle.SOLID);
                    bar.setVisible(true);
                    bar.setProgress(1.0);
                    //bar.setTitle("TEST");
                    //strings[2]
                    //player.showBossBar(bossBar);
                }
                if (strings[1].equals("hide")){
                    bar.setVisible(false);
                    //player.hideBossBar(player.getBo);
                    return true;
                }
                if (strings[1].equals("setname")){
                    bar.setTitle(strings[2]);
                }
                if (strings[1].equals("setprogress")){
                    try
                    {
                        bar.setProgress(Double.parseDouble(strings[2]));
                        return true;
                    } catch (NumberFormatException ex)
                    {
                        return false;
                    }
                }
                //bossBar = BossBar.bossBar(Component.text(bossbarName).color(NamedTextColor.LIGHT_PURPLE), 0.0f, BossBar.Color.PINK, BossBar.Overlay.PROGRESS);
                //player.showBossBar(bossBar);
            }
        }


        //final int[] countDown = {10};
        //new BukkitRunnable(){
        //    @Override
        //    public void run() {
        //        countDown[0]--;
        //        if (countDown[0] <= 0 || countDownBar.progress() - 0.1f <= 0.0f) {
        //            player.hideBossBar(countDownBar);//remove the bossbar
        //            cancel(); //cancels the current task
        //        }
        //        countDownBar.progress(countDownBar.progress() - 0.1f);
        //        countDownBar.name(Component.text("Countdown: " + countDown[0]).color(TextColor.color(countDown[0] <= 5 ? NamedTextColor.RED : NamedTextColor.WHITE)));
        //        player.sendMessage("Boss bar countdown complete.");
        //    }
        //}.runTaskTimerAsynchronously(main, 0, 20);


        return false;
    }
}
