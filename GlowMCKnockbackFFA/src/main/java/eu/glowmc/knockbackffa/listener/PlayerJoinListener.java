package eu.glowmc.knockbackffa.listener;

/*
 * Copyright © 2021 HttpSimon. All Rights Reserved.
 * Created: 2021 / 20:52
 */

import eu.glowmc.knockbackffa.KnockBackPlayer;
import eu.glowmc.knockbackffa.KnockBackService;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        KnockBackPlayer knockbackPlayer = new KnockBackPlayer(player);

        event.setJoinMessage(null);
        knockbackPlayer.setDefaults();
        knockbackPlayer.setScoreboard();
        player.playSound(player.getLocation(), Sound.BURP, 10, 10);

        Bukkit.getOnlinePlayers().forEach(all -> {
            KnockBackPlayer knockbackAll = new KnockBackPlayer(all);
            knockbackAll.updateScoreboard();
        });
    }
}
