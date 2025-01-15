<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Register - Shopping Website</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body>
<jsp:include page="/jsp/header.jsp" />

<main>
    <div class="form-container">
        <h2>Register</h2>
        <form action="${pageContext.request.contextPath}/shopping/register" method="post">
            <div class="form-group">
                <label for="username">Username:</label>
                <input type="text" id="username" name="username" required>
            </div>
            <div class="form-group">
                <label for="email">Email:</label>
                <input type="email" id="email" name="email" required>
            </div>
            <div class="form-group">
                <label for="password">Password:</label>
                <input type="password" id="password" name="password" required>
            </div>
            <div class="form-group">
                <label for="confirm-password">Confirm Password:</label>
                <input type="password" id="confirm-password" name="confirm-password" required>
            </div>
            <button type="submit" class="cta-button">Register</button>
        </form>
        <p class="form-footer">Already have an account? <a href="${pageContext.request.contextPath}/shopping/login">Login here</a></p>
    </div>
</main>

<jsp:include page="/jsp/footer.jsp" />
</body>
</html>