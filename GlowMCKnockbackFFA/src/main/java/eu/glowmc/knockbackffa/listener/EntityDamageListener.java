package eu.glowmc.knockbackffa.listener;

/*
 * Copyright © 2021 HttpSimon. All Rights Reserved.
 * Created: 2021 / 21:15
 */

import eu.glowmc.knockbackffa.KnockBackService;
import eu.glowmc.knockbackffa.types.HeightType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class EntityDamageListener implements Listener {
    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();

            if (KnockBackService.getInstance().getHeightManager().getHeightCache().containsKey(HeightType.HIT)) {
                if (event.getCause() != EntityDamageEvent.DamageCause.FALL)
                    event.setCancelled(player.getLocation().getY() >= KnockBackService.getInstance().getHeightManager().getHeightCache().get(HeightType.HIT));
                else {
                    event.setCancelled(true);
                }
            }
        }
    }
}
