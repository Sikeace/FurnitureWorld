package com.furnitureworld.controller;

import com.furnitureworld.model.FurnitureModel;
import com.furnitureworld.service.ProfileService;
import com.furnitureworld.util.SessionUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Servlet controller for handling user profile operations.
 * Supports viewing profile, editing profile details, and changing password.
 * Uses a single JSP (profile.jsp) and manages different views/forms using a 'mode' parameter.
 */
@WebServlet("/profile/*") // Handles URLs like /profile, /profile/edit, /profile/password
public class ProfileController extends HttpServlet {
    private static final long serialVersionUID = 1L; // Unique identifier for serialization
    private static final Logger LOGGER = Logger.getLogger(ProfileController.class.getName()); // Logger for this class
    private ProfileService profileService; // Service class for profile-related business logic

    /**
     * Initializes the servlet.
     * Called by the servlet container when the servlet is first loaded.
     * @throws ServletException if an initialization error occurs
     */
    @Override
    public void init() throws ServletException {
        super.init(); // Always call super.init()
        profileService = new ProfileService(); // Initialize the profile service
        LOGGER.info("Ctrl: ProfileController initialized (Single JSP version).");
    }

    /**
     * Handles HTTP GET requests for the profile page.
     * Determines the mode (view, edit, password) based on the URL path.
     * Fetches user details and forwards to the profile.jsp page.
     *
     * @param request  the HttpServletRequest object
     * @param response the HttpServletResponse object
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String pathInfo = request.getPathInfo(); // Gets the part of the URL after "/profile"
        // Determine mode: 'view', 'edit', 'password'
        // Default to 'view' if no pathInfo or just "/profile"
        String mode = "view";
        if (pathInfo != null) {
            if (pathInfo.equals("/edit")) {
                mode = "edit"; // Set mode to 'edit' if URL is /profile/edit
            } else if (pathInfo.equals("/password")) {
                mode = "password"; // Set mode to 'password' if URL is /profile/password
            }
        }
        LOGGER.info("Ctrl: GET Request. Mode: [" + mode + "]");

        // Get username from session
        String username = (String) SessionUtil.getAttribute(request, "username");
        // Check if user is logged in
        if (username == null) {
            LOGGER.warning("Ctrl: GET No active session. Redirecting to login.");
            response.sendRedirect(request.getContextPath() + "/login?message=session_expired_profile_get");
            return;
        }
        // Fetch user details from the service
        FurnitureModel user = profileService.getUserDetails(username);
        // Check if user details were found
        if (user == null) {
            LOGGER.severe("Ctrl: GET User details not found for [" + username + "]. Invalidating session.");
            SessionUtil.invalidateSession(request); 
            response.sendRedirect(request.getContextPath() + "/login?message=user_data_error_logout_profile");
            return;
        }

        // Set user details and current mode as request attributes for the JSP
        request.setAttribute("dbUser", user);
        request.setAttribute("profileMode", mode); 
        HttpSession session = request.getSession(false); 
        if (session != null) {
            String flashMessage = (String) session.getAttribute("flashMessage");
            String flashStatus = (String) session.getAttribute("flashStatus");
            if (flashMessage != null) {
                request.setAttribute("message", flashMessage);
                request.setAttribute("status", flashStatus);
                session.removeAttribute("flashMessage");
                session.removeAttribute("flashStatus");
            }
        }

        LOGGER.info("Ctrl: GET Forwarding to profile.jsp (mode: "+mode+") for user ["+username+"]");
        request.getRequestDispatcher("/WEB-INF/pages/profile.jsp").forward(request, response);
    }

    /**
     * Handles HTTP POST requests for profile updates or password changes.
     * Determines the action (update profile or change password) based on the URL path.
     *
     * @param request  the HttpServletRequest object
     * @param response the HttpServletResponse object
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String pathInfo = request.getPathInfo(); 
        LOGGER.info("Ctrl: POST Request. PathInfo: [" + pathInfo + "]");

        // Get username from session
        String username = (String) SessionUtil.getAttribute(request, "username");
        // Check if user is logged in
        if (username == null) {
            LOGGER.warning("Ctrl: POST No active session. Redirecting to login.");
            response.sendRedirect(request.getContextPath() + "/login?message=session_expired_profile_post");
            return;
        }
        HttpSession session = request.getSession(); 
        String redirectPathAfterPost = request.getContextPath() + "/profile"; 
        if ("/edit".equals(pathInfo)) { 
            handleUpdateProfilePost(request, username, session);
            
            if (session.getAttribute("flashStatus") != null && "error".equals(session.getAttribute("flashStatus"))) {
                 redirectPathAfterPost = request.getContextPath() + "/profile/edit"; 
            }
        } else if ("/password".equals(pathInfo)) {
            handleChangePasswordPost(request, username, session);
        
            if (session.getAttribute("flashStatus") != null && "error".equals(session.getAttribute("flashStatus"))) {
                 redirectPathAfterPost = request.getContextPath() + "/profile/password"; 
            }
        } else {
          
            LOGGER.warning("Ctrl: Unknown POST pathInfo: [" + pathInfo + "]");
            session.setAttribute("flashMessage", "Invalid operation attempted.");
            session.setAttribute("flashStatus", "error");
        }
        response.sendRedirect(redirectPathAfterPost);
    }

    /**
     * Handles the logic for updating user profile information.
     * Reads form parameters, validates them, calls the service, and sets flash messages.
     *
     * @param request  the HttpServletRequest object
     * @param username the username of the user whose profile is being updated
     * @param session  the HttpSession object to store flash messages
     */
    private void handleUpdateProfilePost(HttpServletRequest request, String username, HttpSession session) {
        // Get form parameters
        String fullName = request.getParameter("fullName");
        String email = request.getParameter("email");
        LOGGER.info("Ctrl: handleUpdateProfilePost for [" + username + "]: fullName=[" + fullName + "], email=[" + email + "]");
        
        if (fullName == null || fullName.trim().isEmpty() || email == null || email.trim().isEmpty()) {
            session.setAttribute("flashMessage", "Full name and email cannot be empty.");
            session.setAttribute("flashStatus", "error");
            LOGGER.warning("Ctrl: UpdateProfile validation failed: empty fields.");
            return;
        }
        if (!email.trim().matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
             session.setAttribute("flashMessage", "Invalid email format.");
             session.setAttribute("flashStatus", "error");
             LOGGER.warning("Ctrl: UpdateProfile validation failed: invalid email format ["+email.trim()+"].");
             return; 
        }
        boolean success = profileService.updateUserProfile(username, fullName.trim(), email.trim());
        // Set flash messages based on the success of the operation
        if (success) {
            session.setAttribute("flashMessage", "Profile updated successfully!");
            session.setAttribute("flashStatus", "success");
            LOGGER.info("Ctrl: Profile update successful for [" + username + "]");
        } else {
            session.setAttribute("flashMessage", "Failed to update profile. Please try again.");
            session.setAttribute("flashStatus", "error");
            LOGGER.warning("Ctrl: Profile update failed via service for [" + username + "]");
        }
    }

