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

@WebServlet(asyncSupported = true, urlPatterns = { "/login", "/" })
public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final LoginService loginService;
	
	public LoginController() {
		this.loginService = new LoginService();
	}
       
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getRequestDispatcher("/WEB-INF/pages/login.jsp").forward(request, response);
	}



	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String username = req.getParameter("username");
		String password = req.getParameter("password");

		FurnitureModel furnitureModel = new FurnitureModel(username, password);
		Boolean loginStatus = loginService.loginUser(furnitureModel);

		if (loginStatus != null && loginStatus) {
			SessionUtil.setAttribute(req, "username", username);
			if (furnitureModel.isIs_Admin()) {
				CookieUtil.addCookie(resp, "role", "admin", 5 * 30);
				resp.sendRedirect(req.getContextPath() + "/admin"); // Redirect to /home
			} else {
				CookieUtil.addCookie(resp, "role", "user", 5 * 30);
				resp.sendRedirect(req.getContextPath() + "/home"); // Redirect to /home
			}
		} else {
			handleLoginFailure(req, resp, loginStatus);
		}
	}


	 
	private void handleLoginFailure(HttpServletRequest req, HttpServletResponse resp, Boolean loginStatus)
			throws ServletException, IOException {
		String errorMessage;
		if (loginStatus == null) {
			errorMessage = "Our server is under maintenance. Please try again later!";
		} else {
			errorMessage = "User credential mismatch. Please try again!";
		}
		req.setAttribute("error", errorMessage);
		req.getRequestDispatcher("/WEB-INF/pages/login.jsp").forward(req, resp);
	}

}
