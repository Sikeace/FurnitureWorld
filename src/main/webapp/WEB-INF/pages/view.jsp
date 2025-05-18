<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <c:set var="pageTitle" value="Product Details - Furniture World"/>
    <c:if test="${not empty product}">
        <c:set var="pageTitle" value="Product Details - ${product.furniture_name}"/>
    </c:if>
    <c:if test="${not empty errorMessage and empty product}">
        <c:set var="pageTitle" value="Product Not Found - Furniture World"/>
    </c:if>
    <title><c:out value="${pageTitle}"/></title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/header.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/view.css"> <!-- Keep for general detail page styling -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/footer.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Poppins:ital,wght@0,100;0,200;0,300;0,400;0,500;0,600;0,700;0,800;0,900;1,100;1,200;1,300;1,400;1,500;1,600;1,700;1,800;1,900&display=swap" rel="stylesheet">
    <style>
        /* Adjust .product-detail-container if it relied on image section for layout */
        .product-detail-container {
            /* display: flex; (if you still want side-by-side, otherwise it will stack) */
            /* flex-wrap: wrap; */
            gap: 30px;
        }
        .product-detail-info-section {
            flex: 1 1 100%; /* Allow info to take full width if no image */
            padding-left: 0; /* No need for padding if image is gone */
        }
    </style>
</head>
<body>
    <div class="container">
        <jsp:include page="header.jsp" />

        <main class="product-detail-page-content">
            <c:choose>
                <c:when test="${not empty product}">
                    <div class="product-detail-container">
                        <%-- Image section REMOVED --%>
                        <%--
                        <div class="product-detail-image-section">
                            <c:url var="imgUrl" value="/resources/images/${productImageFolder}/${not empty product.imageFilename ? product.imageFilename : 'default_placeholder.png'}" />
                            <img src="${imgUrl}" alt="<c:out value="${product.furniture_name}"/>">
                        </div>
                        --%>
                        <div class="product-detail-info-section">
                            <h1><c:out value="${product.furniture_name}"/></h1>
                             <p class="detail-category" style="font-size: 0.9em; color: #666; margin-bottom: 10px;">Category: <c:out value="${product.category_name}"/></p>
                            <p class="detail-price">₹<fmt:formatNumber value="${product.price}" type="number" minFractionDigits="2" maxFractionDigits="2"/></p>
                            <div class="detail-description">
                                <p>
                                    <c:choose>
                                        <c:when test="${not empty product.description}">
                                            <c:out value="${product.description}" escapeXml="false"/>
                                        </c:when>
                                        <c:otherwise>
                                            Detailed description not available for this product.
                                        </c:otherwise>
                                    </c:choose>
                                </p>
                            </div>
                            <p class="detail-contact"><c:out value="${productContact}"/></p>
                            <a href="${pageContext.request.contextPath}/product" class="btn-back-to-shop">Back to Shop</a>
                        </div>
                    </div>
                </c:when>
                <c:otherwise>
                    <div class="product-not-found">
                        <h2>Product Not Found</h2>
                        <p>
                            <c:choose>
                                <c:when test="${not empty errorMessage}">
                                    <c:out value="${errorMessage}"/>
                                </c:when>
                                <c:otherwise>
                                    The product you are looking for does not exist or is unavailable.
                                </c:otherwise>
                            </c:choose>
                        </p>
                        <a href="${pageContext.request.contextPath}/product" class="btn-back-to-shop">Go to Shop</a>
                    </div>
                </c:otherwise>
            </c:choose>
        </main>
        <br>
        <br>
        <jsp:include page="footer.jsp" />
    </div>
</body>
</html>