package eu.glowmc.knockbackffa.utils;

/*
 * Copyright © 2021 HttpSimon. All Rights Reserved.
 * Created: 2021 / 21:08
 */

import eu.glowmc.knockbackffa.KnockBackService;
import eu.glowmc.knockbackffa.types.HeightType;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class HeightManager {
    private HashMap<HeightType, Double> heightCache;

    public void load() {
        heightCache = new HashMap<>();

        for (HeightType heightType : HeightType.values()) {
            if (getHeight(heightType) != null) {
                heightCache.put(heightType, getHeight(heightType));
            }
        }
    }

    public void setHeight(HeightType heightType, double height) {
        if (getHeight(heightType) == null)
            KnockBackService.getInstance().getDriverManager().update("INSERT INTO Knockback_Heights(NAME, Y) VALUES('" + heightType.name() + "', '" + height + "');");
        heightCache.put(heightType, getHeight(heightType));
    }

    private Double getHeight(HeightType heightType) {
        try {
            ResultSet resultSet = KnockBackService.getInstance().getDriverManager().resultSet("SELECT * FROM Knockback_Heights WHERE NAME='" + heightType.name() + "';");
            if (resultSet.next()) {
                return resultSet.getDouble("Y");
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return null;
    }

    public HashMap<HeightType, Double> getHeightCache() {
        return heightCache;
    }
}
