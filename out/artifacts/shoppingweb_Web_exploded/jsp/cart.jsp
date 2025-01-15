<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Shopping Cart - Shopping Website</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body>
<jsp:include page="/jsp/header.jsp" />

<main>
    <h2>Your Shopping Cart</h2>
    <div class="cart-items">
        <c:if test="${empty cart.items}">
            <p class="cart-empty">Your cart is empty</p>
        </c:if>
        <c:forEach items="${cart.items}" var="item">
            <div class="cart-item">
                <img src="${pageContext.request.contextPath}/images/products/${item.product.id}.jpg"
                     alt="${item.product.name}"
                     class="cart-item-image">
                <div class="item-details">
                    <h3>${item.product.name}</h3>
                    <p class="price">Price: $${item.product.price}</p>
                    <p class="quantity">Quantity: ${item.quantity}</p>
                </div>
                <form action="${pageContext.request.contextPath}/shopping/cart" method="post">
                    <input type="hidden" name="action" value="remove">
                    <input type="hidden" name="productId" value="${item.product.id}">
                    <button type="submit" class="remove-button">Remove</button>
                </form>
            </div>
        </c:forEach>

        <c:if test="${not empty cart.items}">
            <div class="cart-total">
                <h3>Total: $${cart.total}</h3>
                <form action="${pageContext.request.contextPath}/shopping/checkout" method="post">
                    <button type="submit" class="cta-button">Proceed to Checkout</button>
                </form>
            </div>
        </c:if>
    </div>
</main>

<jsp:include page="/jsp/footer.jsp" />
<script src="${pageContext.request.contextPath}/js/main.js"></script>
</body>
</html>