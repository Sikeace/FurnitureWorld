package com.furnitureworld.controller;

import jakarta.servlet.ServletException;
// import jakarta.servlet.annotation.MultipartConfig; // REMOVED
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
// import jakarta.servlet.http.Part;              // REMOVED
import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.furnitureworld.model.FurnitureItemModel;
import com.furnitureworld.model.FurnitureModel;
import com.furnitureworld.service.AdminService;
// import com.furnitureworld.util.ImageUtil;         // REMOVED
import com.furnitureworld.util.SessionUtil;

/**
 * Servlet controller for handling administrator actions.
 * Manages CRUD operations for furniture items and displays admin dashboard data.
 */
@WebServlet("/admin")
// @MultipartConfig(...) // REMOVED
public class AdminController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private AdminService adminService;
    // private ImageUtil imageUtil; // REMOVED

    /**
     * Initializes the servlet.
     * Called by the servlet container to indicate that the servlet is being placed into service.
     * @throws ServletException if an exception occurs that interferes with the servlet's normal operation
     */
    @Override
    public void init() throws ServletException {
        super.init(); 
        adminService = new AdminService(); // Initialize the admin service
        System.out.println("AdminController initialized.");
    }

    /**
     * Handles HTTP GET requests.
     * Displays the admin dashboard, including a list of furniture items and various statistics.
     * Supports searching for furniture items.
     *
     * @param request  the HttpServletRequest object that contains the request the client made of the servlet
     * @param response the HttpServletResponse object that contains the response the servlet returns to the client
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get username from session
        String username = (String) SessionUtil.getAttribute(request, "username");


        // Get search term from request parameters
        String searchTerm = request.getParameter("searchTerm");
        List<FurnitureItemModel> furnitureList;

        // If a search term is provided, search furniture; otherwise, get all furniture
        if (searchTerm != null && !searchTerm.trim().isEmpty()) {
            furnitureList = adminService.searchFurnitureByNameOrCategory(searchTerm.trim()); 
            request.setAttribute("searchTerm", searchTerm); 
            request.setAttribute("isSearchResult", true); 
        } else {
            furnitureList = adminService.getAllFurniture();
            request.setAttribute("isSearchResult", false); 
        }

        // Get various statistics for the admin dashboard
        int totalCategories = adminService.getTotalCategories();
        int totalFurniture = adminService.getTotalFurnitureItems();
        float totalInventoryValue = adminService.getTotalInventoryValue();
        int totalUsers = adminService.getTotalUsers();
        int totalAdmins = adminService.getTotalAdmins();
        List<Map<String, Object>> categories = adminService.getAllCategories(); // Get all categories for dropdowns

        // Set attributes for the JSP to display
        request.setAttribute("totalCategories", totalCategories);
        request.setAttribute("totalFurniture", totalFurniture);
        request.setAttribute("totalInventoryValue", totalInventoryValue);
        request.setAttribute("totalUsers", totalUsers);
        request.setAttribute("totalAdmins", totalAdmins);
        request.setAttribute("furnitureList", furnitureList);
        request.setAttribute("categories", categories);

        // Forward the request to the admin JSP page
        request.getRequestDispatcher("WEB-INF/pages/admin.jsp").forward(request, response);
    }


    /**
     * Handles HTTP POST requests.
     * Performs actions like creating, updating, or deleting furniture items based on the 'action' parameter.
     *
     * @param request  the HttpServletRequest object that contains the request the client made of the servlet
     * @param response the HttpServletResponse object that contains the response the servlet returns to the client
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get the action parameter from the request (e.g., "create", "update", "delete")
        String action = request.getParameter("action");

        // Get username from session
        String username = (String) SessionUtil.getAttribute(request, "username");
        if (username == null || username.trim().isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/login"); 
            return;
        }
        boolean success = false; 
        String operation = "";   
        String message = null;   

        try {
            if ("create".equals(action)) {
                operation = "create";
                // Get parameters for creating a new furniture item
                String name = request.getParameter("furniture_name");
                int categoryId = Integer.parseInt(request.getParameter("category_id"));
                float price = Float.parseFloat(request.getParameter("price"));
                String description = request.getParameter("description");

                // Call service to create furniture
                success = adminService.createFurniture(name, categoryId, price, description);
                message = success ? "Furniture item created successfully." : "Failed to create furniture item.";

            } else if ("update".equals(action)) {
                operation = "update";
                // Get parameters for updating an existing furniture item
                int id = Integer.parseInt(request.getParameter("furniture_id"));
                String name = request.getParameter("furniture_name");
                int categoryId = Integer.parseInt(request.getParameter("category_id"));
                float price = Float.parseFloat(request.getParameter("price"));
                String description = request.getParameter("description");
                
                // Call service to update furniture
                success = adminService.updateFurniture(id, name, categoryId, price, description); 
                message = success ? "Furniture item updated successfully." : "Failed to update furniture item.";

            } else if ("delete".equals(action)) {
                operation = "delete";
                // Get ID for deleting a furniture item
                int id = Integer.parseInt(request.getParameter("furniture_id"));
                // Call service to delete furniture
                success = adminService.deleteFurniture(id);
                message = success ? "Furniture item deleted successfully." : "Failed to delete furniture item.";
            } else {
                message = "Unknown action performed."; 
            }
        } catch (NumberFormatException e) {
            // Handle errors if numeric parameters are not valid numbers
            success = false;
            message = "Invalid number format provided for " + operation + ".";
        } catch (Exception e) {
            // Handle any other unexpected errors
            e.printStackTrace(); // Log the exception
            success = false;
            message = "An unexpected error occurred during " + operation + ".";
        }

        // Set a success or error message in the session to be displayed on the next page
        if (message != null) {
            request.getSession().setAttribute(success ? "adminSuccessMessage" : "adminErrorMessage", message);
        }
    }
    @Override
    public void destroy() {
        // Close database connection if adminService is not null
        if (adminService != null) {
            adminService.closeConnection();
        }
        System.out.println("AdminController destroyed.");
        super.destroy(); // Always call super.destroy()
    }
}