package com.msgsched.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import static java.util.Objects.isNull;

public class ConnectionDB {
    private static HikariDataSource dataSource;

    static {

        try {
            Properties props = new Properties();
            try (InputStream in = ConnectionDB.class
                    .getClassLoader()
                    .getResourceAsStream("application.properties")) {

                if (isNull(in)) {
                    throw new RuntimeException("No se encontró application.properties en /resources.");
                }
                props.load(in);
            }

            String url  = props.getProperty("db.url");
            String user = props.getProperty("db.user");
            String pass = props.getProperty("db.pass");

            HikariConfig config = new HikariConfig();
            config.setJdbcUrl(url);
            config.setUsername(user);
            config.setPassword(pass);
            config.setMaximumPoolSize(10);
            config.setMinimumIdle(2);
            config.setIdleTimeout(600000);
            config.setConnectionTimeout(30000);
            config.setMaxLifetime(1800000);
            config.setPoolName("MyConnectionsPool");
            config.setAutoCommit(true);
            dataSource = new HikariDataSource(config);
        } catch (Exception e) {
            throw new RuntimeException("Error inicializando conexión a DB: ", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
}
