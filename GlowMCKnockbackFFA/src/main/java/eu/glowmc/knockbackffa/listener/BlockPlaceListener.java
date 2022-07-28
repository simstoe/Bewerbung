package eu.glowmc.knockbackffa.listener;

/*
 * Copyright © 2021 HttpSimon. All Rights Reserved.
 * Created: 2021 / 21:14
 */

import eu.glowmc.knockbackffa.KnockBackService;
import eu.glowmc.knockbackffa.types.HeightType;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockPlaceListener implements Listener {
    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        final Block placedBlock = event.getBlock();

        if (placedBlock.getType() == Material.SANDSTONE)
            Bukkit.getScheduler().scheduleSyncDelayedTask(KnockBackService.getInstance(), () -> {
                placedBlock.setType(Material.AIR);
            }, 60);

        if (KnockBackService.getInstance().getHeightManager().getHeightCache().containsKey(HeightType.HIT)) {
            event.setCancelled(placedBlock.getLocation().getY() >= KnockBackService.getInstance().getHeightManager().getHeightCache().get(HeightType.HIT));
        }
    }
}
