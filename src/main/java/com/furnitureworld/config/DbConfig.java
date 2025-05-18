package com.furnitureworld.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Manages database connection settings and provides a way to connect.
 */
public class DbConfig {

    // --- Database Details ---
    // Name of the database to connect to
    private static final String DB_NAME = "furniture world";
    // Connection URL for MySQL. Includes server, port, and database name.
    private static final String URL = "jdbc:mysql://localhost:3306/" + DB_NAME;
    // Username for database access
    private static final String USERNAME = "root";
    // Password for database access (empty in this case)
    private static final String PASSWORD = "";

    /**
     * Creates and returns a connection to the database.
     *
     * @return A Connection object if successful.
     * @throws SQLException If a database error occurs (e.g., wrong credentials, DB down).
     * @throws ClassNotFoundException If the MySQL JDBC driver isn't found.
     */
    public static Connection getDbConnection() throws SQLException, ClassNotFoundException {
        // Load the MySQL JDBC driver class
        Class.forName("com.mysql.cj.jdbc.Driver");
        // Attempt to establish a connection using the URL, username, and password
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }
}