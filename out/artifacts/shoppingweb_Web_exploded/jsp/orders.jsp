<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
    <title>My Orders</title>
</head>
<body>
<h1>My Orders</h1>
<c:forEach var="order" items="${orders}">
    <div class="order-box">
        <p>Order ID: ${order.id}</p>
        <p>Total Price: $${order.totalPrice}</p>
        <p>Create Time: ${order.createTime}</p>
        <c:forEach var="item" items="${order.items}">
            <div class="order-item">
                Product ID: ${item.productId} <br>
                Quantity: ${item.quantity} <br>
                Unit Price: $${item.unitPrice} <br>
            </div>
        </c:forEach>
    </div>
</c:forEach>
</body>
</html>
