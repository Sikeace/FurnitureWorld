<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Furniture World</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/header.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/home.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/footer.css">
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
                    <img src="${pageContext.request.contextPath}/assets/Furniturepurple.png" alt="Minimalist Furniture Design">
                </div>
                <div class="hero-text">
                	<h1>Handled With Care.</h1>
                    <h1>Super Easy To Pick.</h1>
                    <h1>Minimalistic Design.</h1>
                    <p>At Furniture World, we pride ourselves on offering not just variety but also exceptional quality craftsmanship in pieces built to last. Our commitment extends to providing personalized service, ensuring you find furniture that perfectly suits your taste, needs, and the distinct character of your Kathmandu home. We understand the importance of value, and strive to make beautiful, durable furniture accessible.</p>
                    <a href="${pageContext.request.contextPath}/product" class="button">SHOP NOW</a>
                </div>
            </div>

            <%-- Removed "Our Top Selling Products" and product grid --%>
            <%-- New Content Section --%>
            <section class="about-furniture-world">
                <h2>Discover Your Perfect Space with Furniture World</h2>

                <p>
                    Your home's furniture is more than just functional, it's a reflection of your unique style and the backdrop to your life's most cherished moments.
                    Great  are thoughtfully curated, user-friendly to explore, and clearly showcase the quality and design that can transform a house into a home, giving you a taste of the comfort and beauty you deserve.
                </p>
                <p>
                    The best furniture retailers, like Furniture World, use compelling designs, high-quality materials, and inspiring room ideas to showcase true craftsmanship. We engage our customers with an intuitive online experience, diverse style options, and personalized support. Our collections are also easily found using simple navigation and clear product details, letting you confidently choose pieces that will enhance your living spaces.
                </p>
                <p>
                    We've carefully curated our selection at Furniture World using the following criteria: exceptional design, lasting durability, sustainable practices where possible, and outstanding value. Each piece offers fantastic inspiration for new homeowners setting up their first space, individuals looking to refresh their current decor, or experienced designers seeking that perfect item.
                </p>
                <p>
                    Need a simple yet elegant way to find beautiful furniture quickly? Furniture World's intuitive website lets you discover standout pieces effortlessly—often in just a few clicks.
                   Explore our collections today and see how easy it is to create the home of your dreams!
                </p>

                <div class="content-divider"></div> <%-- Optional visual divider --%>

                <h3>How does your living space inspire you?</h3>
                <p>
                We warmly invite you to experience the quality and style firsthand at our showroom, conveniently located at Purano Baneshwor, Kathmandu. Let our friendly and knowledgeable team assist you in discovering the ideal pieces to transform your house into a home you'll cherish. You can also begin your journey by exploring our  Shop Our Collections  online or checking out our  View Latest Arrivals  to see what's new.
                </p>
                 <a href="${pageContext.request.contextPath}/product" class="button content-cta-button">View All Furniture</a>
            </section>
            <%-- End of New Content Section --%>

        </main>

        <jsp:include page="footer.jsp" />
    </div>
</body>
</html>