    /**
     * Handles the logic for changing a user's password.
     * Reads form parameters, validates them, calls the service, and sets flash messages.
     *
     * @param request  the HttpServletRequest object
     * @param username the username of the user whose password is being changed
     * @param session  the HttpSession object to store flash messages
     */
    private void handleChangePasswordPost(HttpServletRequest request, String username, HttpSession session) {
        // Get form parameters
        String currentPassword = request.getParameter("currentPassword");
        String newPassword = request.getParameter("newPassword");
        String confirmPassword = request.getParameter("confirmPassword");
        LOGGER.info("Ctrl: handleChangePasswordPost for [" + username + "]");

        if (currentPassword == null || currentPassword.isEmpty() ||
            newPassword == null || newPassword.isEmpty() ||
            confirmPassword == null || confirmPassword.isEmpty()) {
            session.setAttribute("flashMessage", "All password fields are required.");
            session.setAttribute("flashStatus", "error");
            LOGGER.warning("Ctrl: ChangePassword validation failed: empty fields.");
            return;
        }
        if (!newPassword.equals(confirmPassword)) {
            session.setAttribute("flashMessage", "New passwords do not match.");
            session.setAttribute("flashStatus", "error");
            LOGGER.warning("Ctrl: ChangePassword validation failed: passwords mismatch.");
            return; 
        }
        // Validate input: check new password length
        if (newPassword.length() < 8) { 
            session.setAttribute("flashMessage", "New password must be at least 8 characters long.");
            session.setAttribute("flashStatus", "error");
            LOGGER.warning("Ctrl: ChangePassword validation failed: new password too short.");
            return; // Exit early
        }
        // Validate input: check if new password is the same as the current password
         if (newPassword.equals(currentPassword)) {
            session.setAttribute("flashMessage", "New password cannot be the same as the current password.");
            session.setAttribute("flashStatus", "error");
            LOGGER.warning("Ctrl: ChangePassword validation failed: new password same as current.");
            return; // Exit early
        }
        // Call the service to change the password
        boolean success = profileService.changePassword(username, currentPassword, newPassword);
        if (success) {
            session.setAttribute("flashMessage", "Password changed successfully!");
            session.setAttribute("flashStatus", "success");
            LOGGER.info("Ctrl: Password change successful for [" + username + "]");
        } else {
            session.setAttribute("flashMessage", "Failed to change password. Verify current password.");
            session.setAttribute("flashStatus", "error");
            LOGGER.warning("Ctrl: Password change failed via service for [" + username + "]");
        }
    }
}