package com.furnitureworld.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import com.furnitureworld.model.FurnitureItemModel;
import com.furnitureworld.service.ProductService;
// import com.furnitureworld.util.ImageUtil; // REMOVED - No longer needed for image folder

@WebServlet(name = "ProductController", urlPatterns = {"/product", "/searchProducts"})
public class ProductController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ProductService productService;

    @Override
    public void init() throws ServletException {
        super.init();
        productService = new ProductService();
        System.out.println("ProductController initialized (no images).");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getServletPath();
        List<FurnitureItemModel> productList;
        String searchTerm = request.getParameter("searchTerm");

        System.out.println("ProductController: doGet received action: " + action + ", searchTerm: " + searchTerm);

        if ("/searchProducts".equals(action) && searchTerm != null && !searchTerm.trim().isEmpty()) {
            System.out.println("ProductController: Calling productService.searchProducts for term: " + searchTerm);
            productList = productService.searchProducts(searchTerm);
            request.setAttribute("searchTerm", searchTerm);
        } else {
            System.out.println("ProductController: Calling productService.getAllProducts.");
            productList = productService.getAllProducts();
            if (searchTerm != null) { 
                 request.setAttribute("searchTerm", searchTerm);
            }
        }
        System.out.println("ProductController: Product list size to be set in request: " + (productList != null ? productList.size() : "null"));
        request.setAttribute("products", productList);
        request.getRequestDispatcher("WEB-INF/pages/product.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("ProductController: doPost called, delegating to doGet.");
        doGet(request, response);
    }


}