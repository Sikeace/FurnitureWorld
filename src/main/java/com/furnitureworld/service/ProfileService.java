package com.furnitureworld.service;

import com.furnitureworld.config.DbConfig;
import com.furnitureworld.model.FurnitureModel; // Represents a user
import com.furnitureworld.util.PasswordUtil; // Utility for password encryption/decryption

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * ProfileService provides methods for managing user profile information,
 * such as retrieving user details, updating profile data, and changing passwords.
 */
public class ProfileService {
    // Logger for logging service-level messages and errors
    private static final Logger LOGGER = Logger.getLogger(ProfileService.class.getName());

    /**
     * Retrieves the full details of a user based on their username.
     * Includes fetching the stored (encrypted) password.
     * @param username The username of the user whose details are to be fetched.
     * @return A {@link FurnitureModel} object populated with user details if found,
     *         otherwise null.
     */
    public FurnitureModel getUserDetails(String username) {
        // Validate input: username should not be null or empty
        if (username == null || username.trim().isEmpty()) {
            LOGGER.warning("GetUserDetails: Username is null or empty.");
            return null;
        }
        // SQL query to select all relevant user fields
        String sql = "SELECT user_id, full_name, email, username, password, is_Admin FROM user WHERE username = ?";

        // Try-with-resources to ensure Connection and PreparedStatement are closed
        try (Connection conn = DbConfig.getDbConnection(); // Get DB connection
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username.trim()); // Set the username parameter
            LOGGER.info("Srv: Executing getUserDetails for username: [" + username.trim() + "]");
            try (ResultSet rs = stmt.executeQuery()) { // Execute query
                if (rs.next()) { // If a user is found
                    // Create and populate a FurnitureModel object
                    FurnitureModel user = new FurnitureModel();
                    user.setUser_id(rs.getInt("user_id"));
                    user.setFull_name(rs.getString("full_name"));
                    user.setEmail(rs.getString("email"));
                    user.setUsername(rs.getString("username"));
                    user.setPassword(rs.getString("password")); // Store the encrypted password
                    user.setIs_Admin(rs.getBoolean("is_Admin"));
                    LOGGER.info("Srv: User details found for: ["+username.trim()+"]");
                    return user;
                } else {
                    LOGGER.warning("Srv: No user found with username: [" + username.trim() + "]");
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            // Log any exceptions during database operation
            LOGGER.log(Level.SEVERE, "Srv: Error fetching user details for " + username.trim(), e);
        }
        return null; // Return null if user not found or an error occurred
    }

    /**
     * Updates the full name and email address of a user.
     * @param username The username of the user whose profile is to be updated.
     * @param newFullName The new full name for the user.
     * @param newEmail The new email address for the user.
     * @return true if the profile was updated successfully, false otherwise.
     */
    public boolean updateUserProfile(String username, String newFullName, String newEmail) {
         // Validate input parameters
         if (username == null || username.trim().isEmpty() ||
            newFullName == null || newFullName.trim().isEmpty() ||
            newEmail == null || newEmail.trim().isEmpty()) {
            LOGGER.warning("Srv: UpdateUserProfile: One or more parameters are null or empty. Username: ["+username+"]");
            return false;
        }
        // SQL query to update user's full name and email
        String sql = "UPDATE user SET full_name = ?, email = ? WHERE username = ?";
        try (Connection conn = DbConfig.getDbConnection(); // Get DB connection
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, newFullName.trim()); // Set new full name
            stmt.setString(2, newEmail.trim());   // Set new email
            stmt.setString(3, username.trim());   // Set username for WHERE clause

            LOGGER.info("Srv: Executing updateUserProfile for username: [" + username.trim() + "] with fullName: [" + newFullName.trim() + "], email: [" + newEmail.trim() + "]");
            int rowsAffected = stmt.executeUpdate(); // Execute update
            LOGGER.info("Srv: Profile update Rows affected: " + rowsAffected);

            if (rowsAffected > 0) { // If one or more rows were updated
                LOGGER.info("Srv: Profile updated successfully for " + username.trim());
                return true;
            } else {
                // This can happen if the username doesn't exist or the new data is the same as existing data
                LOGGER.warning("Srv: Profile update FAILED for " + username.trim() + ". User not found or data unchanged.");
                return false;
            }
        } catch (SQLException | ClassNotFoundException e) {
            // Log any exceptions
            LOGGER.log(Level.SEVERE, "Srv: Error updating user profile for " + username.trim(), e);
        }
        return false; // Return false on error
    }

