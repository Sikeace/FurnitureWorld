package com.furnitureworld.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.furnitureworld.config.DbConfig;
import com.furnitureworld.model.FurnitureModel; // Represents a user

/**
 * RegisterService handles the business logic for user registration.
 * It interacts with the database to add new user accounts.
 */
public class RegisterService {

    private Connection dbConn; // Database connection object

    /**
     * Constructor for RegisterService.
     * Initializes the database connection.
     */
    public RegisterService() {
        try {
            this.dbConn = DbConfig.getDbConnection(); // Get database connection
        } catch (SQLException | ClassNotFoundException ex) {
            // Handle errors during database connection setup
            System.err.println("Database connection error: " + ex.getMessage());
            ex.printStackTrace();
            // dbConn will remain null if connection fails
        }
    }

    /**
     * Adds a new user to the database.
     * New users are registered as non-administrators by default.
     *
     * @param user A {@link FurnitureModel} object containing the details of the user to be registered.
     *             The password in this model should already be encrypted.
     * @return {@code Boolean.TRUE} if the user was successfully added to the database.
     *         {@code Boolean.FALSE} if the user was not added (e.g., username/email already exists,
     *                              though this specific check isn't implemented here and relies on DB constraints).
     *         {@code null} if a database connection error occurred or an SQL exception was thrown.
     */
    public Boolean addFurniture(FurnitureModel user) { 
        // Check if the database connection is available
        if (dbConn == null) {
            System.err.println("Database connection is not available.");
            return null; 
        }

        // SQL query to insert a new user into the 'user' table
        String insertQuery = "INSERT INTO user (full_name, email, username, password, is_Admin) "
                + "VALUES (?, ?, ?, ?, ?)";

        // Try-with-resources to ensure PreparedStatement is closed automatically
        try (PreparedStatement insertStmt = dbConn.prepareStatement(insertQuery)) {

            // Set the parameters for the insert query from the user model
            insertStmt.setString(1, user.getFull_name());
            insertStmt.setString(2, user.getEmail());
            insertStmt.setString(3, user.getUsername());
            insertStmt.setString(4, user.getPassword());
            insertStmt.setBoolean(5, false); 
            return insertStmt.executeUpdate() > 0; 
        } catch (SQLException e) {
            // Handle SQL exceptions 
            System.err.println("Error during registration: " + e.getMessage());
            e.printStackTrace();
           
            return null; 
        }
    }
}