<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Login - Shopping Website</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body>
<jsp:include page="/jsp/header.jsp" />

<main>
    <div class="form-container">
        <h2>Login</h2>
        <form action="${pageContext.request.contextPath}/shopping/login" method="post" novalidate>
            <c:if test="${not empty sessionScope.successMessage}">
                <div class="success-message">
                        ${sessionScope.successMessage}
                </div>
                <c:remove var="successMessage" scope="session" />
            </c:if>
            <c:if test="${not empty error}">
                <div class="error">
                        ${error}
                </div>
            </c:if>
            <div class="form-group">
                <label for="username">Username:</label>
                <input type="text" id="username" name="username" required
                       oninvalid="this.setCustomValidity('Please fill in this field')"
                       oninput="this.setCustomValidity('')">
            </div>
            <div class="form-group">
                <label for="password">Password:</label>
                <input type="password" id="password" name="password" required
                       oninvalid="this.setCustomValidity('Please fill in this field')"
                       oninput="this.setCustomValidity('')">
            </div>
            <button type="submit" class="cta-button">Login</button>
        </form>
        <div class="form-footer">
            <p>Don't have an account? <a href="${pageContext.request.contextPath}/shopping/register">Register here</a></p>
            <p>Want to change password? <a href="${pageContext.request.contextPath}/shopping/changePassword">Change Password</a></p>
        </div>
    </div>
</main>

<jsp:include page="/jsp/footer.jsp" />
</body>
</html>







