<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Shopping Cart</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body>
<jsp:include page="/jsp/header.jsp" />

<main class="container">
    <h1>Shopping Cart</h1>

    <c:if test="${not empty sessionScope.cartMessage}">
        <div class="floating-notification">
                ${sessionScope.cartMessage}
            <button onclick="this.parentElement.style.display='none'" class="close-button">&times;</button>
        </div>
        <c:remove var="cartMessage" scope="session" />
    </c:if>

    <!-- 外层统一容器 -->
    <div class="cart-container">
        <!-- 如果 cartItems 为空，显示空状态 -->
        <c:if test="${empty cartItems}">
            <div class="cart-empty-state">
                <p>Your cart is empty.</p>
                <a href="${pageContext.request.contextPath}/shopping/products" class="cta-button">Continue Shopping</a>
            </div>
        </c:if>

        <!-- 如果 cartItems 不为空，显示购物车商品列表 -->
        <c:if test="${not empty cartItems}">
            <div class="cart-items">
                <c:forEach var="item" items="${cartItems}">
                    <div class="cart-item">
                        <img src="${pageContext.request.contextPath}${item.imageUrl}"
                             alt="${item.name}" class="cart-item-image">
                        <div class="cart-item-details">
                            <h3>${item.name}</h3>
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

            <!-- 购物车底部操作 + 总价 -->
            <div class="cart-footer">
                <div class="cart-actions">
                    <button onclick="checkout()" class="checkout-button">Checkout</button>
                    <a href="${pageContext.request.contextPath}/shopping/products" class="continue-shopping">Continue Shopping</a>
                </div>
                <div class="cart-total-display">
                    <!-- 如果已计算 totalPrice -->
                    Total: $<c:out value="${totalPrice}" />
                </div>
            </div>
        </c:if>
    </div>
</main>

<jsp:include page="/jsp/footer.jsp" />
<script src="${pageContext.request.contextPath}/js/main.js"></script>
<script>
    function checkout() {
        alert("Implement your checkout logic here.");
    }
</script>
</body>
</html>










