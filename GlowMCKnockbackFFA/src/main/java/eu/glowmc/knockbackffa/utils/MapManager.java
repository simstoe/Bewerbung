package eu.glowmc.knockbackffa.utils;

/*
 * Copyright © 2021 HttpSimon. All Rights Reserved.
 * Created: 2021 / 21:09
 */

import eu.glowmc.knockbackffa.KnockBackPlayer;
import eu.glowmc.knockbackffa.KnockBackService;
import org.bukkit.Bukkit;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class MapManager {
    private ArrayList<String> mapNameArrayList;

    public void load() {
        mapNameArrayList = new ArrayList<>();

        if (getMapName() != null) {
            mapNameArrayList.add(0, getMapName());
        }
    }

    private String getMapName() {
        ResultSet resultSet = KnockBackService.getInstance().getDriverManager().resultSet("SELECT * FROM Knockback_MapName");
        try {
            if (resultSet.next()) {
                return resultSet.getString("MAPNAME");
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return null;
    }

    public void setMapName(String mapName) {
        if (!mapNameArrayList.contains(mapName)) {
            KnockBackService.getInstance().getDriverManager().update("INSERT INTO Knockback_MapName(MAPNAME) VALUES('" + mapName + "')");
            mapNameArrayList.add(0, mapName);

            Bukkit.getOnlinePlayers().forEach(all -> {
                KnockBackPlayer knockbackPlayer = new KnockBackPlayer(all);
                knockbackPlayer.updateScoreboard();
            });
        }
    }

    public ArrayList<String> getMapNameArrayList() {
        return mapNameArrayList;
    }
}
