<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Shopping Cart - Shopping Website</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body>
<jsp:include page="/jsp/header.jsp" />

<main class="container">
    <h1>Shopping Cart</h1>

    <c:if test="${empty sessionScope.cart or empty sessionScope.cart.items}">
        <p>Your cart is empty.</p>
        <a href="${pageContext.request.contextPath}/products" class="button">Continue Shopping</a>
    </c:if>

    <c:if test="${not empty sessionScope.cart and not empty sessionScope.cart.items}">
        <div class="cart-items">
            <c:forEach var="cartItem" items="${sessionScope.cart.items}">
                <div class="cart-item">
                    <div class="cart-item-content">
                        <img src="${pageContext.request.contextPath}/placeholder.svg" alt="Product ${cartItem.key}" class="cart-item-image">
                        <div class="cart-item-details">
                            <h3 class="cart-item-title">Product ${cartItem.key}</h3>
                            <p class="cart-item-price">Price: $19.99</p>
                            <p class="cart-item-quantity">Quantity: ${cartItem.value}</p>
                        </div>
                        <button onclick="removeFromCart(${cartItem.key})" class="remove-button">Remove</button>
                    </div>
                </div>
            </c:forEach>
        </div>

        <div class="cart-actions">
            <button onclick="checkout()" class="checkout-button">Checkout</button>
            <a href="${pageContext.request.contextPath}/products" class="continue-shopping">Continue Shopping</a>
        </div>
    </c:if>
</main>

<c:if test="${not empty sessionScope.cartMessage}">
    <div class="floating-notification" id="cartNotification">
            ${sessionScope.cartMessage}
        <button onclick="this.parentElement.style.display='none'" class="close-button">&times;</button>
    </div>
    <c:remove var="cartMessage" scope="session" />
</c:if>

<jsp:include page="/jsp/footer.jsp" />
<script src="${pageContext.request.contextPath}/js/main.js"></script>
</body>
</html>







