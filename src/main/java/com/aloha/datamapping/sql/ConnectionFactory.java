package com.aloha.datamapping.sql;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionFactory {

    public static Connection getConnection(DBConfig config) {
        try {
            Class.forName(config.getDriverClassName());
            return DriverManager.getConnection(config.getUrl(), config.getUser(), config.getPassword());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
