<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Log In - Furniture World</title>
    <link rel="stylesheet" href="css/style.css">
    <link rel="stylesheet" href="css/header.css">
    <link rel="stylesheet" href="css/login.css">
    <link rel="stylesheet" href="css/footer.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css" integrity="sha512-SfTiTlX6kk+qitfevl/7LibUOeJWlt9rbyDn92a1DqWOw9vWG2MFoays0sgObmWazO5BQPiFucnnEAjpAB+/Sw==" crossorigin="anonymous" referrerpolicy="no-referrer" />
	<link rel="preconnect" href="https://fonts.googleapis.com">
	<link rel="preconnect" href="https://fonts.gstatic.com">
	<link href="https://fonts.googleapis.com/css2?family=Poppins:ital,wght@0,100;0,200;0,300;0,400;0,500;0,600;0,700;0,800;0,900;1,100;1,200;1,300;1,400;1,500;1,600;1,700;1,800;1,900&display=swap" rel="stylesheet">
</head>
<body>
    <div class="container">
        <jsp:include page="header.jsp" />

        <main class="login-page">
            <div class="login-container">
                <div class="login-left">
                   
                    <img src="${pageContext.request.contextPath}/assets/login.png" alt="Welcome Image">
                </div>
                <div class="login-right">
                    <h2>Log In</h2>
                    <c:if test="${not empty error}">
						<p class="error-message">${error}</p>
					</c:if>
			
					<!-- Display success message if available -->
					<c:if test="${not empty success}">
						<p class="success-message">${success}</p>
					</c:if>
			       
                    <form class="login-form" method="post" action="${pageContext.request.contextPath}/login">
                        <div class="input-group">
                            <label for="username"><i class="fa fa-user"></i></label>
                            <input type="text" id="username" name="username" placeholder="Username">
                        </div>
                        <div class="input-group">
                            <label for="password"><i class="fa fa-lock"></i></label>
                            <input type="password" id="password" name="password" placeholder="Password">
                            
                        </div>
                        <div class="form-actions">
                            <button type="submit" class="login-button">Log In</button>
                            <div class="separator">
                                <hr>
                                <span>or</span>
                                <hr>
                            </div>
                            <a href = "${pageContext.request.contextPath}/register" class="signup-button">Sign Up</a>
                            
                        </div>
                    </form>
                    
                </div>
            </div>
        </main>

        <jsp:include page="footer.jsp" />
    </div>
    
</body>
</html>