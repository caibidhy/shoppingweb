<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Welcome to Our Shopping Website</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body>
<jsp:include page="/jsp/header.jsp" />

<main>
    <section class="hero">
        <h1>Welcome to Our Shopping Website</h1>
        <p>Discover amazing products at great prices!</p>
        <a href="${pageContext.request.contextPath}/shopping/products" class="cta-button">Shop Now</a>
    </section>
</main>

<jsp:include page="/jsp/footer.jsp" />
<script src="${pageContext.request.contextPath}/js/main.js"></script>
</body>
</html>








