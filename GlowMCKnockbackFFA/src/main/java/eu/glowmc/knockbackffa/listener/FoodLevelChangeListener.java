package eu.glowmc.knockbackffa.listener;

/*
 * Copyright © 2021 HttpSimon. All Rights Reserved.
 * Created: 2021 / 21:16
 */

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;

public class FoodLevelChangeListener implements Listener {
    @EventHandler
    public void onFoodLevelChange(FoodLevelChangeEvent event) {
        event.setCancelled(true);
    }
}
