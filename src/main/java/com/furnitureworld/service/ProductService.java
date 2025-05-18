package com.furnitureworld.service;

import com.furnitureworld.model.FurnitureItemModel;
import java.util.List;
import java.util.ArrayList;

/**
 * ProductService provides methods for retrieving product information.
 * It acts as a layer between controllers and the AdminService (which handles database interactions).
 */
public class ProductService {

    // No constructor explicitly needed if AdminService is instantiated per method call.

    /**
     * Creates and returns a new instance of AdminService.
     * Note: This approach creates a new AdminService (and potentially a new DB connection)
     * for each call. For high-traffic applications, consider alternatives like
     * dependency injection or a shared AdminService instance.
     * @return A new AdminService instance.
     */
    private AdminService getAdminServiceInstance() {
        return new AdminService();
    }

    /**
     * Retrieves a list of all available products.
     * @return A List of {@link FurnitureItemModel} objects. Returns an empty list if
     *         no products are found or an error occurs.
     */
    public List<FurnitureItemModel> getAllProducts() {
        AdminService currentAdminService = getAdminServiceInstance(); // Get an AdminService instance
        List<FurnitureItemModel> products = new ArrayList<>(); // Initialize an empty list

        if (currentAdminService != null) { // Check if AdminService instantiation was successful
            // System.out.println("ProductService: Attempting to get all furniture from AdminService.");
            products = currentAdminService.getAllFurniture(); // Fetch all products via AdminService
            // System.out.println("ProductService: Received " + (products != null ? products.size() : "null list of") + " products from AdminService.");
            currentAdminService.closeConnection(); // Close the AdminService's DB connection
        } else {
            System.err.println("ProductService.getAllProducts: Failed to get AdminService instance.");
        }
        // Ensure a non-null list is always returned
        return products != null ? products : new ArrayList<>();
    }

    /**
     * Retrieves a specific product by its ID.
     * @param productIdStr The ID of the product as a String.
     * @return The {@link FurnitureItemModel} object if found, or null if not found,
     *         the ID is invalid, or an error occurs.
     */
    public FurnitureItemModel getProductById(String productIdStr) {
        AdminService currentAdminService = getAdminServiceInstance(); // Get an AdminService instance
        FurnitureItemModel product = null; // Initialize product to null

        if (currentAdminService != null) {
            try {
                int id = Integer.parseInt(productIdStr); // Convert product ID string to integer
                // System.out.println("ProductService: Attempting to get product by ID: " + id);
                product = currentAdminService.getFurnitureById(id); // Fetch product by ID via AdminService
                // System.out.println("ProductService: Received product: " + (product != null ? product.getFurniture_name() : "null"));
            } catch (NumberFormatException e) {
                // Handle cases where productIdStr is not a valid integer
                System.err.println("ProductService: Invalid product ID format: " + productIdStr);
            } finally {
                currentAdminService.closeConnection(); // Ensure connection is closed
            }
        } else {
            System.err.println("ProductService.getProductById: Failed to get AdminService instance.");
        }
        return product; // Return the found product or null
    }

    /**
     * Searches for products based on a search term.
     * If the search term is null or empty, it may return all products or an empty list
     * depending on the AdminService implementation.
     * @param searchTerm The term to search for in product names or categories.
     * @return A List of matching {@link FurnitureItemModel} objects. Returns an empty list
     *         if no matches are found or an error occurs.
     */
    public List<FurnitureItemModel> searchProducts(String searchTerm) {
        AdminService currentAdminService = getAdminServiceInstance(); // Get an AdminService instance
        List<FurnitureItemModel> products = new ArrayList<>(); // Initialize an empty list

        if (currentAdminService != null) {
            if (searchTerm != null && !searchTerm.trim().isEmpty()) {
                // If a search term is provided, search using AdminService
                // System.out.println("ProductService: Attempting to search products with term: " + searchTerm);
                products = currentAdminService.searchFurnitureByNameOrCategory(searchTerm.trim());
                // System.out.println("ProductService: Received " + (products != null ? products.size() : "null list of") + " products from search.");
            } else {
                // If search term is empty, get all products (or an empty list as per current logic)
                // System.out.println("ProductService: Search term is empty, getting all products instead.");
                products = currentAdminService.getAllFurniture();
                // System.out.println("ProductService: Received " + (products != null ? products.size() : "null list of") + " products (all).");
            }
            currentAdminService.closeConnection(); // Close the AdminService's DB connection
        } else {
             System.err.println("ProductService.searchProducts: Failed to get AdminService instance.");
        }
        // Ensure a non-null list is always returned
        return products != null ? products : new ArrayList<>();
    }
}