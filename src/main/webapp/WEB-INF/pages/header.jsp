<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<header class="main-header">
    <div class="logo">
            <img src="${pageContext.request.contextPath}/assets/sofa.png" alt="Furniture World">
    		<p style="text-align: center;">Furniture World</p>
    </div>
    <nav class="main-nav">
        <ul>
            <li><a href="${pageContext.request.contextPath}/home" class="active"><i class="fa fa-home" aria-hidden="true"></i> HOME</a></li>
            <li><a href="${pageContext.request.contextPath}/product"><i class="fa fa-shopping-cart" aria-hidden="true"></i> SHOP</a></li>
            <li><a href="${pageContext.request.contextPath}/admin"><i class="fa fa-users" aria-hidden="true"></i> ADMIN</a></li>
            <li><a href="${pageContext.request.contextPath}/about"><i class="fa fa-info-circle" aria-hidden="true"></i> ABOUT</a></li>
            <li><a href="${pageContext.request.contextPath}/contact"><i class="fa fa-phone" aria-hidden="true"></i> CONTACT</a></li>
        </ul>
    </nav>
    <div class="header-actions">
        <a href="${pageContext.request.contextPath}/login"><i class="fa fa-sign-out" aria-hidden="true"> LogOut</i></a>
    </div>
</header>