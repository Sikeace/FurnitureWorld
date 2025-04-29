// package com.furnitureworld.filter;
//
//import jakarta.servlet.Filter;
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.FilterConfig;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.ServletRequest;
//import jakarta.servlet.ServletResponse;
//import jakarta.servlet.annotation.WebFilter;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import jakarta.servlet.http.HttpFilter;
//import java.io.IOException;
//
//import com.furnitureworld.util.CookieUtil;
//import com.furnitureworld.util.SessionUtil;
//
//
///**
// * Servlet Filter implementation class AuthenticationFilter
// */
//@WebFilter(asyncSupported = true, urlPatterns = "/*")
//public class AuthenticationFilter implements Filter {
//	
//	private static final String LOGIN = "/login";
//	private static final String REGISTER = "/register";
//	private static final String HOME = "/home";
//	private static final String PRODUCT = "/product";
//	private static final String ADMIN = "/admin";
//	private static final String CONTACT = "/contact";
//	private static final String ABOUT = "/about";
//	private static final String ROOT = "/";
//
//	
//	
//	@Override
//	public void init(FilterConfig filterConfig) throws ServletException {
//		// Initialization logic, if required
//	}
//    /**
//     * @see HttpFilter#HttpFilter()
//     */
//    public AuthenticationFilter() {
//        super();
//        // TODO Auto-generated constructor stub
//    }
//
//	/**
//	 * @see Filter#destroy()
//	 */
//	public void destroy() {
//		// TODO Auto-generated method stub
//	}
//
//	/**
//	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
//	 */
//	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
//	        throws IOException, ServletException {
//
//	    HttpServletRequest req = (HttpServletRequest) request;
//	    HttpServletResponse res = (HttpServletResponse) response;
//
//	    // Get the requested URI
//	    String uri = req.getRequestURI();
//
//	    if (uri.endsWith(".css") || uri.endsWith(".jpg") || uri.endsWith(".jpeg") || uri.endsWith(".png")
//	    	    || uri.endsWith(HOME) || uri.endsWith(PRODUCT)|| uri.endsWith(ADMIN)||uri.endsWith(CONTACT)||uri.endsWith(LOGIN)||uri.endsWith(ABOUT)||uri.endsWith(ROOT)) {
//	    	    chain.doFilter(request, response);
//	    	    return;
//	    	}
//	    
//
//	    // Get the session and check if user is logged in
//	    boolean isLoggedIn = SessionUtil.getAttribute(req, "username") != null;
//
//	    if (!isLoggedIn) {
//	        if (uri.endsWith(LOGIN) || uri.endsWith(REGISTER)) {
//	            chain.doFilter(request, response);
//	        } else {
//	            res.sendRedirect(req.getContextPath() + LOGIN);
//	        }
//	    } else {
//	        if (uri.endsWith(LOGIN) || uri.endsWith(REGISTER)) {
//	            res.sendRedirect(req.getContextPath() + HOME);
//	        } else {
//	            chain.doFilter(request, response);
//	        }
//	    }
//	}
//}