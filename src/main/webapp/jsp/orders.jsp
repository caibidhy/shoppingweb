<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <title>My Orders</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body>
<jsp:include page="/jsp/header.jsp" />

<main>
    <div class="container">
        <h1>My Orders</h1>

        <c:if test="${empty orders}">
            <div class="cart-empty-state">
                <p>You haven't placed any orders yet.</p>
                <a href="${pageContext.request.contextPath}/shopping/products" class="cta-button">Start Shopping</a>
            </div>
        </c:if>

        <c:if test="${not empty orders}">
            <c:forEach var="order" items="${orders}">
                <div class="order-box">
                    <h3>Order ID: ${order.id}</h3>
                    <p>Order Date: ${order.createTime}</p>
                    <p>Total Amount: $${order.totalPrice}</p>

                    <div class="order-items">
                        <h4>Order Items:</h4>
                        <c:forEach var="item" items="${order.orderItems}">
                            <div class="order-item">
                                <p>Product ID: ${item.productId}</p>
                                <p>Quantity: ${item.quantity}</p>
                                <p>Unit Price: $${item.unitPrice}</p>
                            </div>
                        </c:forEach>
                    </div>
                </div>
            </c:forEach>
        </c:if>
    </div>
</main>

<jsp:include page="/jsp/footer.jsp" />
</body>
</html>
