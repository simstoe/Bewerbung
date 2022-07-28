package eu.glowmc.knockbackffa.utils;

/*
 * Copyright © 2021 HttpSimon. All Rights Reserved.
 * Created: 2021 / 21:10
 */

import eu.glowmc.knockbackffa.KnockBackService;
import eu.glowmc.knockbackffa.types.StatisticType;
import org.bukkit.entity.Player;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class StatisticManager {
    private HashMap<Player, Integer> kills;
    private HashMap<Player, Integer> deaths;

    private ArrayList<Player> registeredArrayList;

    public void load() {
        kills = new HashMap<>();
        deaths = new HashMap<>();
        registeredArrayList = new ArrayList<>();
    }

    public void increaseStatistics(Player player, StatisticType statisticType) {
        switch (statisticType) {
            case KILLS:
                if (kills.containsKey(player))
                    kills.put(player, Math.addExact(getStatistics(player, statisticType), 1));
                else
                    kills.put(player, 1);
                break;
            case DEATHS:
                if (deaths.containsKey(player))
                    deaths.put(player, Math.addExact(getStatistics(player, statisticType), 1));
                else
                    deaths.put(player, 1);
                break;
        }
    }

    public void setFinalStatistics(Player player) {
        if (playerIsRegistered(player)) {
            ResultSet resultSet =KnockBackService.getInstance().getDriverManager().resultSet("SELECT * FROM Knockback_Statistics WHERE UUID='" + player.getUniqueId() + "'");

            try {
                if (resultSet.next()) {
                    if (kills.containsKey(player))
                        KnockBackService.getInstance().getDriverManager().update("UPDATE Knockback_Statistics SET KILLS='" + getStatistics(player, StatisticType.KILLS) + "' WHERE UUID='" + player.getUniqueId() + "'");
                    if (deaths.containsKey(player))
                        KnockBackService.getInstance().getDriverManager().update("UPDATE Knockback_Statistics SET DEATHS='" + getStatistics(player, StatisticType.DEATHS) + "' WHERE UUID='" + player.getUniqueId() + "'");
                }
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        } else {
            KnockBackService.getInstance().getDriverManager().update("INSERT INTO Knockback_Statistics(UUID, KILLS, DEATHS) VALUES('" + player.getUniqueId() + "', '0', '0')");
            setFinalStatistics(player);
        }
    }

    public boolean playerIsRegistered(Player player) {
        if (!registeredArrayList.contains(player)) {
            ResultSet resultSet = KnockBackService.getInstance().getDriverManager().resultSet("SELECT * FROM Knockback_Statistics WHERE UUID='" + player.getUniqueId() + "'");

            try {
                if (resultSet.next()) {
                    if (resultSet.getString("UUID") != null) {
                        registeredArrayList.add(player);
                        return true;
                    }
                }
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        } else {
            return true;
        }
        return false;
    }

    public int getStatistics(Player player, StatisticType statisticType) {
        switch (statisticType) {
            case DEATHS:
                if (deaths.containsKey(player)) {
                    return deaths.get(player);
                } else {
                    if (playerIsRegistered(player)) {
                        ResultSet resultSet = KnockBackService.getInstance().getDriverManager().resultSet("SELECT * FROM Knockback_Statistics WHERE UUID='" + player.getUniqueId() + "'");

                        try {
                            if (resultSet.next()) {
                                deaths.put(player, resultSet.getInt("DEATHS"));
                                return deaths.get(player);
                            }
                        } catch (SQLException exception) {
                            exception.printStackTrace();
                        }
                    } else {
                        KnockBackService.getInstance().getDriverManager().update("INSERT INTO Knockback_Statistics(UUID, KILLS, DEATHS) VALUES('" + player.getUniqueId() + "', '0', '0')");
                        getStatistics(player, statisticType);
                    }
                }
            case KILLS:
                if (kills.containsKey(player)) {
                    return kills.get(player);
                } else {
                    if (playerIsRegistered(player)) {
                        ResultSet resultSet = KnockBackService.getInstance().getDriverManager().resultSet("SELECT * FROM Knockback_Statistics WHERE UUID='" + player.getUniqueId() + "'");

                        try {
                            if (resultSet.next()) {
                                kills.put(player, resultSet.getInt("KILLS"));
                                return kills.get(player);
                            }
                        } catch (SQLException exception) {
                            exception.printStackTrace();
                        }
                    } else {
                        KnockBackService.getInstance().getDriverManager().update("INSERT INTO Knockback_Statistics(UUID, KILLS, DEATHS) VALUES('" + player.getUniqueId() + "', '0', '0')");
                        getStatistics(player, statisticType);
                    }
                }
        }
        return 0;
    }
}
