<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>About Us - Furniture World</title>
    <link rel="stylesheet" href="css/style.css">
    <link rel="stylesheet" href="css/header.css">
    <link rel="stylesheet" href="css/about.css">
    <link rel="stylesheet" href="css/footer.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css" integrity="sha512-SfTiTlX6kk+qitfevl/7LibUOeJWlt9rbyDn92a1DqWOw9vWG2MFoays0sgObmWazO5BQPiFucnnEAjpAB+/Sw==" crossorigin="anonymous" referrerpolicy="no-referrer" />
	<link rel="preconnect" href="https://fonts.googleapis.com">
	<link rel="preconnect" href="https://fonts.gstatic.com">
	<link href="https://fonts.googleapis.com/css2?family=Poppins:ital,wght@0,100;0,200;0,300;0,400;0,500;0,600;0,700;0,800;0,900;1,100;1,200;1,300;1,400;1,500;1,600;1,700;1,800;1,900&display=swap" rel="stylesheet">
</head>
<body>
    <div class="container">
        <jsp:include page="header.jsp" />

        <main class="about-page">
            <section class="about-hero">
                <div class="hero-image">
                    <img src="${pageContext.request.contextPath}/assets/group1.png" >
                </div>
                <div class="hero-text">
                    <h2>CEO</h2>
                    <h1>Kayosh Raj Pradhan </h1>
                    <p>Furniture World was founded with a clear vision to become the leading destination in Purano Baneshwor, Kathmandu, for individuals and families seeking to create beautiful, comfortable, and functional living spaces. More than just a retail store, we are a team deeply passionate about home furnishings and dedicated to understanding the unique aspirations of our community. From our showroom in the vibrant heart of Purano Baneshwor, we've made it our mission to help our neighbours transform their houses into homes that truly reflect their personalities and lifestyles, one carefully chosen piece at a time.</p>
                    <p>Our core mission is to provide an exceptional selection of quality-crafted furniture, ensuring durability and lasting style for every item that leaves our store. We believe in offering diverse styles, from contemporary trends to timeless classics and designs that resonate with traditional Nepali aesthetics, all at fair value. Central to our ethos is an unwavering commitment to customer satisfaction; our knowledgeable team is always on hand to offer personalized guidance, helping you navigate our collections to find the perfect items. We are proud to be a local business, dedicated to serving the Kathmandu community with integrity and a genuine desire to enhance the comfort and beauty of your homes.</p>
                    	<div class="Thankyou">
                		<h1>Where Comfort Meets Style</h1>
                		</div>
                </div>
                
            </section>
        </main>

        <jsp:include page="footer.jsp" />
    </div>
</body>
</html>