<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Register - Furniture World</title>
    <link rel="stylesheet" href="css/style.css">
    <link rel="stylesheet" href="css/header.css">
    <link rel="stylesheet" href="css/register.css">
    <link rel="stylesheet" href="css/footer.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css" integrity="sha512-SfTiTlX6kk+qitfevl/7LibUOeJWlt9rbyDn92a1DqWOw9vWG2MFoays0sgObmWazO5BQPiFucnnEAjpAB+/Sw==" crossorigin="anonymous" referrerpolicy="no-referrer" />
	<link rel="preconnect" href="https://fonts.googleapis.com">
	<link rel="preconnect" href="https://fonts.gstatic.com">
	<link href="https://fonts.googleapis.com/css2?family=Poppins:ital,wght@0,100;0,200;0,300;0,400;0,500;0,600;0,700;0,800;0,900;1,100;1,200;1,300;1,400;1,500;1,600;1,700;1,800;1,900&display=swap" rel="stylesheet">
</head>
<body>
    <div class="container">
        <jsp:include page="header.jsp" />

        <main class="register-page">
            <div class="register-container">
                <h2>Sign Up</h2>
                 <c:if test="${not empty error}">
						<p class="error-message">${error}</p>
					</c:if>
			
					<!-- Display success message if available -->
					<c:if test="${not empty success}">
						<p class="success-message">${success}</p>
					</c:if>
                <form class="register-form" action="${pageContext.request.contextPath}/register" method="post">
                    <div class="input-group">
                        <label for="fullName"><i class="fa fa-user"></i></label>
                        <input type="text" id="fullName" name="fullName" placeholder="Full Name" >
                    </div>
                    <div class="input-group">
                        <label for="email"><i class="fa fa-envelope"></i></label>
                        <input type="email" id="email" name="email" placeholder="Email Address" >
                    </div>
                    <div class="input-group">
                        <label for="username"><i class="fa fa-id-card"></i></label>
                        <input type="text" id="username" name="username" placeholder="Username" >
                    </div>
                    <div class="input-group">
                        <label for="password"><i class="fa fa-lock"></i></label>
                        <input type="password" id="password" name="password" placeholder="Password" >
                        <button type="button" class="toggle-password">
                          
                        </button>
                    </div>
                    <div class="input-group">
                        <label for="confirmPassword"><i class="fa fa-lock"></i></label>
                        <input type="password" id="confirmPassword" name="confirmPassword" placeholder="Confirm Password" >
                        <button type="button" class="toggle-password">
                            
                        </button>
                    </div>
                    <button type="submit" class="register-button">Sign Up</button>
                    <p class="login-link">Already have an account? <a href="${pageContext.request.contextPath}/login">Log In</a></p>
                </form>
            </div>
        </main>

        <jsp:include page="footer.jsp" />
    </div>
    
</body>
</html>