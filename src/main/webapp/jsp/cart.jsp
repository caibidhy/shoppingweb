<%-- cart.jsp --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Shopping Cart</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body>
<jsp:include page="/jsp/header.jsp"/>

<main class="container">
    <h1>Shopping Cart</h1>

    <c:if test="${not empty sessionScope.cartMessage}">
        <div class="floating-notification">
                ${sessionScope.cartMessage}
            <button onclick="this.parentElement.style.display='none'" class="close-button">&times;</button>
        </div>
        <c:remove var="cartMessage" scope="session" />
    </c:if>

    <c:if test="${empty cartItems}">
        <p>Your cart is empty.</p>
        <a href="${pageContext.request.contextPath}/shopping/products" class="button">Continue Shopping</a>
    </c:if>

    <c:if test="${not empty cartItems}">
        <div class="cart-items">
            <c:forEach var="item" items="${cartItems}">
                <div class="cart-item">
                    <img src="${pageContext.request.contextPath}${item.imageUrl}"
                         alt="${item.name}" class="cart-item-image">
                    <div class="cart-item-details">
                        <h3>Product ${item.productId}: ${item.name}</h3>
                        <p>Price: $${item.price}</p>
                        <p>Quantity: ${item.quantity}</p>
                    </div>

                    <form action="${pageContext.request.contextPath}/shopping/cart" method="post">
                        <input type="hidden" name="action" value="remove" />
                        <input type="hidden" name="productId" value="${item.productId}" />
                        <button type="submit" class="remove-button">Remove</button>
                    </form>
                </div>
            </c:forEach>
        </div>

        <div class="cart-actions">
            <button onclick="checkout()" class="checkout-button">Checkout</button>
            <a href="${pageContext.request.contextPath}/shopping/products" class="continue-shopping">Continue Shopping</a>
        </div>

        <!-- 这里显示总价 -->
        <div class="cart-total">
            <strong>Total:</strong> $<c:out value="${totalPrice}"/>
        </div>
    </c:if>
</main>

<jsp:include page="/jsp/footer.jsp"/>
<script src="${pageContext.request.contextPath}/js/main.js"></script>
<script>
    function checkout() {
        alert("Implement your checkout logic here.");
    }
</script>
</body>
</html>









