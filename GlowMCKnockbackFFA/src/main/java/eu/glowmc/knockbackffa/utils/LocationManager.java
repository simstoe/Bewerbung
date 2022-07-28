package eu.glowmc.knockbackffa.utils;

/*
 * Copyright © 2021 HttpSimon. All Rights Reserved.
 * Created: 2021 / 21:08
 */

import eu.glowmc.knockbackffa.KnockBackService;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class LocationManager {
    private HashMap<String, Location> locationHashMap;

    public void load() {
        locationHashMap = new HashMap<>();

        if (getLocation("spawn") != null) {
            locationHashMap.put("spawn", getLocation("spawn"));
        }
    }

    public void setLocation(String locationName, Location location) {
        if (getLocation(locationName) == null) {
            KnockBackService.getInstance().getDriverManager().update("INSERT INTO Knockback_Locations(NAME, WORLD, X, Y, Z, YAW, PITCH) VALUES('"
                    + locationName + "', '"
                    + location.getWorld().getName() + "', '"
                    + location.getBlockX() + "', '"
                    + location.getBlockY() + "', '"
                    + location.getBlockZ() + "', '"
                    + location.getYaw() + "', "
                    + location.getPitch() + ");");
            locationHashMap.put(locationName, getLocation(locationName));
        }
    }

    public Location getLocation(String locationName) {
        try {
            ResultSet resultSet = KnockBackService.getInstance().getDriverManager().resultSet("SELECT * FROM Knockback_Locations WHERE NAME='" + locationName + "';");
            if (resultSet.next()) {
                return new Location(Bukkit.getWorld(resultSet.getString("WORLD")),
                        resultSet.getDouble("X"),
                        resultSet.getDouble("Y"),
                        resultSet.getDouble("Z"),
                        resultSet.getFloat("YAW"),
                        resultSet.getFloat("PITCH"));
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return null;
    }

    public HashMap<String, Location> getLocationHashMap() {
        return locationHashMap;
    }
}