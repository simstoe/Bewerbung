package eu.glowmc.knockbackffa;

/*
 * Copyright © 2021 HttpSimon. All Rights Reserved.
 * Created: 2021 / 21:04
 */

import eu.glowmc.knockbackffa.types.StatisticType;
import eu.glowmc.knockbackffa.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class KnockBackPlayer {
    private final Player player;
    public KnockBackPlayer(Player player) {
        this.player = player;
    }

    public void setDefaults() {
        if (KnockBackService.getInstance().getLocationManager().getLocationHashMap().get("spawn") != null) {
            player.teleport(KnockBackService.getInstance().getLocationManager().getLocationHashMap().get("spawn"));

            player.getInventory().clear();
            player.getInventory().setArmorContents(null);

            player.getInventory().setItem(0, new ItemBuilder(Material.IRON_SWORD).addEnchant(Enchantment.DAMAGE_ALL, 1).build());
            player.getInventory().setItem(1, new ItemBuilder(Material.SANDSTONE).setAmount(64).build());
            player.getInventory().setItem(2, new ItemBuilder(Material.STICK).addEnchant(Enchantment.KNOCKBACK, 1).build());
            player.getInventory().setItem(3, new ItemBuilder(Material.WEB).build());
            player.getInventory().setItem(5, new ItemBuilder(Material.BOW).build());
            player.getInventory().setItem(6, new ItemBuilder(Material.ARROW).setAmount(16).build());
            player.getInventory().setItem(8, new ItemBuilder(Material.ENDER_PEARL).build());

            player.getInventory().setHelmet(new ItemBuilder(Material.CHAINMAIL_HELMET).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2).build());
            player.getInventory().setChestplate(new ItemBuilder(Material.CHAINMAIL_CHESTPLATE).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2).build());
            player.getInventory().setLeggings(new ItemBuilder(Material.CHAINMAIL_LEGGINGS).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2).build());
            player.getInventory().setBoots(new ItemBuilder(Material.CHAINMAIL_BOOTS).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2).build());
        } else {
            player.sendMessage(KnockBackService.getPrefix() + "Der Spawn wurde noch nicht gesetzt! §a/setup");
        }
    }

    public void setScoreboard() {
        Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        Objective objective = scoreboard.getObjective("§aGlowMC §8┃ §7KnockBackFFA") == null ? scoreboard.registerNewObjective("§aGlowMC §8┃ §7KnockBackFFA", "bbb") : scoreboard.getObjective("§aGlowMC §8┃ §7KnockBackFFA");

        objective.setDisplaySlot(DisplaySlot.SIDEBAR);

        if (player.getScoreboard() == null) {
            objective.setDisplayName("§aGlowMC §8┃ §7KnockBackFFA");
        }

        Team coins = scoreboard.getTeam("c") == null ? scoreboard.registerNewTeam("c") : scoreboard.getTeam("c");
        coins.setPrefix("§8➥ §a0");
        coins.addEntry("§a");

        Team map = scoreboard.getTeam("m") == null ? scoreboard.registerNewTeam("m") : scoreboard.getTeam("m");
        if (KnockBackService.getInstance().getMapManager().getMapNameArrayList().size() == 0)
            map.setPrefix("§8➥ §anull");
        else
            map.setPrefix("§8➥ §a" + KnockBackService.getInstance().getMapManager().getMapNameArrayList().get(0));
        map.addEntry("§m");

        Team kills = scoreboard.getTeam("k") == null ? scoreboard.registerNewTeam("k") : scoreboard.getTeam("k");
        kills.setPrefix("§8➥ §a" + KnockBackService.getInstance().getStatisticManager().getStatistics(player, StatisticType.KILLS));
        kills.addEntry("§l");

        objective.getScore("§1").setScore(10);
        objective.getScore("§7Coins§8:").setScore(9);
        objective.getScore("§a").setScore(8);
        objective.getScore("§2").setScore(7);
        objective.getScore("§7Map§8:").setScore(6);
        objective.getScore("§m").setScore(5);
        objective.getScore("§4").setScore(4);
        objective.getScore("§7Kills§8:").setScore(3);
        objective.getScore("§l").setScore(2);
        objective.getScore("§a          ").setScore(1);

        player.setScoreboard(scoreboard);
    }

    public void updateScoreboard() {
        Scoreboard scoreboard = player.getScoreboard();

        Team coins = scoreboard.getTeam("c") == null ? scoreboard.registerNewTeam("c") : scoreboard.getTeam("c");
        coins.setPrefix("§8➥ §a0");
        coins.addEntry("§a");

        Team map = scoreboard.getTeam("m") == null ? scoreboard.registerNewTeam("m") : scoreboard.getTeam("m");
        try {
            map.setPrefix("§8➥ §a" + KnockBackService.getInstance().getMapManager().getMapNameArrayList().get(0));
        } catch (Exception exception) {
            map.setPrefix("§8➥ §aNull");
        }
        map.addEntry("§m");

        Team kills = scoreboard.getTeam("k") == null ? scoreboard.registerNewTeam("k") : scoreboard.getTeam("k");
        kills.setPrefix("§8➥ §a" + KnockBackService.getInstance().getStatisticManager().getStatistics(player, StatisticType.KILLS));
        kills.addEntry("§l");

        player.setScoreboard(scoreboard);
    }
}
