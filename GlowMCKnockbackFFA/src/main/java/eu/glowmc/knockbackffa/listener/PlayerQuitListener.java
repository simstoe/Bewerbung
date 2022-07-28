package eu.glowmc.knockbackffa.listener;

/*
 * Copyright © 2021 HttpSimon. All Rights Reserved.
 * Created: 2021 / 21:20
 */

import eu.glowmc.knockbackffa.KnockBackPlayer;
import eu.glowmc.knockbackffa.KnockBackService;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener implements Listener {
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        event.setQuitMessage(null);

        KnockBackPlayer knockbackPlayer = new KnockBackPlayer(player);
        knockbackPlayer.updateScoreboard();

        KnockBackService.getInstance().getStatisticManager().setFinalStatistics(player);
        KnockBackService.getInstance().getStreakManager().getStreakHashMap().remove(player);
    }
}
