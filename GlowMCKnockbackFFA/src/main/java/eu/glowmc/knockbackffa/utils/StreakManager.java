package eu.glowmc.knockbackffa.utils;

/*
 * Copyright © 2021 HttpSimon. All Rights Reserved.
 * Created: 2021 / 21:11
 */

import eu.glowmc.knockbackffa.KnockBackService;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class StreakManager {
    private final int[] streakToBroadcast = new int[]{
            1, 5, 10, 15, 20, 25, 30, 35, 40, 45, 50, 55, 60, 65, 70, 75, 80,
            85, 90, 95, 100, 105, 110, 115, 120, 125, 130, 135, 140, 145,
            150, 155, 160, 165, 170, 175, 180, 185, 190, 195, 200
    };
    private HashMap<Player, Integer> streakHashMap;

    public void load() {
        streakHashMap = new HashMap<>();
    }

    public void increaseStreak(Player player) {
        if (streakHashMap.get(player) != null) {
            streakHashMap.put(player, streakHashMap.get(player) + 1);
            player.setLevel(streakHashMap.get(player));

            for (int count : streakToBroadcast) {
                if (streakHashMap.get(player) == count) {
                    Bukkit.getOnlinePlayers().forEach(all -> {
                        all.sendMessage(KnockBackService.getPrefix() + "Der Spieler §a" + player.getDisplayName() + " §7hat einen Killstreak von §a"+ streakHashMap.get(player) + " §7Kills!");
                    });
                    player.playSound(player.getLocation(), Sound.ENDERDRAGON_DEATH, 10, 10);
                    return;
                }
            }
            player.playSound(player.getLocation(), Sound.LEVEL_UP, 10, 10);
            return;
        }

        streakHashMap.put(player, 1);
        player.setLevel(streakHashMap.get(player));
    }

    public void resetStreak(Player player) {
        streakHashMap.remove(player);
        player.setLevel(0);
    }

    public HashMap<Player, Integer> getStreakHashMap() {
        return streakHashMap;
    }
}
