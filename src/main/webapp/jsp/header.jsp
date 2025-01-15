<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<header>
    <nav>
        <div class="nav-wrapper">
            <a href="${pageContext.request.contextPath}/shopping" class="brand-logo">Shopping Website</a>
            <ul class="right">
                <li><a href="${pageContext.request.contextPath}/shopping/products">Products</a></li>
                <li><a href="${pageContext.request.contextPath}/shopping/cart">Cart</a></li>
                <% if (session.getAttribute("username") == null) { %>
                <li><a href="${pageContext.request.contextPath}/shopping/login">Login</a></li>
                <li><a href="${pageContext.request.contextPath}/shopping/register">Register</a></li>
                <% } else { %>
                <li>Welcome, <%= session.getAttribute("username") %></li>
                <li><a href="${pageContext.request.contextPath}/shopping/logout">Logout</a></li>
                <% } %>
            </ul>
        </div>
    </nav>
</header>