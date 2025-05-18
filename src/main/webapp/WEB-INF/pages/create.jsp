<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Create - Furniture World</title>
    <link rel="stylesheet" href="css/style.css">
    <link rel="stylesheet" href="css/header.css">
    <link rel="stylesheet" href="css/create.css">
    <link rel="stylesheet" href="css/footer.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css" integrity="sha512-SfTiTlX6kk+qitfevl/7LibUOeJWlt9rbyDn92a1DqWOw9vWG2MFoays0sgObmWazO5BQPiFucnnEAjpAB+/Sw==" crossorigin="anonymous" referrerpolicy="no-referrer" />
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com">
    <link href="https://fonts.googleapis.com/css2?family=Poppins:ital,wght@0,100;0,200;0,300;0,400;0,500;0,600;0,700;0,800;0,900;1,100;1,200;1,300;1,400;1,500;1,600;1,700;1,800;1,900&display=swap" rel="stylesheet">
    <title>Create</title>
</head>
<body>
    <div class="container">
        <jsp:include page="header.jsp" />

        <div class="create-container">
            <h2>Create New Furniture Item</h2>
            <form action="${pageContext.request.contextPath}/create" method="post">
                <label for="furnitureName">Furniture Name:</label>
                <input type="text" id="furnitureName" name="furnitureName" required>

                <label for="categoryId">Category:</label>
                <select id="categoryId" name="categoryId" required>
                    <c:forEach var="category" items="${categoryList}">
                        <option value="${category.category_id}">${category.category_name}</option>
                    </c:forEach>
                </select>

                <label for="price">Price:</label>
                <input type="number" step="0.01" id="price" name="price" required>

                <button type="submit">Create Furniture</button>
            </form>

            <h2 class="existing-furniture-title">Existing Furniture</h2>
            <c:if test="${not empty furnitureList}">
                <table class="furniture-table">
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Name</th>
                            <th>Category</th>
                            <th>Price (₹)</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="furniture" items="${furnitureList}">
                            <tr>
                                <td>${furniture.furniture_id}</td>
                                <td>${furniture.furniture_name}</td>
                                <td>${furniture.category_name}</td>
                                <td>${furniture.price}</td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </c:if>
            <c:if test="${empty furnitureList}">
                <p>No furniture items available.</p>
            </c:if>
        </div>
    </div>
    <jsp:include page="footer.jsp" />
</body>
</html>