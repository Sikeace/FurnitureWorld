package com.furnitureworld.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.furnitureworld.config.DbConfig;
import com.furnitureworld.model.FurnitureModel; // Represents a user
import com.furnitureworld.model.FurnitureItemModel; // Represents a furniture item

/**
 * AdminService provides business logic for administrator functionalities.
 * This includes managing furniture items (CRUD operations), fetching dashboard statistics,
 * and retrieving user information.
 */
public class AdminService {
    private Connection dbConn; // Database connection object

    /**
     * Constructor for AdminService.
     * Initializes the database connection.
     */
    public AdminService() {
        try {
            this.dbConn = DbConfig.getDbConnection(); 
            if (this.dbConn != null && !this.dbConn.isClosed()) {
              
            } else {
                System.err.println("AdminService: DbConfig.getDbConnection() returned NULL or a CLOSED connection during initialization.");
                this.dbConn = null; 
            }
        } catch (SQLException | ClassNotFoundException ex) {
            // Handle exceptions during connection setup
            System.err.println("AdminService: Database connection error during initialization: " + ex.getMessage());
            ex.printStackTrace();
            this.dbConn = null; 
        }
    }

    /**
     * Closes the database connection if it is open.
     */
    public void closeConnection() {
        if (this.dbConn != null) {
            try {
                if (!this.dbConn.isClosed()) {
                    this.dbConn.close(); // Close the connection
                }
            } catch (SQLException e) {
                System.err.println("AdminService: Error closing database connection: " + e.getMessage());
            }
            this.dbConn = null; // Set to null after closing
        }
    }

    /**
     * Gets the total number of furniture categories.
     * @return The total count of categories, or 0 if an error occurs or no connection.
     */
     public int getTotalCategories() {
        String query = "SELECT COUNT(*) FROM furniture_category";
        return executeCountQuery(query, "Error fetching total categories");
    }

    /**
     * Gets the total number of furniture items.
     * @return The total count of furniture items, or 0 if an error occurs or no connection.
     */
    public int getTotalFurnitureItems() {
        String query = "SELECT COUNT(*) FROM furniture";
        return executeCountQuery(query, "Error fetching total furniture items");
    }

