<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Online Shop - Furniture World</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/header.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/Product.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/footer.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css" integrity="sha512-SfTiTlX6kk+qitfevl/7LibUOeJWlt9rbyDn92a1DqWOw9vWG2MFoays0sgObmWazO5BQPiFucnnEAjpAB+/Sw==" crossorigin="anonymous" referrerpolicy="no-referrer" />
	<link rel="preconnect" href="https://fonts.googleapis.com">
	<link rel="preconnect" href="https://fonts.gstatic.com">
	<link href="https://fonts.googleapis.com/css2?family=Poppins:ital,wght@0,100;0,200;0,300;0,400;0,500;0,600;0,700;0,800;0,900;1,100;1,200;1,300;1,400;1,500;1,600;1,700;1,800;1,900&display=swap" rel="stylesheet">
</head>
<body>
    <div class="container">
        <jsp:include page="header.jsp" />

        <main class="product-page">
            <div class="product-banner">
                <h1>ONLINE SHOP</h1>
                <img src = "${pageContext.request.contextPath}/assets/saleban.png">
                <br>	
                <h2>Explore Our Exquisite Collection Of Hand Crafted Furniture.</h2>
            </div>
            
	
            <div class="product-list">
            	<div class="product-item">
                    <div class="product-image">
                        <img src = "${pageContext.request.contextPath}/assets/sofa prod.jpg">
                    </div>
                    <div class="product-details">
                        <h3>SOFA</h3>
                        <p class="price">$200.00</p>
                        <p class="description">Plush and inviting, the perfect centerpiece for comfortable lounging.</p>
                    </div>
                </div>

                <div class="product-item">
                    <div class="product-image">
                        <img src = "${pageContext.request.contextPath}/assets/table.jpg">
                    </div>
                    <div class="product-details">
                        <h3>TABLE</h3>
                        <p class="price">$200.00</p>
                        <p class="description">A dedicated workspace blending functionality with elegant design.</p>
                    </div>
                </div>

                <div class="product-item">
                    <div class="product-image">
                        <img src = "${pageContext.request.contextPath}/assets/wardrobe.jpg">
                    </div>
                    <div class="product-details">
                        <h3>WARDROBE</h3>
                        <p class="price">$200.00</p>
                        <p class="description">Offering ample drawer space to keep your essentials neatly tucked away.</p>
                    </div>
                </div>

                <div class="product-item">
                    <div class="product-image">
                        <img src = "${pageContext.request.contextPath}/assets/dining.jpg">
                    </div>
                    <div class="product-details">
                        <h3>DINING</h3>
                        <p class="price">$200.00</p>
                        <p class="description">A versatile surface for dining, working, or gathering with loved ones.</p>
                    </div>
            	</div>
            </div>
            <div class="product-list">
            	<div class="product-item">
                    <div class="product-image">
                        <img src = "${pageContext.request.contextPath}/assets/bed.jpg">
                    </div>
                    <div class="product-details">
                        <h3>BED</h3>
                        <p class="price">$200.00</p>
                        <p class="description">Plush and inviting, the perfect centerpiece for comfortable lounging.</p>
                    </div>
                </div>

                <div class="product-item">
                    <div class="product-image">
                        <img src = "${pageContext.request.contextPath}/assets/bench.jpeg">
                    </div>
                    <div class="product-details">
                        <h3>BENCH</h3>
                        <p class="price">$200.00</p>
                        <p class="description">A dedicated workspace blending functionality with elegant design.</p>
                    </div>
                </div>

                <div class="product-item">
                    <div class="product-image">
                        <img src = "${pageContext.request.contextPath}/assets/cabinet.jpeg">
                    </div>
                    <div class="product-details">
                        <h3>CABINET</h3>
                        <p class="price">$200.00</p>
                        <p class="description">Offering ample drawer space to keep your essentials neatly tucked away.</p>
                    </div>
                </div>

                <div class="product-item">
                    <div class="product-image">
                        <img src = "${pageContext.request.contextPath}/assets/chair.jpg">
                    </div>
                    <div class="product-details">
                        <h3>CHAIR</h3>
                        <p class="price">$200.00</p>
                        <p class="description">A versatile surface for dining, working, or gathering with loved ones.</p>
                    </div>
            	</div>
            </div>
        </main>

        <jsp:include page="footer.jsp" />
    </div>
</body>
</html>