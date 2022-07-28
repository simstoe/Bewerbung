package eu.glowmc.knockbackffa.listener;

/*
 * Copyright © 2021 HttpSimon. All Rights Reserved.
 * Created: 2021 / 21:19
 */

import eu.glowmc.knockbackffa.KnockBackPlayer;
import eu.glowmc.knockbackffa.KnockBackService;
import eu.glowmc.knockbackffa.types.HeightType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerMoveListener implements Listener {
    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        if (KnockBackService.getInstance().getHeightManager().getHeightCache().containsKey(HeightType.DEATH)) {
            if (event.getPlayer().getLocation().getY() <= KnockBackService.getInstance().getHeightManager().getHeightCache().get(HeightType.DEATH)) {
                event.getPlayer().setHealth(0.0D);
            }
        }
    }
}
