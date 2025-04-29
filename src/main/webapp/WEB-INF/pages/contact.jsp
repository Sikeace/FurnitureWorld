<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Contact Us - Furniture World</title>
    <link rel="stylesheet" href="css/style.css">
    <link rel="stylesheet" href="css/header.css">
    <link rel="stylesheet" href="css/contact.css">
    <link rel="stylesheet" href="css/footer.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css" integrity="sha512-SfTiTlX6kk+qitfevl/7LibUOeJWlt9rbyDn92a1DqWOw9vWG2MFoays0sgObmWazO5BQPiFucnnEAjpAB+/Sw==" crossorigin="anonymous" referrerpolicy="no-referrer" />
	<link rel="preconnect" href="https://fonts.googleapis.com">
	<link rel="preconnect" href="https://fonts.gstatic.com">
	<link href="https://fonts.googleapis.com/css2?family=Poppins:ital,wght@0,100;0,200;0,300;0,400;0,500;0,600;0,700;0,800;0,900;1,100;1,200;1,300;1,400;1,500;1,600;1,700;1,800;1,900&display=swap" rel="stylesheet">
</head>
<body>
    <div class="container">
        <jsp:include page="header.jsp" />

        <main class="contact-page">
            <section class="contact-info">
                <h2>CONTACT US</h2>
                <div class="contact-details">
                    <p class="detail-item">
                        <strong><i class="fa fa-phone" aria-hidden="true"></i>PHONE</strong><br>
                        123-456-7890
                    </p>
                    <p class="detail-item">
                        <strong><i class="fa fa-envelope" aria-hidden="true"></i>EMAIL</strong><br>
                        furnitureworld@gmail.com
                    </p>
                    <p class="detail-item">
                        <strong><i class="fa fa-map-marker" aria-hidden="true"></i>ADDRESS</strong><br>
                        Old Baneshwor St221, Kathmandu
                    </p>
                    <p class="detail-item">
                        <strong><i class="fa fa-users" aria-hidden="true"></i>WEBSITE</strong><br>
                        www.furnitureworld.com
                    </p>
                     <p class="detail-item">
                        <strong><i class="fa fa-twitter" aria-hidden="true"></i>TWITTER</strong><br>
                       @FurnitureWorld
                    </p>
                    <p class="detail-item">
                        <strong><i class="fa fa-instagram" aria-hidden="true"></i>INSTAGRAM</strong><br>
                       @BestFurnitureWorld
                    </p>
                </div>
            </section>

            <section class="contact-image">
                <img src="${pageContext.request.contextPath}/assets/map.png" >
            </section>
        </main>

        <jsp:include page="footer.jsp" />
    </div>
</body>
</html>