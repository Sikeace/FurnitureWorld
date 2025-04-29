package com.furnitureworld.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import com.furnitureworld.model.FurnitureModel;
import com.furnitureworld.service.RegisterService;
import com.furnitureworld.util.PasswordUtil;
import com.furnitureworld.util.ValidationUtil;


/**
 * RegisterController handles user registration requests and processes form
 * submissions. It also manages file uploads and account creation.
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/register" })
public class RegisterController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private final RegisterService registerService = new RegisterService();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("/WEB-INF/pages/register.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
	
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
				handleSuccess(req, resp, "added", "WEB-INF/pages/login.jsp");
			} else {
				handleError(req, resp, "Could not register your account. Please try again later!");
			}
		} catch (Exception e) {
			handleError(req, resp, "An unexpected error occurred. Please try again later!");
			e.printStackTrace(); // Log the exception
		}
	}

	private String validateRegistrationForm(HttpServletRequest req) {
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

		
	

		// Validate fields
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

	private FurnitureModel extractFurnitureModel(HttpServletRequest req) throws Exception {
		String fullName = req.getParameter("fullName");
		String username = req.getParameter("username");
		String email = req.getParameter("email");
		String password = req.getParameter("password");

		// Assuming password validation is already done in validateRegistrationForm
		password = PasswordUtil.encrypt(username, password);
		FurnitureModel user = new FurnitureModel(fullName, email, username, password);
		
		return user;
	}

	private void handleSuccess(HttpServletRequest req, HttpServletResponse resp, String message, String redirectPage)
			throws ServletException, IOException {
		req.setAttribute("success", message);
		req.getRequestDispatcher(redirectPage).forward(req, resp);
	}

	private void handleError(HttpServletRequest req, HttpServletResponse resp, String message)
			throws ServletException, IOException {
		req.setAttribute("error", message);
		req.setAttribute("firstName", req.getParameter("fullname"));
		
		req.setAttribute("username", req.getParameter("username"));
		
		req.setAttribute("email", req.getParameter("email"));
		
		req.getRequestDispatcher("/WEB-INF/pages/register.jsp").forward(req, resp);
	}
	}