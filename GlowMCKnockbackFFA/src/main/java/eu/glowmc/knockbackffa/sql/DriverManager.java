package eu.glowmc.knockbackffa.sql;

/*
 * Copyright © 2021 HttpSimon. All Rights Reserved.
 * Created: 2021 / 20:56
 */

import eu.glowmc.knockbackffa.KnockBackService;
import org.bukkit.Bukkit;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DriverManager {
    private final String host;
    private final String username;
    private final String database;
    private final String password;

    private Connection connection;

    public DriverManager(String host, String username, String database, String password) {
        this.host = host;
        this.username = username;
        this.database = database;
        this.password = password;
    }

    public void load() {
        if (isConnected())
            return;

        try {
            connection = java.sql.DriverManager.getConnection("jdbc:mysql://" + host + ":3306/" + database + "?autoReconnect=true", username, password);
            Bukkit.getConsoleSender().sendMessage(KnockBackService.getPrefix() + "Successfully connected to jdbc:mysql://" + host + ":3306/" + database + "!");
        } catch (SQLException exception) {
            Bukkit.getConsoleSender().sendMessage(KnockBackService.getPrefix() + "Can't connect to MySQL database!");
        }
    }

    public void update(String query) {
        if (isConnected()) {
            try {
                connection.prepareStatement(query).executeUpdate(query);
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        }
    }

    public ResultSet resultSet(String query) {
        try {
            return connection.prepareStatement(query).executeQuery();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return null;
    }

    public boolean isConnected() {
        return connection != null;
    }

    public Connection getConnection() {
        return connection;
    }
}
