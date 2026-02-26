package com.oceanview.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class DatabaseConnectionPool {
    private static DatabaseConnectionPool instance;
    private static final String DB_URL = "jdbc:sqlserver://localhost:1433;databaseName=OceanViewResort_DB;encrypt=true;trustServerCertificate=true";
    private static final String DB_USER = "chamara";
    private static final String DB_PASS = "2002";
    private static final String DB_DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    private static final int POOL_SIZE = 5;

    private List<Connection> availableConnections;
    private List<Connection> usedConnections;

    private DatabaseConnectionPool() {
        availableConnections = new ArrayList<>();
        usedConnections = new ArrayList<>();
        initializePool();
    }

    public static synchronized DatabaseConnectionPool getInstance() {
        if (instance == null) {
            instance = new DatabaseConnectionPool();
        }
        return instance;
    }

    private void initializePool() {
        try {
            Class.forName(DB_DRIVER);
            for (int i = 0; i < POOL_SIZE; i++) {
                Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
                availableConnections.add(connection);
            }
            System.out.println("Database connection pool initialized with " + POOL_SIZE + " connections");
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Failed to initialize connection pool: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public synchronized Connection getConnection() {
        if (availableConnections.size() > 0) {
            Connection connection = availableConnections.remove(0);
            usedConnections.add(connection);
            return connection;
        } else {
            try {
                Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
                usedConnections.add(connection);
                return connection;
            } catch (SQLException e) {
                System.err.println("Failed to create new connection: " + e.getMessage());
                return null;
            }
        }
    }

    public synchronized void releaseConnection(Connection connection) {
        if (connection != null) {
            if (usedConnections.remove(connection)) {
                availableConnections.add(connection);
            }
        }
    }

    public void closeAllConnections() {
        for (Connection connection : availableConnections) {
            try {
                connection.close();
            } catch (SQLException e) {
                System.err.println("Failed to close connection: " + e.getMessage());
            }
        }
        for (Connection connection : usedConnections) {
            try {
                connection.close();
            } catch (SQLException e) {
                System.err.println("Failed to close connection: " + e.getMessage());
            }
        }
    }
}