    /**
     * Changes a user's password after verifying the current password.
     * @param username The username of the user.
     * @param currentPasswordPlain The user's current password in plain text.
     * @param newPasswordPlain The new password for the user in plain text.
     * @return true if the password was changed successfully, false otherwise
     *         (e.g., current password incorrect, user not found, DB error).
     */
    public boolean changePassword(String username, String currentPasswordPlain, String newPasswordPlain) {
        // Validate input parameters
        if (username == null || username.trim().isEmpty() ||
            currentPasswordPlain == null || currentPasswordPlain.isEmpty() ||
            newPasswordPlain == null || newPasswordPlain.isEmpty()) {
            LOGGER.warning("Srv: ChangePassword: One or more parameters are null or empty. Username: [" + username +"]");
            return false;
        }

        // Step 1: Get current user details (including stored encrypted password)
        FurnitureModel user = getUserDetails(username.trim());
        if (user == null) {
            LOGGER.warning("Srv: ChangePassword: User not found for password change: [" + username.trim() + "]");
            return false;
        }
        String storedEncryptedPassword = user.getPassword();
        if (storedEncryptedPassword == null || storedEncryptedPassword.trim().isEmpty()) {
            LOGGER.severe("Srv: ChangePassword: Stored password for user ["+username.trim()+"] is NULL or EMPTY. Cannot verify.");
            return false;
        }

        // Step 2: Decrypt the stored password and verify it against the provided current password
        String decryptedStoredPassword = PasswordUtil.decrypt(storedEncryptedPassword, username.trim());
        if (decryptedStoredPassword == null) {
            // This might happen if decryption fails (e.g., wrong salt/key, algorithm issue)
            LOGGER.warning("Srv: ChangePassword: Failed to decrypt stored password for user [" + username.trim() + "]. StoredEnc: "+storedEncryptedPassword);
            return false;
        }
        if (!currentPasswordPlain.equals(decryptedStoredPassword)) {
            LOGGER.warning("Srv: ChangePassword: Current password does not match for user [" + username.trim() + "].");
            return false; // Current password incorrect
        }
        LOGGER.info("Srv: ChangePassword: Current password verified for user ["+username.trim()+"].");

        // Step 3: Encrypt the new password
        String newEncryptedPassword = PasswordUtil.encrypt(username.trim(), newPasswordPlain);
        if (newEncryptedPassword == null) {
            LOGGER.severe("Srv: ChangePassword: Failed to encrypt new password for user [" + username.trim() + "].");
            return false;
        }

        // Step 4: Update the password in the database
        String sql = "UPDATE user SET password = ? WHERE username = ?";
        try (Connection conn = DbConfig.getDbConnection(); // Get DB connection
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, newEncryptedPassword); // Set the new encrypted password
            stmt.setString(2, username.trim());       // Set username for WHERE clause

            LOGGER.info("Srv: Executing changePassword (DB update) for username: [" + username.trim() + "]");
            int rowsAffected = stmt.executeUpdate(); // Execute update
            LOGGER.info("Srv: ChangePassword update Rows affected: " + rowsAffected);

            if (rowsAffected > 0) { // If update was successful
                LOGGER.info("Srv: Password changed successfully in DB for " + username.trim());
                return true;
            } else {
                LOGGER.warning("Srv: Password change DB update FAILED for " + username.trim() + " (user might not exist, though checked earlier).");
                return false;
            }
        } catch (SQLException | ClassNotFoundException e) {
            // Log any exceptions during DB update
            LOGGER.log(Level.SEVERE, "Srv: Error during password DB update for " + username.trim(), e);
        }
        return false; // Return false on error
    }
}