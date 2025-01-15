package servlet;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import util.DatabaseUtil;

@WebServlet("/shopping/register")
public class RegisterServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirm-password");

        // 验证输入
        if (username == null || email == null || password == null || confirmPassword == null ||
                username.trim().isEmpty() || email.trim().isEmpty() ||
                password.trim().isEmpty() || !password.equals(confirmPassword)) {
            request.setAttribute("error", "Invalid input or passwords do not match");
            request.getRequestDispatcher("/jsp/register.jsp").forward(request, response);
            return;
        }

        // 检查用户名是否已存在
        if (DatabaseUtil.isUsernameExists(username)) {
            request.setAttribute("error", "Username already exists. Please choose a different username.");
            request.getRequestDispatcher("/jsp/register.jsp").forward(request, response);
            return;
        }

        // 检查邮箱是否已存在
        // Remove this block
        /*if (DatabaseUtil.isEmailExists(email)) {
            request.setAttribute("error", "Email already exists");
            request.getRequestDispatcher("/jsp/register.jsp").forward(request, response);
            return;
        }*/

        // 注册用户
        if (DatabaseUtil.registerUser(username, email, password)) {
            // 注册成功，设置成功消息并重定向到登录页面
            HttpSession session = request.getSession();
            session.setAttribute("successMessage", "Registration successful! Please login with your credentials.");
            response.sendRedirect(request.getContextPath() + "/shopping/login");
        } else {
            request.setAttribute("error", "Registration failed");
            request.getRequestDispatcher("/jsp/register.jsp").forward(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/jsp/register.jsp").forward(request, response);
    }
}











