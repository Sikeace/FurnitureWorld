package com.furnitureworld.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import com.furnitureworld.model.FurnitureItemModel;
import com.furnitureworld.service.ProductService;
// import com.furnitureworld.util.ImageUtil; // REMOVED - No longer needed for image folder

/**
 * Servlet controller for viewing details of a single product.
 * It fetches product information based on an ID and displays it on the view.jsp page.
 */
@WebServlet("/viewProduct") // Maps this servlet to the "/viewProduct" URL pattern
public class ViewController extends HttpServlet {
    private static final long serialVersionUID = 1L; // Unique identifier for serialization
    private ProductService productService; // Service class for product-related operations

    /**
     * Initializes the servlet.
     * Called by the servlet container when the servlet is first loaded.
     * @throws ServletException if an initialization error occurs
     */
    @Override
    public void init() throws ServletException {
        super.init(); // Always call super.init()
        productService = new ProductService(); 
        System.out.println("ViewController initialized (no images)."); 
    }

    /**
     * Handles HTTP GET requests to view a product.
     * Retrieves the product ID from the request parameters, fetches the product details,
     * and forwards the request to the view.jsp page.
     *
     * @param request  the HttpServletRequest object
     * @param response the HttpServletResponse object
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get the 'id' parameter from the request URL 
        String productIdStr = request.getParameter("id");
        FurnitureItemModel product = null;

        System.out.println("ViewController: doGet received product ID string: " + productIdStr); 

        // Check if the product ID string is not null and not empty
        if (productIdStr != null && !productIdStr.trim().isEmpty()) {
            System.out.println("ViewController: Calling productService.getProductById for: " + productIdStr); 
            // Fetch the product details using the product service
            product = productService.getProductById(productIdStr);
        }

        // Check if a product was found
        if (product != null) {
            System.out.println("ViewController: Product found: " + product.getFurniture_name()); 
            // Set the found product as a request attribute to be used in the JSP
            request.setAttribute("product", product);
        } else {
            System.out.println("ViewController: Product NOT found for ID: " + productIdStr); 
            // If product is not found, set an error message
            request.setAttribute("errorMessage", "Product not found or ID is invalid.");
        }
        // Set common contact information as a request attribute
        request.setAttribute("productContact", "To buy, contact: 9851098701");
        // Forward the request to the view.jsp page for display
        request.getRequestDispatcher("WEB-INF/pages/view.jsp").forward(request, response);
    }

    /**
     * Handles HTTP POST requests.
     * In this controller, POST requests are simply delegated to the doGet method.
     *
     * @param request  the HttpServletRequest object
     * @param response the HttpServletResponse object
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("ViewController: doPost called, delegating to doGet."); // Debug log
        doGet(request, response);
    }
 
}