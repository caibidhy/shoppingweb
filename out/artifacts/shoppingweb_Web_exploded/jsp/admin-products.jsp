<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Product Management</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/styles.css">
    <style>
        .admin-table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }
        .admin-table th, .admin-table td {
            border: 1px solid #ddd;
            padding: 8px;
            text-align: left;
        }
        .admin-table th {
            background-color: #f4f4f4;
        }
        .action-buttons {
            display: flex;
            gap: 10px;
        }
        .edit-btn, .delete-btn {
            padding: 5px 10px;
            border: none;
            border-radius: 3px;
            cursor: pointer;
            color: white;
        }
        .edit-btn {
            background-color: #4CAF50;
        }
        .delete-btn {
            background-color: #dc3545;
        }
        .add-product-btn {
            background-color: #007bff;
            color: white;
            padding: 10px 20px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            margin-bottom: 20px;
        }
        .edit-input {
            width: 100%;
            padding: 5px;
            border: 1px solid #ddd;
            border-radius: 4px;
        }
        #completeEdit {
            background-color: #28a745;
            color: white;
            padding: 10px 20px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            margin: 20px auto;
            display: none;
        }
    </style>
</head>
<body>
<jsp:include page="/jsp/header.jsp" />

<main>
    <div class="container">
        <h1>Product Management</h1>
        <button class="add-product-btn" onclick="showAddProductForm()">Add New Product</button>

        <table class="admin-table">
            <thead>
            <tr>
                <th>ID</th>
                <th>Image</th>
                <th>Name</th>
                <th>Description</th>
                <th>Price</th>
                <th>Stock</th>
                <th>Actions</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="product" items="${products}">
                <tr data-product-id="${product.id}">
                    <td>
                        <span class="display-text">${product.id}</span>
                        <input type="number" class="edit-input" style="display:none" value="${product.id}">
                    </td>
                    <td>
                        <img src="${pageContext.request.contextPath}${product.imageUrl}"
                             alt="${product.name}"
                             style="width: 50px; height: 50px; object-fit: cover;">
                        <input type="file" class="edit-input" style="display:none" accept="image/*">
                    </td>
                    <td>
                        <span class="display-text">${product.name}</span>
                        <input type="text" class="edit-input" style="display:none" value="${product.name}">
                    </td>
                    <td>
                        <span class="display-text">${product.description}</span>
                        <input type="text" class="edit-input" style="display:none" value="${product.description}">
                    </td>
                    <td>
                        <span class="display-text">$${product.price}</span>
                        <input type="number" step="0.01" class="edit-input" style="display:none" value="${product.price}">
                    </td>
                    <td>
                        <span class="display-text">${product.stockQuantity}</span>
                        <input type="number" class="edit-input" style="display:none" value="${product.stockQuantity}">
                    </td>
                    <td>
                        <div class="action-buttons">
                            <button class="edit-btn" onclick="toggleEdit(this)">Edit</button>
                            <button class="delete-btn" onclick="deleteProduct(${product.id})">Delete</button>
                        </div>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        <button id="completeEdit" onclick="saveAllChanges()">Complete Editing</button>
    </div>

    <!-- Add Product Modal -->
    <div id="addModal" class="modal" style="display: none;">
        <div class="modal-content">
            <span class="close" onclick="closeModal('addModal')">&times;</span>
            <h2>Add New Product</h2>
            <form id="addForm" enctype="multipart/form-data">
                <div class="form-group">
                    <label for="addName">Name:</label>
                    <input type="text" id="addName" name="name" required>
                </div>
                <div class="form-group">
                    <label for="addDescription">Description:</label>
                    <textarea id="addDescription" name="description" required></textarea>
                </div>
                <div class="form-group">
                    <label for="addPrice">Price:</label>
                    <input type="number" id="addPrice" name="price" step="0.01" required>
                </div>
                <div class="form-group">
                    <label for="addStock">Stock:</label>
                    <input type="number" id="addStock" name="stockQuantity" required>
                </div>
                <div class="form-group">
                    <label for="addImage">Image:</label>
                    <input type="file" id="addImage" name="image" accept="image/*" required>
                </div>
                <button type="submit">Add Product</button>
            </form>
        </div>
    </div>
</main>

<script>
    let editingRows = new Set();

    function showAddProductForm() {
        document.getElementById('addModal').style.display = 'block';
    }

    function closeModal(modalId) {
        document.getElementById(modalId).style.display = 'none';
    }

    function toggleEdit(button) {
        const row = button.closest('tr');
        const displayElements = row.querySelectorAll('.display-text');
        const editInputs = row.querySelectorAll('.edit-input');

        if (!editingRows.has(row)) {
            editingRows.add(row);
            displayElements.forEach(el => el.style.display = 'none');
            editInputs.forEach(el => el.style.display = 'block');
            button.textContent = 'Cancel';
            document.getElementById('completeEdit').style.display = 'block';
        } else {
            editingRows.delete(row);
            displayElements.forEach(el => el.style.display = 'block');
            editInputs.forEach(el => el.style.display = 'none');
            button.textContent = 'Edit';
            if (editingRows.size === 0) {
                document.getElementById('completeEdit').style.display = 'none';
            }
        }
    }

    function saveAllChanges() {
        const promises = Array.from(editingRows).map(row => {
            const formData = new FormData();
            const productId = row.dataset.productId;

            // 添加新的ID
            const newId = row.querySelector('td:first-child input[type="number"]').value;
            formData.append('newId', newId);

            formData.append('action', 'edit');
            formData.append('id', productId);
            formData.append('name', row.querySelector('input[type="text"]').value);
            formData.append('description', row.querySelectorAll('input[type="text"]')[0].value);
            formData.append('price', row.querySelector('input[type="number"]').value);
            formData.append('stockQuantity', row.querySelectorAll('input[type="number"]')[1].value);

            const imageInput = row.querySelector('input[type="file"]');
            if (imageInput.files.length > 0) {
                formData.append('image', imageInput.files[0]);
            }

            return fetch('${pageContext.request.contextPath}/shopping/admin/products', {
                method: 'POST',
                body: formData
            });
        });

        Promise.all(promises)
            .then(() => window.location.reload())
            .catch(error => {
                console.error('Error saving changes:', error);
                alert('Failed to save some changes');
            });
    }

    function deleteProduct(productId) {
        if (!confirm('Are you sure you want to delete this product?')) {
            return;
        }

        fetch('${pageContext.request.contextPath}/shopping/admin/products', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded',
            },
            body: 'action=delete&id=' + productId
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                window.location.reload();
            })
            .catch(error => {
                console.error('Error:', error);
                alert('Failed to delete product. Please try again.');
            });
    }

    document.getElementById('addForm').addEventListener('submit', function(e) {
        e.preventDefault();
        const formData = new FormData(this);
        formData.append('action', 'add');

        fetch('${pageContext.request.contextPath}/shopping/admin/products', {
            method: 'POST',
            body: formData
        }).then(response => {
            if (response.ok) {
                window.location.reload();
            } else {
                alert('Failed to add product');
            }
        }).catch(error => {
            console.error('Error:', error);
            alert('An error occurred');
        });
    });

    window.onclick = function(event) {
        if (event.target.className === 'modal') {
            event.target.style.display = 'none';
        }
    }
</script>
</body>
</html>