<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Online Shop - Furniture World</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/header.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/Product.css"> <!-- Keep this for general product card styling -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/footer.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Poppins:ital,wght@0,100;0,200;0,300;0,400;0,500;0,600;0,700;0,800;0,900;1,100;1,200;1,300;1,400;1,500;1,600;1,700;1,800;1,900&display=swap" rel="stylesheet">
    <style>
        /* You might need to adjust Product.css if image containers provided significant structure/spacing */
        .search-bar-container {
            text-align: center; margin-bottom: 25px; padding: 15px;
            background-color: #fff; border-radius: 8px; box-shadow: 0 2px 5px rgba(0,0,0,0.05);
        }
        .search-bar-container input[type="text"] {
            padding: 10px 15px; font-size: 1em; border: 1px solid #ccc;
            border-radius: 20px 0 0 20px; width: 60%; max-width: 400px;
            box-sizing: border-box; vertical-align: middle;
        }
        .search-bar-container button {
            padding: 10px 20px; font-size: 1em; background-color: #408080;
            color: white; border: 1px solid #408080; border-left: none;
            border-radius: 0 20px 20px 0; cursor: pointer;
            transition: background-color 0.3s ease; vertical-align: middle; margin-left: -5px;
        }
        .search-bar-container button:hover { background-color: #306060; }
        .no-products { text-align: center; font-size: 1.2em; color: #555; margin-top: 30px; padding: 20px; background-color: #f9f9f9; border: 1px dashed #ddd;}
        
        /* Adjust .product-card if needed since image is removed */
        .product-card {
            /* ... your existing styles ... */
            /* You might want to remove fixed heights previously set for image containers */
        }
        .product-card-details {
            padding-top: 20px; /* Add some top padding if the image area is gone */
        }
    </style>
</head>
<body>
    <div class="container">
        <jsp:include page="header.jsp" />

        <main class="product-page-content">
            <div class="product-banner">
                <h1>ONLINE SHOP</h1>
                <!-- You can keep this banner image if it's a static asset -->
                <img src="${pageContext.request.contextPath}/assets/saleban.png" alt="Sale Banner">
                <br>
                <h2>Explore Our Exquisite Collection Of Hand Crafted Furniture.</h2>
            </div>

            <div class="search-bar-container">
                <form action="${pageContext.request.contextPath}/searchProducts" method="get">
                    <input type="text" name="searchTerm" placeholder="Search furniture or category..."
                           value="${fn:escapeXml(searchTerm)}">
                    <button type="submit"><i class="fa fa-search"></i> Search</button>
                </form>
            </div>

            <c:if test="${empty products}">
                <c:choose>
                    <c:when test="${not empty searchTerm}">
                        <p class="no-products">No products found matching your search: "<strong><c:out value="${searchTerm}"/></strong>".</p>
                    </c:when>
                    <c:otherwise>
                        <p class="no-products">No products available at the moment. Please check back later or ensure the database is populated and accessible.</p>
                    </c:otherwise>
                </c:choose>
            </c:if>

            <div class="product-grid">
                <c:forEach var="product" items="${products}">
                    <div class="product-card">
                        <%-- Image container and image tag REMOVED --%>
                        <%--
                        <div class="product-card-image-container">
                            <c:url var="imgUrl" value="/resources/images/${productImageFolder}/${not empty product.imageFilename ? product.imageFilename : 'default_placeholder.png'}" />
                            <img src="${imgUrl}" alt="<c:out value="${product.furniture_name}"/>">
                        </div>
                        --%>
                        <div class="product-card-details">
                            <h3><c:out value="${product.furniture_name}"/></h3>
                            <p class="product-price">₹<fmt:formatNumber value="${product.price}" type="number" minFractionDigits="2" maxFractionDigits="2"/></p>
                            <p class="product-description">
                                <c:choose>
                                    <c:when test="${not empty product.description}">
                                        <c:out value="${fn:substring(product.description, 0, 100)}"/>
                                        <c:if test="${fn:length(product.description) > 100}">...</c:if>
                                    </c:when>
                                    <c:otherwise>
                                        Click 'View Details' for more information.
                                    </c:otherwise>
                                </c:choose>
                            </p>
                            <p class="product-contact">To buy, contact: 9851098701</p>
                            <a href="${pageContext.request.contextPath}/viewProduct?id=${product.furniture_id}" class="btn-view-details">View Details</a>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </main>

        <jsp:include page="footer.jsp" />
    </div>
</body>
</html>