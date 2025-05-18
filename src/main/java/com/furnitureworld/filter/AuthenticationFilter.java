package com.furnitureworld.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import com.furnitureworld.util.CookieUtil;
import com.furnitureworld.util.SessionUtil;

/**
 * AuthenticationFilter controls access to various parts of the web application
 * based on user authentication status (logged in or not) and user roles (admin or user).
 * It intercepts all requests ("/*") and applies rules to redirect users
 * to appropriate pages or allow access.
 */
@WebFilter(asyncSupported = true, urlPatterns = "/*") // Applies to all URLs in the application
public class AuthenticationFilter implements Filter {

    // Define constants for common URL paths to improve readability and maintainability
    private static final String LOGIN = "/login";
    private static final String LOGOUT = "/logout";
    private static final String REGISTER = "/register";
    private static final String HOME = "/home";
    private static final String ROOT = "/"; // The root path of the application
    private static final String DASHBOARD = "/admin"; // Base path for admin section
    private static final String ABOUT = "/about";
    private static final String CONTACT = "/contact";
    // Product related paths
    private static final String PRODUCT = "/product";         // Product listing page
    private static final String VIEW_PRODUCT = "/viewProduct";   // Product detail page
    private static final String SEARCH_PRODUCTS = "/searchProducts"; // Product search results page
    // User profile
    private static final String PROFILE = "/profile"; // Base path for user profile pages

    /**
     * Called by the web container to indicate to a filter that it is being
     * placed into service.
     *
     * @param filterConfig A filter configuration object used by a servlet container
     *                     to pass information to a filter during initialization.
     * @throws ServletException if an error occurs during initialization.
     */
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Initialization logic can be placed here, if required.
        System.out.println("AuthenticationFilter initialized.");
    }

    /**
     * The doFilter method of the Filter is called by the container
     * each time a request/response pair is passed through the chain due
     * to a client request for a resource at the end of the chain.
     *
     * @param request  The ServletRequest object.
     * @param response The ServletResponse object.
     * @param chain    The FilterChain for invoking the next filter or the resource.
     * @throws IOException      if an I/O error occurs during processing.
     * @throws ServletException if a servlet error occurs.
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        String requestURI = req.getRequestURI(); // Full URI, e.g., /FurnitureWorld/login
        String contextPath = req.getContextPath(); // Application context, e.g., /FurnitureWorld
        String path = requestURI.substring(contextPath.length()); // Path relative to context, e.g., /login

        // Allow access to static resources (images, CSS, JavaScript, fonts) without authentication checks.
        if (requestURI.endsWith(".png") || requestURI.endsWith(".jpg") || requestURI.endsWith(".jpeg") ||
            requestURI.endsWith(".css") || requestURI.endsWith(".js") || requestURI.endsWith(".ico") ||
            requestURI.contains("/fonts/") || requestURI.contains("/assets/")) { // Added /assets/ for banner image
            chain.doFilter(request, response); // Pass the request along the filter chain
            return; // Stop further filter processing for static resources
        }

        // Check if the user is logged in by looking for a "username" attribute in the session.
        boolean isLoggedIn = SessionUtil.getAttribute(req, "username") != null;
        // Get the user's role from a cookie.
        String userRole = CookieUtil.getCookie(req, "role") != null ? CookieUtil.getCookie(req, "role").getValue()
                : null;

        // Logic for users with "admin" role
        if ("admin".equals(userRole)) {
            if (path.equals(LOGIN) || path.equals(REGISTER)) { // Admin trying to access login/register page
                res.sendRedirect(contextPath + DASHBOARD); // Redirect admin to their dashboard
            } else if (path.equals(ROOT)) { // Admin trying to access the site root
                 res.sendRedirect(contextPath + DASHBOARD); // Redirect admin to their dashboard
            } else if (path.startsWith(DASHBOARD) || // Allow access to /admin or /admin/*
                       path.equals(LOGOUT) ||        // Allow access to /logout
                       path.startsWith(PROFILE) ||   // Allow access to /profile, /profile/edit, /profile/password
                       path.equals(HOME)) {          // Allow admin to access /home if intended (e.g., to see user view)
                chain.doFilter(request, response); // Proceed with the request
            } else if (path.startsWith(ABOUT) || path.startsWith(CONTACT) ||
                       path.equals(PRODUCT) || path.equals(VIEW_PRODUCT) || path.equals(SEARCH_PRODUCTS)) {
                // Admin trying to access general user pages (about, contact, product, viewProduct, searchProducts), redirect to dashboard.
                // This keeps the admin focused on admin tasks.
                res.sendRedirect(contextPath + DASHBOARD);
            } else {
                // For any other unhandled paths for an admin, redirect to their dashboard.
                res.sendRedirect(contextPath + DASHBOARD);
            }
        }
        // Logic for users with "user" role
        else if ("user".equals(userRole)) {
            if (path.equals(LOGIN) || path.equals(REGISTER)) { // User trying to access login/register page
                res.sendRedirect(contextPath + HOME); // Redirect user to their home page
            } else if (path.startsWith(DASHBOARD)) { // User trying to access admin pages
                res.sendRedirect(contextPath + HOME); // Redirect user to their home page (access denied for admin area)
            } else if (path.equals(HOME) ||
                       path.equals(ROOT) ||          // Allow access to the site root
                       path.startsWith(ABOUT) ||     // Allow access to /about or /about/*
                       path.startsWith(CONTACT) ||   // Allow access to /contact or /contact/*
                       path.equals(PRODUCT) ||       // Allow access to /product (listing)
                       path.equals(VIEW_PRODUCT) ||  // Allow access to /viewProduct (details)
                       path.equals(SEARCH_PRODUCTS) ||// Allow access to /searchProducts (search results)
                       path.equals(LOGOUT) ||        // Allow access to /logout
                       path.startsWith(PROFILE)) {   // Allow access to /profile, /profile/edit, /profile/password
                chain.doFilter(request, response); // Proceed with the request
            } else {
                // For any other unhandled paths for a logged-in user, redirect to their home page.
                res.sendRedirect(contextPath + HOME);
            }
        }
        // Logic for users who are not logged in (guests)
        else {
            // Not logged in (guest user)
            if (path.equals(LOGIN) || path.equals(REGISTER) || path.equals(ROOT) ||
                path.equals(HOME) ||               // Allow guests to access the home page
                path.startsWith(ABOUT) ||          // Allow guests to access the about page
                path.startsWith(CONTACT) ||        // Allow guests to access the contact page
                path.equals(PRODUCT) ||            // Allow guests to access product listings
                path.equals(VIEW_PRODUCT) ||       // Allow guests to access product details
                path.equals(SEARCH_PRODUCTS) ) {   // Allow guests to access product search results
                chain.doFilter(request, response); // Proceed with the request for public pages
            } else if (path.startsWith(PROFILE) || path.equals(LOGOUT) || path.startsWith(DASHBOARD)) {
                // Guest trying to access protected areas (profile, logout, admin dashboard)
                res.sendRedirect(contextPath + LOGIN); // Redirect to login page
            }
            else {
                 // For other paths when not logged in, redirect to login.
                res.sendRedirect(contextPath + LOGIN);
            }
        }
    }

    /**
     * Called by the web container to indicate to a filter that it is being
     * taken out of service.
     */
    @Override
    public void destroy() {
        // Cleanup logic can be placed here, if required.
        System.out.println("AuthenticationFilter destroyed.");
    }
}