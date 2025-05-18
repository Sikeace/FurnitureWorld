package com.furnitureworld.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import com.furnitureworld.model.FurnitureModel; // Assuming FurnitureModel represents a user
import com.furnitureworld.service.RegisterService;
import com.furnitureworld.util.PasswordUtil;
import com.furnitureworld.util.ValidationUtil;


/**
 * RegisterController handles user registration requests.
 * It validates user input, processes form submissions,
 * and interacts with the RegisterService to create new user accounts.
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/register" }) 
public class RegisterController extends HttpServlet {
	private static final long serialVersionUID = 1L; 

	private final RegisterService registerService = new RegisterService(); 

	/**
	 * Handles HTTP GET requests.
	 * Displays the registration page to the user.
	 *
	 * @param req  the HttpServletRequest object
	 * @param resp the HttpServletResponse object
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException      if an I/O error occurs
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// Forward the request to the register.jsp page
		req.getRequestDispatcher("/WEB-INF/pages/register.jsp").forward(req, resp);
	}

	/**
	 * Handles HTTP POST requests, typically from a registration form submission.
	 * Validates the submitted data, creates a new user account if valid,
	 * and redirects or displays appropriate messages.
	 *
	 * @param req  the HttpServletRequest object
	 * @param resp the HttpServletResponse object
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException      if an I/O error occurs
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			// Validate the registration form data
			String validationMessage = validateRegistrationForm(req);
			if (validationMessage != null) {
				handleError(req, resp, validationMessage);
				return;
			}
			System.out.println("done validating");

			FurnitureModel furnitureModel = extractFurnitureModel(req);
			System.out.println(furnitureModel);

			Boolean isAdded = registerService.addFurniture(furnitureModel);

			if (isAdded == null) {
				handleError(req, resp, "Our server is under maintenance. Please try again later!");
			} else if (isAdded) {
				handleSuccess(req, resp, "Registration successful! Please login.", "WEB-INF/pages/login.jsp");
			} else {
				handleError(req, resp, "Could not register your account. Username or email might already exist. Please try again!");
			}
		} catch (Exception e) {
			handleError(req, resp, "An unexpected error occurred. Please try again later!");
			e.printStackTrace(); 
		}
	}

	/**
	 * Validates the registration form data.
	 *
	 * @param req the HttpServletRequest object containing form data
	 * @return A validation error message if validation fails, or null if successful.
	 */
	private String validateRegistrationForm(HttpServletRequest req) {
		// Retrieve form parameters
		String fullName = req.getParameter("fullName");
		String username = req.getParameter("username");
		String email = req.getParameter("email");
		String password = req.getParameter("password");
		String retypePassword = req.getParameter("confirmPassword");

		// Check for null or empty fields first
		if (ValidationUtil.isNullOrEmpty(fullName))
			return "Full name is required.";
		if (ValidationUtil.isNullOrEmpty(username))
			return "Username is required.";
		if (ValidationUtil.isNullOrEmpty(email))
			return "Email is required.";
		if (ValidationUtil.isNullOrEmpty(password))
			return "Password is required.";
		if (ValidationUtil.isNullOrEmpty(retypePassword))
			return "Please retype the password.";

		// Validate individual fields using utility methods
		if (!ValidationUtil.isAlphanumericStartingWithLetter(username))
			return "Username must start with a letter and contain only letters and numbers.";
		if (!ValidationUtil.isValidEmail(email))
			return "Invalid email format.";
		if (!ValidationUtil.isValidPassword(password)) 
			return "Password must be at least 8 characters long, with 1 uppercase letter, 1 number, and 1 symbol.";
		if (!ValidationUtil.doPasswordsMatch(password, retypePassword))
			return "Passwords do not match.";

		return null; 
	}

	/**
	 * Extracts user data from the request and creates a FurnitureModel object.
	 * Also encrypts the password.
	 *
	 * @param req the HttpServletRequest object
	 * @return A FurnitureModel object populated with user data.
	 * @throws Exception if password encryption fails.
	 */
	private FurnitureModel extractFurnitureModel(HttpServletRequest req) throws Exception {
		// Retrieve form parameters
		String fullName = req.getParameter("fullName");
		String username = req.getParameter("username");
		String email = req.getParameter("email");
		String password = req.getParameter("password");

		// Encrypt the password before storing it
		String encryptedPassword = PasswordUtil.encrypt(username, password); 
		FurnitureModel user = new FurnitureModel(fullName, email, username, encryptedPassword); 
		return user;
	}

	/**
	 * Handles successful registration by setting a success message and forwarding
	 * to a specified page (usually the login page).
	 *
	 * @param req          the HttpServletRequest object
	 * @param resp         the HttpServletResponse object
	 * @param message      the success message to display
	 * @param redirectPage the page to forward to after success
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException      if an I/O error occurs
	 */
	private void handleSuccess(HttpServletRequest req, HttpServletResponse resp, String message, String redirectPage)
			throws ServletException, IOException {
		req.setAttribute("success", message); 
		req.getRequestDispatcher(redirectPage).forward(req, resp); 
	}

	/**
	 * Handles registration errors by setting an error message and forwarding
	 * the user back to the registration page, repopulating the form fields.
	 *
	 * @param req     the HttpServletRequest object
	 * @param resp    the HttpServletResponse object
	 * @param message the error message to display
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException      if an I/O error occurs
	 */
	private void handleError(HttpServletRequest req, HttpServletResponse resp, String message)
			throws ServletException, IOException {
		req.setAttribute("error", message); 
		// Repopulate form fields so the user doesn't have to re-enter everything
		req.setAttribute("fullName", req.getParameter("fullName")); 
		req.setAttribute("username", req.getParameter("username"));
		req.setAttribute("email", req.getParameter("email"));
		// Forward back to the registration page
		req.getRequestDispatcher("/WEB-INF/pages/register.jsp").forward(req, resp);
	}
}