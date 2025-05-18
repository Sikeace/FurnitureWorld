package com.furnitureworld.controller;

import com.furnitureworld.service.LoginService;
import com.furnitureworld.util.CookieUtil;
import com.furnitureworld.util.SessionUtil;
import com.furnitureworld.model.FurnitureModel;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet controller for handling user login.
 * Mapped to "/login" and the root ("/") path.
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/login", "/" }) // Defines URL patterns for this servlet
public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L; // Unique identifier for serialization
	private final LoginService loginService; // Service class for handling login logic

	/**
     * Constructor that initializes the LoginService.
     */
	public LoginController() {
		this.loginService = new LoginService(); // Instantiate the login service
	}

	/**
	 * Handles HTTP GET requests.
	 * Displays the login page to the user.
	 *
	 * @param request  the HttpServletRequest object
	 * @param response the HttpServletResponse object
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException      if an I/O error occurs
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Forward the request to the login.jsp page
		request.getRequestDispatcher("/WEB-INF/pages/login.jsp").forward(request, response);
	}


	/**
	 * Handles HTTP POST requests, typically from a login form submission.
	 * Validates user credentials and redirects based on login success and user role.
	 *
	 * @param req  the HttpServletRequest object
	 * @param resp the HttpServletResponse object
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException      if an I/O error occurs
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// Get username and password from the request parameters
		String username = req.getParameter("username");
		String password = req.getParameter("password");

		// Create a FurnitureModel (representing a user) with the provided credentials
		FurnitureModel furnitureModel = new FurnitureModel(username, password);
		// Attempt to log in the user using the LoginService
		Boolean loginStatus = loginService.loginUser(furnitureModel);

		// Check if login was successful
		if (loginStatus != null && loginStatus) {
			// If login is successful, set the username in the session
			SessionUtil.setAttribute(req, "username", username);
			// Check if the logged-in user is an administrator
			if (furnitureModel.isIs_Admin()) {
				// Add a cookie indicating the user role is "admin"
				CookieUtil.addCookie(resp, "role", "admin", 5 * 30);
				// Redirect to the admin page
				resp.sendRedirect(req.getContextPath() + "/admin");
			} else {
				// Add a cookie indicating the user role is "user"
				CookieUtil.addCookie(resp, "role", "user", 5 * 30); 
				// Redirect to the home page
				resp.sendRedirect(req.getContextPath() + "/home");
			}
		} else {
			// If login fails, handle the failure (e.g., display an error message)
			handleLoginFailure(req, resp, loginStatus);
		}
	}


	/**
	 * Handles login failure by setting an appropriate error message and forwarding
	 * the user back to the login page.
	 *
	 * @param req         the HttpServletRequest object
	 * @param resp        the HttpServletResponse object
	 * @param loginStatus the status of the login attempt (null for server error, false for credential mismatch)
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException      if an I/O error occurs
	 */
	private void handleLoginFailure(HttpServletRequest req, HttpServletResponse resp, Boolean loginStatus)
			throws ServletException, IOException {
		String errorMessage;
		if (loginStatus == null) {
			errorMessage = "Our server is under maintenance. Please try again later!"; 
		} else {
			errorMessage = "User credential mismatch. Please try again!"; // Incorrect username/password
		}
		// Set the error message as a request attribute to be displayed on the login page
		req.setAttribute("error", errorMessage);
		// Forward the request back to the login.jsp page
		req.getRequestDispatcher("/WEB-INF/pages/login.jsp").forward(req, resp);
	}

}