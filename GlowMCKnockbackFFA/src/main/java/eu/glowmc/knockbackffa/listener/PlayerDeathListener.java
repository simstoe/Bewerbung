package eu.glowmc.knockbackffa.listener;

/*
 * Copyright © 2021 HttpSimon. All Rights Reserved.
 * Created: 2021 / 21:16
 */

import eu.glowmc.knockbackffa.KnockBackPlayer;
import eu.glowmc.knockbackffa.KnockBackService;
import eu.glowmc.knockbackffa.types.StatisticType;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerDeathListener implements Listener {
    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        if (event.getEntity() != null) {
            Player player = event.getEntity();
            KnockBackPlayer knockbackPlayer = new KnockBackPlayer(player);

            event.setDeathMessage(null);
            event.getDrops().clear();

            KnockBackService.getInstance().getStatisticManager().increaseStatistics(player, StatisticType.DEATHS);
            KnockBackService.getInstance().getStreakManager().resetStreak(player);
            Bukkit.getScheduler().scheduleSyncDelayedTask(KnockBackService.getInstance(), () -> player.spigot().respawn(), 1);
            Bukkit.getScheduler().scheduleSyncDelayedTask(KnockBackService.getInstance(), knockbackPlayer::setDefaults, 2);
            Bukkit.getScheduler().scheduleSyncDelayedTask(KnockBackService.getInstance(), () -> player.playSound(player.getLocation(), Sound.NOTE_BASS, 10, 10), 3);
            knockbackPlayer.updateScoreboard();

            if (player.getKiller() != null) {
                KnockBackPlayer knockbackKiller = new KnockBackPlayer(player.getKiller());

                KnockBackService.getInstance().getStreakManager().increaseStreak(player.getKiller());
                KnockBackService.getInstance().getStatisticManager().increaseStatistics(player.getKiller(), StatisticType.KILLS);
                knockbackKiller.updateScoreboard();

                player.getKiller().sendMessage(KnockBackService.getPrefix() + "Du hast §a" + player.getDisplayName() + " §7getötet!");
                player.sendMessage(KnockBackService.getPrefix() + "Du wurdest von §a" + player.getKiller().getDisplayName() + " §7getötet!");
                player.getKiller().setHealth(20);
                return;
            }

            player.sendMessage(KnockBackService.getPrefix() + "Du bist gestorben!");
        }
    }
}
