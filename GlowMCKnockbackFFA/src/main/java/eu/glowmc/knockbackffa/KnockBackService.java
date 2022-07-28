package eu.glowmc.knockbackffa;

/*
 * Copyright © 2021 HttpSimon. All Rights Reserved.
 * Created: 2021 / 20:52
 */

import eu.glowmc.knockbackffa.commands.SetupCommandExecutor;
import eu.glowmc.knockbackffa.commands.StatsCommandExecutor;
import eu.glowmc.knockbackffa.listener.*;
import eu.glowmc.knockbackffa.sql.DriverManager;
import eu.glowmc.knockbackffa.utils.*;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class KnockBackService extends JavaPlugin {
    private static KnockBackService instance;
    private static String prefix;

    private DriverManager driverManager;

    private HeightManager heightManager;
    private LocationManager locationManager;
    private MapManager mapManager;
    private StatisticManager statisticManager;
    private StreakManager streakManager;

    @Override
    public void onEnable() {
        instance = this;
        prefix = "§8● §aGlowMC §8┃ §7";

        driverManager = new DriverManager("localhost","knockback","knockback","sCp_wuOkZU5P@f_5");

        heightManager = new HeightManager();
        locationManager = new LocationManager();
        mapManager = new MapManager();
        statisticManager = new StatisticManager();
        streakManager = new StreakManager();

        driverManager.load();

        driverManager.update("CREATE TABLE IF NOT EXISTS Knockback_Heights(NAME VARCHAR(16), Y double);");
        driverManager.update("CREATE TABLE IF NOT EXISTS Knockback_Statistics(UUID VARCHAR(128), KILLS int, DEATHS int);");
        driverManager.update("CREATE TABLE IF NOT EXISTS Knockback_Locations(NAME VARCHAR(16), WORLD VARCHAR(64), X double, Y double, Z double, YAW float, PITCH float)");
        driverManager.update("CREATE TABLE IF NOT EXISTS Knockback_MapName(MAPNAME VARCHAR(16))");

        heightManager.load();
        locationManager.load();
        mapManager.load();
        statisticManager.load();
        streakManager.load();

        Bukkit.getPluginManager().registerEvents(new BlockBreakListener(), this);
        Bukkit.getPluginManager().registerEvents(new BlockPlaceListener(), this);
        Bukkit.getPluginManager().registerEvents(new EntityDamageListener(), this);
        Bukkit.getPluginManager().registerEvents(new FoodLevelChangeListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerDeathListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerDropItemListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerInteractListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerJoinListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerMoveListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerPickupItemListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerQuitListener(), this);
        Bukkit.getPluginManager().registerEvents(new WeatherChangeListener(), this);

        getCommand("setup").setExecutor(new SetupCommandExecutor());
        getCommand("stats").setExecutor(new StatsCommandExecutor());
    }

    @Override
    public void onDisable() {

    }
    public static KnockBackService getInstance() {
        return instance;
    }

    public static String getPrefix() {
        return prefix;
    }

    public DriverManager getDriverManager() {
        return driverManager;
    }

    public LocationManager getLocationManager() {
        return locationManager;
    }

    public MapManager getMapManager() {
        return mapManager;
    }

    public StatisticManager getStatisticManager() {
        return statisticManager;
    }

    public StreakManager getStreakManager() {
        return streakManager;
    }

    public HeightManager getHeightManager() {
        return heightManager;
    }
}
