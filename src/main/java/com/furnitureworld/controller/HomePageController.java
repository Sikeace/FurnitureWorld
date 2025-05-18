package com.furnitureworld.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet controller for handling requests to the home page.
 * It forwards requests to the home.jsp page.
 */
@WebServlet("/home") // Maps this servlet to the "/home" URL pattern
public class HomePageController extends HttpServlet {
	private static final long serialVersionUID = 1L; // Unique identifier for serialization

    /**
     * Default constructor.
     * @see HttpServlet#HttpServlet()
     */
    public HomePageController() {
        super(); // Calls the constructor of the superclass (HttpServlet)
        // TODO Auto-generated constructor stub - This is a placeholder comment, can be removed if no custom constructor logic is needed.
    }

	/**
	 * Handles HTTP GET requests.
	 * Forwards the request to the home.jsp page for display.
	 *
	 * @param request  the HttpServletRequest object that contains the request the client made of the servlet
	 * @param response the HttpServletResponse object that contains the response the servlet returns to the client
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException      if an I/O error occurs
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Forwards the request and response objects to the specified JSP page
		request.getRequestDispatcher("WEB-INF/pages/home.jsp").forward(request, response);
	}

	/**
	 * Handles HTTP POST requests.
	 * By default, it calls the doGet method to handle POST requests in the same way as GET requests.
	 *
	 * @param request  the HttpServletRequest object that contains the request the client made of the servlet
	 * @param response the HttpServletResponse object that contains the response the servlet returns to the client
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException      if an I/O error occurs
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub - This is a placeholder comment.
		// Delegates POST request handling to the doGet method.
		doGet(request, response);
	}

}