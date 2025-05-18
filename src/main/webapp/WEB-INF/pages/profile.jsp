<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> <%-- For number formatting --%>


<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Profile - Furniture World</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/header.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/profile.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/footer.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css" integrity="sha512-SfTiTlX6kk+qitfevl/7LibUOeJWlt9rbyDn92a1DqWOw9vWG2MFoays0sgObmWazO5BQPiFucnnEAjpAB+/Sw==" crossorigin="anonymous" referrerpolicy="no-referrer" />
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com">
    <link href="https://fonts.googleapis.com/css2?family=Poppins:ital,wght@0,100;0,200;0,300;0,400;0,500;0,600;0,700;0,800;0,900;1,100;1,200;1,300;1,400;1,500;1,600;1,700;1,800;1,900&display=swap" rel="stylesheet">
</head>
<body>
    <div class="container">
        <jsp:include page="header.jsp" />
<br>
<br>
        <main class="content-wrap">
            <div class="profile-section card">
                
                <%-- Determine Title and Links based on Mode --%>
                <c:choose>
                    <c:when test="${profileMode eq 'edit'}">
                        <h2>Edit Profile Information</h2>
                    </c:when>
                    <c:when test="${profileMode eq 'password'}">
                        <h2>Change Your Password</h2>
                    </c:when>
                    <c:otherwise> <%-- Default to 'view' mode --%>
                        <h2>My Profile</h2>
                    </c:otherwise>
                </c:choose>

                <%-- Flash Message Display --%>
                <c:if test="${not empty message}">
                    <div class="message-banner ${status == 'success' ? 'success' : 'error'}">
                        <p><c:out value="${message}" /></p>
                    </div>
                </c:if>

                <%-- Conditional Content Display --%>
                <%-- VIEW PROFILE SECTION --%>
                <c:if test="${profileMode eq 'view' or empty profileMode}">
                    <div class="profile-details-view">
                        <p><strong>Full Name:</strong> <c:out value="${dbUser.full_name}" /></p>
                        <p><strong>Email:</strong> <c:out value="${dbUser.email}" /></p>
                        <p><strong>Username:</strong> <c:out value="${dbUser.username}" /></p>
                    </div>
                    <div class="profile-actions">
                        <a href="${pageContext.request.contextPath}/profile/edit" class="btn btn-secondary">Edit Information</a>
                        <a href="${pageContext.request.contextPath}/profile/password" class="btn btn-secondary">Change Password</a>
                    </div>
                </c:if>


                <%-- EDIT PROFILE FORM SECTION --%>
                <c:if test="${profileMode eq 'edit'}">
                    <form method="POST" action="${pageContext.request.contextPath}/profile/edit" class="profile-form">
                        <div class="form-field">
                            <label for="fullName">Full Name:</label>
                            <input type="text" id="fullName" name="fullName" value="<c:out value="${dbUser.full_name}" />" required>
                        </div>
                        <div class="form-field">
                            <label for="email">Email Address:</label>
                            <input type="email" id="email" name="email" value="<c:out value="${dbUser.email}" />" required>
                        </div>
                        <div class="form-field">
                            <label for="username">Username:</label>
                            <input type="text" id="username" name="username" value="<c:out value="${dbUser.username}" />" readonly disabled class="readonly-input">
                        </div>
                        <div class="form-actions">
                            <button type="submit" class="btn btn-primary">Save Changes</button>
                            <a href="${pageContext.request.contextPath}/profile" class="btn btn-link">Cancel</a>
                        </div>
                    </form>
                </c:if>

                <%-- CHANGE PASSWORD FORM SECTION --%>
                <c:if test="${profileMode eq 'password'}">
                    <form method="POST" action="${pageContext.request.contextPath}/profile/password" class="profile-form">
                        <div class="form-field">
                            <label for="currentPassword">Current Password:</label>
                            <input type="password" id="currentPassword" name="currentPassword" required autocomplete="current-password">
                        </div>
                        <div class="form-field">
                            <label for="newPassword">New Password:</label>
                            <input type="password" id="newPassword" name="newPassword" required autocomplete="new-password">
                        </div>
                        <div class="form-field">
                            <label for="confirmPassword">Confirm New Password:</label>
                            <input type="password" id="confirmPassword" name="confirmPassword" required autocomplete="new-password">
                        </div>
                        <div class="form-actions">
                            <button type="submit" class="btn btn-primary">Update Password</button>
                            <a href="${pageContext.request.contextPath}/profile" class="btn btn-link">Cancel</a>
                        </div>
                    </form>
                </c:if>

            </div>
        </main>
        <br>
<br>
<br>
<br>
<br>



        <jsp:include page="footer.jsp" /> <%-- Assuming footer.jsp is directly in /WEB-INF/pages/ --%>
    </div>
</body>
</html>