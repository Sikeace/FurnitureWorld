<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin Dashboard - Furniture World</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/header.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/admin.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/footer.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css" integrity="sha512-SfTiTlX6kk+qitfevl/7LibUOeJWlt9rbyDn92a1DqWOw9vWG2MFoays0sgObmWazO5BQPiFucnnEAjpAB+/Sw==" crossorigin="anonymous" referrerpolicy="no-referrer" />
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Poppins:ital,wght@0,100;0,200;0,300;0,400;0,500;0,600;0,700;0,800;0,900;1,100;1,200;1,300;1,400;1,500;1,600;1,700;1,800;1,900&display=swap" rel="stylesheet">
</head>
<body>
    <div class="container"> <%-- Changed class for main wrapper --%>
        <jsp:include page="header.jsp" />
    
        <h1>Furniture Dashboard</h1>

        <div class="card-container">
            <div class="dashboard-card">
                <h2>Total Categories</h2>
                <p><c:out value="${totalCategories}" /></p>
            </div>
            <div class="dashboard-card">
                <h2>Total Furniture Items</h2>
                <p><c:out value="${totalFurniture}" /></p>
            </div>
            <div class="dashboard-card">
                <h2>Total Admins</h2>
                <p><c:out value="${totalAdmins}" /></p>
            </div>
            <div class="dashboard-card">
                <h2>Total Users</h2>
                <p><c:out value="${totalUsers}" /></p>
            </div>
            <div class="dashboard-card">
                <h2>Total Inventory Value</h2>
                <p>₹ <fmt:formatNumber value="${totalInventoryValue}" type="number" minFractionDigits="2" maxFractionDigits="2"/></p>
            </div>
        </div>

        <%-- Search Bar Section --%>
        <div class="search-container">
            <form action="${pageContext.request.contextPath}/admin" method="GET" class="search-form">
                <input type="text" name="searchTerm" placeholder="Search furniture by name..." 
                       value="<c:out value="${searchTerm}"/>" class="search-input">
                <button type="submit" class="search-button"><i class="fa fa-search"></i> Search</button>
                <c:if test="${not empty searchTerm}">
                    <a href="${pageContext.request.contextPath}/admin" class="clear-search-button">Clear Search</a>
                </c:if>
            </form>
        </div>

        <h2>
            <c:choose>
                <c:when test="${isSearchResult}">
                    Search Results for "<c:out value="${searchTerm}"/>"
                </c:when>
                <c:otherwise>
                    Furniture Inventory
                </c:otherwise>
            </c:choose>
        </h2>
        <table class="furniture-table">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Furniture Name</th>
                    <th>Category</th>
                    <th>Price (₹)</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="furniture" items="${furnitureList}">
                    <tr>
                        <td><c:out value="${furniture.furniture_id}" /></td>
                        <td><c:out value="${furniture.furniture_name}" /></td>
                        <td><c:out value="${furniture.category_name}" /></td>
                        <td><fmt:formatNumber value="${furniture.price}" type="number" minFractionDigits="2" maxFractionDigits="2"/></td>
                        <td>
                            <button class="action-button edit-button" 
                                    onclick="openUpdateModal(${furniture.furniture_id}, '${furniture.furniture_name}', ${furniture.category_id}, ${furniture.price})">
                                <i class="fa fa-pencil"></i> Edit
                            </button>
                            <button class="action-button delete-button" 
                                    onclick="openDeleteModal(${furniture.furniture_id}, '${furniture.furniture_name}')">
                                <i class="fa fa-trash"></i> Delete
                            </button>
                        </td>
                    </tr>
                </c:forEach>
                <c:if test="${empty furnitureList}">
                    <tr>
                        <td colspan="5">
                            <c:choose>
                                <c:when test="${isSearchResult}">
                                    No furniture items found matching your search criteria.
                                </c:when>
                                <c:otherwise>
                                    No furniture items found.
                                </c:otherwise>
                            </c:choose>
                        </td>
                    </tr>
                </c:if>
            </tbody>	
        </table>

        <div class="button-group">
            <a id="createBtn" class="admin-button"><i class="fa fa-plus-circle"></i> Add Furniture</a>
        </div>
    </div>

    <!-- Add Furniture Modal -->
    <div id="createModal" class="modal">
        <div class="modal-content">
            <div class="modal-header">
                <h2>Add New Furniture</h2>
                <span class="close-btn" id="closeCreateModal">×</span>
            </div>
            <form id="createForm" method="POST" action="${pageContext.request.contextPath}/admin">
                <input type="hidden" name="action" value="create">
                <div class="form-group">
                    <label for="createFurnitureName">Furniture Name</label>
                    <input type="text" id="createFurnitureName" name="furniture_name" class="form-control" required>
                </div>
                <div class="form-group">
                    <label for="createCategoryId">Category</label>
                    <select id="createCategoryId" name="category_id" class="form-control" required>
                        <option value="">-- Select Category --</option>
                        <c:forEach var="category" items="${categories}">
                            <option value="${category.category_id}"><c:out value="${category.category_name}" /></option>
                        </c:forEach>
                    </select>
                </div>
                <div class="form-group">
                    <label for="createPrice">Price (₹)</label>
                    <input type="number" id="createPrice" name="price" class="form-control" min="0" step="0.01" required>
                </div>
                <div class="button-group-modal">
                    <button type="button" class="modal-button cancel-button close-create-modal">Cancel</button>
                    <button type="submit" class="modal-button save-button">Add Furniture</button>
                </div>
            </form>
        </div>
    </div>

    <!-- Update Modal -->
    <div id="updateModal" class="modal">
        <div class="modal-content">
            <div class="modal-header">
                <h2>Update Furniture</h2>
                <span class="close-btn" id="closeUpdateModal">×</span>
            </div>
            <form id="updateForm" method="POST" action="${pageContext.request.contextPath}/admin">
                <input type="hidden" name="action" value="update">
                <input type="hidden" id="updateFurnitureId" name="furniture_id">
                <div class="form-group">
                    <label for="updateFurnitureName">Furniture Name</label>
                    <input type="text" id="updateFurnitureName" name="furniture_name" class="form-control" required>
                </div>
                <div class="form-group">
                    <label for="updateCategoryId">Category</label>
                    <select id="updateCategoryId" name="category_id" class="form-control" required>
                        <option value="">-- Select Category --</option>
                        <c:forEach var="category" items="${categories}">
                            <option value="${category.category_id}"><c:out value="${category.category_name}" /></option>
                        </c:forEach>
                    </select>
                </div>
                <div class="form-group">
                    <label for="updatePrice">Price (₹)</label>
                    <input type="number" id="updatePrice" name="price" class="form-control" min="0" step="0.01" required>
                </div>
                <div class="button-group-modal">
                    <button type="button" class="modal-button cancel-button close-update-modal">Cancel</button>
                    <button type="submit" class="modal-button save-button">Save Changes</button>
                </div>
            </form>
        </div>
    </div>

    <!-- Delete Modal -->
    <div id="deleteModal" class="modal">
        <div class="modal-content">
            <div class="modal-header">
                <h2>Delete Furniture</h2>
                <span class="close-btn" id="closeDeleteModal">×</span>
            </div>
            <form id="deleteForm" method="POST" action="${pageContext.request.contextPath}/admin">
                <input type="hidden" name="action" value="delete">
                <input type="hidden" id="deleteFurnitureId" name="furniture_id">
                <p>Are you sure you want to delete "<strong id="deleteFurnitureName"></strong>"?</p>
                <p>This action cannot be undone.</p>
                <div class="button-group-modal">
                    <button type="button" class="modal-button cancel-button close-delete-modal">Cancel</button>
                    <button type="submit" class="modal-button save-button delete-confirm-button">Delete</button>
                </div>
            </form>
        </div>
    </div>

    <script>
        // Get modal elements
        const createModal = document.getElementById("createModal");
        const updateModal = document.getElementById("updateModal");
        const deleteModal = document.getElementById("deleteModal");

        const createBtn = document.getElementById("createBtn");

        const closeCreateModalBtn = document.getElementById("closeCreateModal");
        const closeUpdateModalBtn = document.getElementById("closeUpdateModal");
        const closeDeleteModalBtn = document.getElementById("closeDeleteModal");

        // Get all elements with class="close-modal-btn-footer" (cancel buttons in modals)
        const closeCreateBtnsByClass = document.getElementsByClassName("close-create-modal");
        const closeUpdateBtnsByClass = document.getElementsByClassName("close-update-modal");
        const closeDeleteBtnsByClass = document.getElementsByClassName("close-delete-modal");


        if(createBtn) {
            createBtn.onclick = function() {
                document.getElementById("createForm").reset();
                createModal.style.display = "block";
                document.body.style.overflow = "hidden"; // Prevent background scroll
            }
        }

        function openUpdateModal(id, name, categoryId, price) {
            document.getElementById("updateForm").reset();
            document.getElementById("updateFurnitureId").value = id;
            document.getElementById("updateFurnitureName").value = name;
            document.getElementById("updateCategoryId").value = categoryId; // Set selected category
            document.getElementById("updatePrice").value = price;
            updateModal.style.display = "block";
            document.body.style.overflow = "hidden";
        }

        function openDeleteModal(id, name) {
            document.getElementById("deleteForm").reset();
            document.getElementById("deleteFurnitureId").value = id;
            document.getElementById("deleteFurnitureName").textContent = name;
            deleteModal.style.display = "block";
            document.body.style.overflow = "hidden";
        }

        // Generic function to close any modal
        function closeModal(modalElement) {
            if (modalElement) {
                modalElement.style.display = "none";
                document.body.style.overflow = "auto"; // Restore background scroll
            }
        }
        
        // Close modals using X button
        if(closeCreateModalBtn) closeCreateModalBtn.onclick = function() { closeModal(createModal); }
        if(closeUpdateModalBtn) closeUpdateModalBtn.onclick = function() { closeModal(updateModal); }
        if(closeDeleteModalBtn) closeDeleteModalBtn.onclick = function() { closeModal(deleteModal); }

        // Close modals using cancel buttons in footer
        for (let i = 0; i < closeCreateBtnsByClass.length; i++) {
            closeCreateBtnsByClass[i].onclick = function() { closeModal(createModal); }
        }
        for (let i = 0; i < closeUpdateBtnsByClass.length; i++) {
            closeUpdateBtnsByClass[i].onclick = function() { closeModal(updateModal); }
        }
        for (let i = 0; i < closeDeleteBtnsByClass.length; i++) {
            closeDeleteBtnsByClass[i].onclick = function() { closeModal(deleteModal); }
        }


        // Close when clicking outside of modal content
        window.onclick = function(event) {
            if (event.target == createModal) closeModal(createModal);
            if (event.target == updateModal) closeModal(updateModal);
            if (event.target == deleteModal) closeModal(deleteModal);
        }
    </script>
    
    <br><br><br>
    <jsp:include page="footer.jsp" />
</body>
</html>