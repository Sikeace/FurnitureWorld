package com.furnitureworld.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.furnitureworld.config.DbConfig;
import com.furnitureworld.model.FurnitureModel; // Represents a user
import com.furnitureworld.util.PasswordUtil;   // For password operations

/**
 * Handles user login authentication.
 */
public class LoginService {

    private Connection dbConn; // Database connection
    private boolean isConnectionError = false; // Flag for DB connection issues

    /**
     * Constructor: Initializes database connection.
     */
    public LoginService() {
        try {
            dbConn = DbConfig.getDbConnection(); // Get connection
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
            isConnectionError = true; // Mark connection error
        }
    }

    /**
     * Authenticates a user based on username and password.
     * @param furnitureModel User object with login credentials.
     * @return True if login successful, False if credentials mismatch, null if DB error.
     */
    public Boolean loginUser(FurnitureModel furnitureModel) {
        // Check for initial connection error
        if (isConnectionError) {
            System.out.println("LoginService: Connection Error during initialization!");
            return null;
        }

        // SQL to find user by username
        String query = "SELECT username, password, is_Admin FROM user WHERE username = ?";
        try (PreparedStatement stmt = dbConn.prepareStatement(query)) {
            stmt.setString(1, furnitureModel.getUsername()); // Set username in query
            ResultSet result = stmt.executeQuery();

            if (result.next()) { // If user found
                // Set admin status from DB to the model
                furnitureModel.setIs_Admin(result.getBoolean("is_Admin"));
                // Validate the password
                return validatePassword(result, furnitureModel);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null; // Return null on SQL error
        }

        // User not found
        return false;
    }

    /**
     * Validates the provided password against the stored encrypted password.
     * @param result ResultSet containing user data from DB.
     * @param furnitureModel User object with plain-text password from login form.
     * @return True if passwords match, false otherwise.
     * @throws SQLException If DB access error.
     */
    private boolean validatePassword(ResultSet result, FurnitureModel furnitureModel) throws SQLException {
        String dbUsername = result.getString("username"); // Username from DB
        String dbPassword = result.getString("password"); // Encrypted password from DB

        // Compare usernames and decrypted password with submitted password
        return dbUsername.equals(furnitureModel.getUsername())
                && PasswordUtil.decrypt(dbPassword, dbUsername).equals(furnitureModel.getPassword());
    }
}