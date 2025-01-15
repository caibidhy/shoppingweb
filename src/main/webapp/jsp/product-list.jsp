<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Products - Shopping Website</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body>
<jsp:include page="/jsp/header.jsp" />

<main>
    <h2>Our Products</h2>
    <div class="product-grid">
        <c:forEach items="${products}" var="product">
            <div class="product-card">
                <img src="${pageContext.request.contextPath}/images/products/${product.id}.jpg"
                     alt="${product.name}"
                     class="product-image">
                <h3>${product.name}</h3>
                <p class="price">$${product.price}</p>
                <p class="description">${product.description}</p>
                <form action="${pageContext.request.contextPath}/shopping/cart" method="post">
                    <input type="hidden" name="action" value="add">
                    <input type="hidden" name="productId" value="${product.id}">
                    <button type="submit" class="cta-button">Add to Cart</button>
                </form>
            </div>
        </c:forEach>
    </div>
</main>

<jsp:include page="/jsp/footer.jsp" />
<script src="${pageContext.request.contextPath}/js/main.js"></script>
</body>
</html>