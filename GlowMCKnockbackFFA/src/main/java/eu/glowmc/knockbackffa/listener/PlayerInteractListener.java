package eu.glowmc.knockbackffa.listener;

/*
 * Copyright © 2021 HttpSimon. All Rights Reserved.
 * Created: 2021 / 21:18
 */

import eu.glowmc.knockbackffa.KnockBackService;
import eu.glowmc.knockbackffa.types.HeightType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class PlayerInteractListener implements Listener {
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        if (KnockBackService.getInstance().getHeightManager().getHeightCache().containsKey(HeightType.HIT)) {
            event.setCancelled(player.getLocation().getY() >= KnockBackService.getInstance().getHeightManager().getHeightCache().get(HeightType.HIT));
        }
    }
}