    /**
     * Calculates the total inventory value (sum of prices of all furniture items).
     * @return The total inventory value, or 0.0f if an error occurs or no connection.
     */
     public float getTotalInventoryValue() {
        String query = "SELECT SUM(price) FROM furniture";
        if (dbConn == null) {
            System.err.println("AdminService: Error fetching total inventory value - Database connection is NOT AVAILABLE.");
            return 0.0f;
        }
        try (PreparedStatement stmt = dbConn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getFloat(1); // Get the sum
            }
        } catch (SQLException e) {
            System.err.println("AdminService: Error fetching total inventory value - SQL error: " + e.getMessage());
            e.printStackTrace();
        }
        return 0.0f; 
    }

    /**
     * Gets the total number of registered users.
     * @return The total count of users, or 0 if an error occurs or no connection.
     */
     public int getTotalUsers() {
        String query = "SELECT COUNT(*) FROM user"; 
        return executeCountQuery(query, "Error fetching total users");
    }

    /**
     * Gets the total number of users who are administrators.
     * @return The total count of admin users, or 0 if an error occurs or no connection.
     */
    public int getTotalAdmins() {
        String query = "SELECT COUNT(*) FROM user WHERE is_Admin = TRUE"; 
        return executeCountQuery(query, "Error fetching total admins");
    }

    /**
     * Gets a map of furniture categories and the count of items in each category.
     * @return A Map where keys are category names and values are the counts of items.
     */
    public Map<String, Integer> getFurnitureItemsByCategory() {
        Map<String, Integer> itemsByCategory = new HashMap<>();
        if (dbConn == null) {
            System.err.println("AdminService: Error fetching furniture items by category - Database connection is NOT AVAILABLE.");
            return itemsByCategory; // Return empty map if no connection
        }
        String query = "SELECT fc.category_name, COUNT(f.furniture_id) AS total_items " +
                       "FROM furniture f " +
                       "JOIN furniture_category fc ON f.category_id = fc.category_id " +
                       "GROUP BY fc.category_name";
        try (PreparedStatement stmt = dbConn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                itemsByCategory.put(rs.getString("category_name"), rs.getInt("total_items"));
            }
        } catch (SQLException e) {
            System.err.println("AdminService: Error fetching furniture items by category - SQL error: " + e.getMessage());
            e.printStackTrace();
        }
        return itemsByCategory;
    }

    /**
     * Retrieves user details based on the username.
     * Password is not fetched for security reasons.
     * @param username The username to search for.
     * @return A FurnitureModel (user) object if found, otherwise null.
     */
    public FurnitureModel getUserByUsername(String username) {
        if (dbConn == null) {
            System.err.println("AdminService.getUserByUsername: Database connection is NOT AVAILABLE.");
            return null; // Return null if no connection
        }
        String query = "SELECT user_id, full_name, email, username, is_Admin FROM user WHERE username = ?";
        try (PreparedStatement stmt = dbConn.prepareStatement(query)) {
            stmt.setString(1, username); // Set the username parameter
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    // Populate FurnitureModel with user data
                    FurnitureModel user = new FurnitureModel();
                    user.setUser_id(rs.getInt("user_id"));
                    user.setFull_name(rs.getString("full_name"));
                    user.setEmail(rs.getString("email"));
                    user.setUsername(rs.getString("username"));
                    user.setIs_Admin(rs.getBoolean("is_Admin"));
                    return user;
                }
            }
        } catch (SQLException e) {
            System.err.println("AdminService.getUserByUsername: Error fetching user details for username " + username + ": " + e.getMessage());
            e.printStackTrace();
        }
        return null; // Return null if user not found or error occurs
    }


    /**
     * Helper method to execute a SQL query that returns a single integer count.
     * @param query The SQL query to execute (e.g., SELECT COUNT(*)...).
     * @param errorMsg The error message to log if an SQL exception occurs.
     * @return The count, or -1 if connection is unavailable, or 0 if query fails or returns no result.
     */
    private int executeCountQuery(String query, String errorMsg) {
        if (dbConn == null) {
            System.err.println("AdminService: " + errorMsg + " - Database connection is NOT AVAILABLE.");
            return -1; // Indicate connection error
        }
        try (PreparedStatement stmt = dbConn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1); 
            }
        } catch (SQLException e) {
            System.err.println("AdminService: " + errorMsg + " - SQL error: " + e.getMessage());
            e.printStackTrace();
        }
        return 0; 
    }

    /**
     * Retrieves a list of all furniture categories.
     * @return A List of Maps, where each Map represents a category with "category_id" and "category_name".
     */
    public List<Map<String, Object>> getAllCategories() {
        List<Map<String, Object>> categoryList = new ArrayList<>();
        if (dbConn == null) {
            System.err.println("AdminService.getAllCategories: Database connection is NOT AVAILABLE.");
            return categoryList;
        }
        String query = "SELECT category_id, category_name FROM furniture_category ORDER BY category_name";
        try (PreparedStatement stmt = dbConn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Map<String, Object> category = new HashMap<>();
                category.put("category_id", rs.getInt("category_id"));
                category.put("category_name", rs.getString("category_name"));
                categoryList.add(category);
            }
        } catch (SQLException e) {
            System.err.println("AdminService.getAllCategories: Error fetching categories: " + e.getMessage());
            e.printStackTrace();
        }
        return categoryList;
    }


    /**
     * Creates a new furniture item in the database.
     * @param name The name of the furniture.
     * @param categoryId The ID of the category.
     * @param price The price of the furniture.
     * @param description The description of the furniture.
     * @return true if the furniture item was created successfully, false otherwise.
     */
    public boolean createFurniture(String name, int categoryId, float price, String description) { 
        if (dbConn == null) return false; // Cannot operate without a database connection
        String query = "INSERT INTO furniture (furniture_name, category_id, price, description) VALUES (?, ?, ?, ?)"; 
        try (PreparedStatement stmt = dbConn.prepareStatement(query)) {
            stmt.setString(1, name);
            stmt.setInt(2, categoryId);
            stmt.setFloat(3, price);
            stmt.setString(4, description);
            return stmt.executeUpdate() > 0; 
        } catch (SQLException e) {
            e.printStackTrace(); 
        }
        return false; // Return false on error
    }

    /**
     * Retrieves a list of all furniture items from the database, joined with their category names.
     * @return A List of FurnitureItemModel objects.
     */
    public List<FurnitureItemModel> getAllFurniture() {
        List<FurnitureItemModel> furnitureList = new ArrayList<>();
        if (dbConn == null) return furnitureList; // Return empty list if no connection
        String query = "SELECT f.furniture_id, f.furniture_name, f.price, f.category_id, fc.category_name, f.description " + 
                       "FROM furniture f " +
                       "JOIN furniture_category fc ON f.category_id = fc.category_id " +
                       "ORDER BY f.furniture_id DESC"; 
        try (PreparedStatement stmt = dbConn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                // Create and populate FurnitureItemModel for each row
                FurnitureItemModel furniture = new FurnitureItemModel();
                furniture.setFurniture_id(rs.getInt("furniture_id"));
                furniture.setFurniture_name(rs.getString("furniture_name"));
                furniture.setPrice(rs.getFloat("price"));
                furniture.setCategory_id(rs.getInt("category_id"));
                furniture.setCategory_name(rs.getString("category_name"));
                furniture.setDescription(rs.getString("description"));
                
                furnitureList.add(furniture); // Add to the list
            }
        } catch (SQLException e) {
            e.printStackTrace(); 
        }
        return furnitureList;
    }

    /**
     * Searches for furniture items by name or category name.
     * The search is case-insensitive.
     * @param searchTerm The term to search for in furniture names or category names.
     * @return A List of matching FurnitureItemModel objects.
     */
    public List<FurnitureItemModel> searchFurnitureByNameOrCategory(String searchTerm) {
        List<FurnitureItemModel> furnitureList = new ArrayList<>();
        if (dbConn == null) return furnitureList; // Return empty list if no connection
        // SQL query for searching by furniture name or category name
        String query = "SELECT f.furniture_id, f.furniture_name, f.price, f.category_id, fc.category_name, f.description " + 
                       "FROM furniture f " +
                       "JOIN furniture_category fc ON f.category_id = fc.category_id " +
                       "WHERE LOWER(f.furniture_name) LIKE LOWER(?) OR LOWER(fc.category_name) LIKE LOWER(?) " + // Case-insensitive search
                       "ORDER BY f.furniture_name ASC"; 
        try (PreparedStatement stmt = dbConn.prepareStatement(query)) {
            String likeTerm = "%" + searchTerm.trim() + "%"; 
            stmt.setString(1, likeTerm);
            stmt.setString(2, likeTerm);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    // Create and populate FurnitureItemModel for each matching row
                    FurnitureItemModel furniture = new FurnitureItemModel();
                    furniture.setFurniture_id(rs.getInt("furniture_id"));
                    furniture.setFurniture_name(rs.getString("furniture_name"));
                    furniture.setPrice(rs.getFloat("price"));
                    furniture.setCategory_id(rs.getInt("category_id"));
                    furniture.setCategory_name(rs.getString("category_name"));
                    furniture.setDescription(rs.getString("description"));
                    furnitureList.add(furniture);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); 
        }
        return furnitureList;
    }

    /**
     * Retrieves a specific furniture item by its ID.
     * @param id The ID of the furniture item to retrieve.
     * @return A FurnitureItemModel object if found, otherwise null.
     */
    public FurnitureItemModel getFurnitureById(int id) {
        FurnitureItemModel furniture = null;
        if (dbConn == null) return null; // Return null if no connection
        // SQL query to get a furniture item by its ID
        String query = "SELECT f.furniture_id, f.furniture_name, f.category_id, f.price, fc.category_name, f.description " + 
                       "FROM furniture f " +
                       "JOIN furniture_category fc ON f.category_id = fc.category_id " +
                       "WHERE f.furniture_id = ?";
        try (PreparedStatement stmt = dbConn.prepareStatement(query)) {
            stmt.setInt(1, id); // Set the ID parameter
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    // Populate FurnitureItemModel if found
                    furniture = new FurnitureItemModel();
                    furniture.setFurniture_id(rs.getInt("furniture_id"));
                    furniture.setFurniture_name(rs.getString("furniture_name"));
                    furniture.setCategory_id(rs.getInt("category_id"));
                    furniture.setPrice(rs.getFloat("price"));
                    furniture.setCategory_name(rs.getString("category_name"));
                    furniture.setDescription(rs.getString("description"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); 
        }
        return furniture; // Return the found item or null
    }

    /**
     * Updates an existing furniture item in the database.
     * @param id The ID of the furniture item to update.
     * @param name The new name for the furniture.
     * @param categoryId The new category ID.
     * @param price The new price.
     * @param description The new description.
     * @return true if the update was successful, false otherwise.
     */
    public boolean updateFurniture(int id, String name, int categoryId, float price, String description) { 
        if (dbConn == null) return false; 
        // SQL query to update a furniture item
        String query = "UPDATE furniture SET furniture_name = ?, category_id = ?, price = ?, description = ? WHERE furniture_id = ?"; 
        try (PreparedStatement stmt = dbConn.prepareStatement(query)) {
            stmt.setString(1, name);
            stmt.setInt(2, categoryId);
            stmt.setFloat(3, price);
            stmt.setString(4, description);
            stmt.setInt(5, id); 
            return stmt.executeUpdate() > 0; 
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; 
    }

    /**
     * Deletes a furniture item from the database.
     * @param id The ID of the furniture item to delete.
     * @return true if the deletion was successful, false otherwise.
     */
    public boolean deleteFurniture(int id) {
        if (dbConn == null) return false; 
        String query = "DELETE FROM furniture WHERE furniture_id = ?"; 
        try (PreparedStatement stmt = dbConn.prepareStatement(query)) {
            stmt.setInt(1, id); 
            return stmt.executeUpdate() > 0; 
        } catch (SQLException e) {
            e.printStackTrace(); 
        }
        return false; 
    }
}