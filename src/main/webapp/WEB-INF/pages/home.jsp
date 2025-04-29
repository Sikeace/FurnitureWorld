<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Furniture world</title>
    <link rel="stylesheet" href="css/style.css">
    <link rel="stylesheet" href="css/header.css">
    <link rel="stylesheet" href="css/home.css">
    <link rel="stylesheet" href="css/footer.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css" integrity="sha512-SfTiTlX6kk+qitfevl/7LibUOeJWlt9rbyDn92a1DqWOw9vWG2MFoays0sgObmWazO5BQPiFucnnEAjpAB+/Sw==" crossorigin="anonymous" referrerpolicy="no-referrer" />
	<link rel="preconnect" href="https://fonts.googleapis.com">
	<link rel="preconnect" href="https://fonts.gstatic.com">
	<link href="https://fonts.googleapis.com/css2?family=Poppins:ital,wght@0,100;0,200;0,300;0,400;0,500;0,600;0,700;0,800;0,900;1,100;1,200;1,300;1,400;1,500;1,600;1,700;1,800;1,900&display=swap" rel="stylesheet">
</head>
<body>
    <div class="container">
        <jsp:include page="header.jsp" />

        <main class="home-content">
            <div class="hero">
                <div class="hero-image">
                    <img src = "${pageContext.request.contextPath}/assets/Furniturepurple.png">
                    
                </div>
                <div class="hero-text">
                	<h1>Handled With Care.</h1>
                    <h1>Super Easy To Pick.</h1>
                    <h1>Minimalistic Design.</h1>
                    <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.</p>
                    <a href="${pageContext.request.contextPath}/product"class="button">SHOP NOW</a>
                </div>
            </div>

            <div class="product-previews">
                <h2>Our Top Selling Products</h2>
                <div class="preview-grid">
                    <div class="preview-item">
					<img src = "${pageContext.request.contextPath}/assets/table.jpg">
                    </div>
                    <div class="product-prevdes">
                        <h3>Office Table</h3>
                        <p class="description">Hydrating soap with glycerin to lock in moisture and keep skin soft.</p>
                        <a href="${pageContext.request.contextPath}/product"class="button">View</a>
                    </div>
                    <div class="preview-item">
                        <img src = "${pageContext.request.contextPath}/assets/wardrobe.jpg">
                    </div>
                    <div class="product-prevdes">
                        <h3>Wardrobe</h3>
                        <p class="description">Hydrating soap with glycerin to lock in moisture and keep skin soft.</p>
                        <a href="${pageContext.request.contextPath}/product"class="button">View</a>
                    </div>
                    <div class="preview-item">
                         <img src = "${pageContext.request.contextPath}/assets/sofa prod.jpg">
                    </div>
                    <div class="product-prevdes">
                        <h3>Sofa</h3>
                        <p class="description">Hydrating soap with glycerin to lock in moisture and keep skin soft.</p>
                        <a href="${pageContext.request.contextPath}/product"class="button">View</a>
                    </div>
                     <div class="preview-item">
                         <img src = "${pageContext.request.contextPath}/assets/dining.jpg">
                    </div>
                    <div class="product-prevdes">
                        <h3>Dining table</h3>
                        <p class="description">Hydrating soap with glycerin to lock in moisture and keep skin soft.</p>
                        <a href="${pageContext.request.contextPath}/product"class="button">View</a>
                    </div>
                </div>
            </div>
        </main>

        <jsp:include page="footer.jsp" />
    </div>
</body>