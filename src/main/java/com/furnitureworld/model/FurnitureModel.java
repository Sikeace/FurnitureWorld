package com.furnitureworld.model;

/**
 * Represents a user account in the furniture application.
 * This class holds user details such as ID, full name, email, username, password,
 * and an administrator status flag.
 */
public class FurnitureModel {
    private int user_id;        // Unique identifier for the user
    private String full_name;   // User's full name
    private String email;       // User's email address
    private String username;    // User's login username
    private String password;    // User's (typically encrypted) password
    private boolean is_Admin;   // Flag indicating if the user is an administrator (true) or regular user (false)

    /**
     * Default constructor.
     */
    public FurnitureModel() {}

    /**
     * Parameterized constructor to initialize a FurnitureModel object with all fields.
     * Used for retrieving full user details from the database.
     * @param user_id The ID of the user.
     * @param full_name The full name of the user.
     * @param email The email address of the user.
     * @param username The username of the user.
     * @param password The password of the user.
     * @param is_Admin The admin status of the user.
     */
    public FurnitureModel(int user_id, String full_name, String email, String username, String password, boolean is_Admin) {
        super(); // Call to the superclass (Object) constructor
        this.user_id = user_id;
        this.full_name = full_name;
        this.email = email;
        this.username = username;
        this.password = password;
        this.is_Admin = is_Admin; // Sets the admin status
    }

    /**
     * Constructor used primarily for user registration.
     * Initializes a new user with full name, email, username, and password.
     * The 'is_Admin' status defaults to false for new registrations.
     * @param full_name The full name of the user.
     * @param email The email address of the user.
     * @param username The username for the new account.
     * @param password The password for the new account.
     */
    public FurnitureModel(String full_name, String email, String username, String password){
        super();
        this.full_name = full_name;
        this.email = email;
        this.username = username;
        this.password = password;
        // is_Admin will be false by default for this constructor, suitable for new user registration
    }

    /**
     * Constructor used primarily for user login.
     * Initializes a user object with only username and password for authentication purposes.
     * @param username The username provided for login.
     * @param password The password provided for login.
     */
    public FurnitureModel(String username, String password ) {
        super();
        this.username = username;
        this.password = password;
        // Other fields (full_name, email, is_Admin) would be populated after successful login if needed
    }

    // Getter and setter methods for accessing and modifying the private fields of the FurnitureModel.

    public int getUser_id() {
        return user_id;
    }
    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getFull_name() {
        return full_name;
    }
    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Checks if the user is an administrator.
     * The method name 'isIs_Admin' follows JavaBean naming conventions for boolean getters (isPropertyName).
     * @return true if the user is an administrator, false otherwise.
     */
    public boolean isIs_Admin() {
        return is_Admin;
    }
    public void setIs_Admin(boolean is_Admin) {
        this.is_Admin = is_Admin;
    }
}