<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Login - Shopping Website</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body>
<jsp:include page="/jsp/header.jsp" />

<main>
    <div class="form-container">
        <h2>Login</h2>
        <form action="${pageContext.request.contextPath}/shopping/login" method="post">
            <c:if test="${not empty error}">
                <p class="error">${error}</p>
            </c:if>
            <div class="form-group">
                <label for="username">Username:</label>
                <input type="text" id="username" name="username" required>
            </div>
            <div class="form-group">
                <label for="password">Password:</label>
                <input type="password" id="password" name="password" required>
            </div>
            <button type="submit" class="cta-button">Login</button>
        </form>
        <p class="form-footer">Don't have an account? <a href="${pageContext.request.contextPath}/shopping/register">Register here</a></p>
    </div>
</main>

<jsp:include page="/jsp/footer.jsp" />
</body>
</html>