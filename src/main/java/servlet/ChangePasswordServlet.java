package servlet;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import util.DatabaseUtil;

@WebServlet("/shopping/changePassword")
public class ChangePasswordServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/jsp/change-password.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String oldPassword = request.getParameter("oldPassword");
        String newPassword = request.getParameter("newPassword");
        String confirmPassword = request.getParameter("confirmPassword");


        // 添加管理员检查
        if ("admin".equals(username)) {
            request.setAttribute("error", "Admin password cannot be changed");
            request.getRequestDispatcher("/jsp/change-password.jsp").forward(request, response);
            return;
        }

        // 验证输入
        if (username == null || oldPassword == null || newPassword == null || confirmPassword == null
                || username.trim().isEmpty() || oldPassword.trim().isEmpty()
                || newPassword.trim().isEmpty() || confirmPassword.trim().isEmpty()) {
            request.setAttribute("error", "All fields are required");
            request.getRequestDispatcher("/jsp/change-password.jsp").forward(request, response);
            return;
        }

        // 验证新密码和确认密码是否匹配
        if (!newPassword.equals(confirmPassword)) {
            request.setAttribute("error", "New passwords do not match");
            request.getRequestDispatcher("/jsp/change-password.jsp").forward(request, response);
            return;
        }

        // 验证新密码不能与旧密码相同
        if (newPassword.equals(oldPassword)) {
            request.setAttribute("error", "New password must be different from old password");
            request.getRequestDispatcher("/jsp/change-password.jsp").forward(request, response);
            return;
        }

        // 验证旧密码是否正确
        if (!DatabaseUtil.validateUser(username, oldPassword)) {
            request.setAttribute("error", "Invalid username or old password");
            request.getRequestDispatcher("/jsp/change-password.jsp").forward(request, response);
            return;
        }

        // 更新密码
        if (DatabaseUtil.updatePassword(username, newPassword)) {
            request.setAttribute("success", "Password changed successfully!");
        } else {
            request.setAttribute("error", "Failed to change password");
        }

        request.getRequestDispatcher("/jsp/change-password.jsp").forward(request, response);
    }
}