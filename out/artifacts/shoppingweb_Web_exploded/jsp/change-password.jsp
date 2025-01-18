<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Change Password</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body>
<jsp:include page="/jsp/header.jsp" />

<main>
    <div class="form-container">
        <h2>Change Password</h2>

        <!-- 错误信息提示 -->
        <c:if test="${not empty error}">
            <div class="error">
                    ${error}
            </div>
        </c:if>

        <!-- 成功信息提示 -->
        <c:if test="${not empty success}">
            <div class="success-message">
                    ${success}
            </div>
        </c:if>

        <form action="${pageContext.request.contextPath}/shopping/changePassword" method="post">
            <div class="form-group">
                <label for="username">Username:</label>
                <input type="text" id="username" name="username" required>
            </div>

            <div class="form-group">
                <label for="oldPassword">Old Password:</label>
                <input type="password" id="oldPassword" name="oldPassword" required>
            </div>

            <div class="form-group">
                <label for="newPassword">New Password:</label>
                <input type="password" id="newPassword" name="newPassword" required>
            </div>

            <div class="form-group">
                <label for="confirmPassword">Confirm New Password:</label>
                <input type="password" id="confirmPassword" name="confirmPassword" required>
            </div>

            <button type="submit" class="cta-button">Change Password</button>
        </form>

        <p class="form-footer">
            <a href="${pageContext.request.contextPath}/shopping/login">Back to Login</a>
        </p>
    </div>
</main>

<jsp:include page="/jsp/footer.jsp" />
</body>
</html>