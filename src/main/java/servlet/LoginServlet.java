package servlet;

import java.io.IOException;
import java.util.Map;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Cart;
import util.DatabaseUtil;

@WebServlet("/shopping/login")
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if (DatabaseUtil.validateUser(username, password)) {
            HttpSession session = request.getSession();
            session.setAttribute("username", username);

            // 添加管理员检查
            if ("admin".equals(username) && "admin".equals(password)) {
                session.setAttribute("role", "admin");
                response.sendRedirect(request.getContextPath() + "/shopping/admin/products");
                return;
            }

            // 处理普通用户的购物车同步
            Cart sessionCart = (Cart) session.getAttribute("cart");
            Map<Integer, Integer> dbCart = DatabaseUtil.getCartItems(username);

            if (sessionCart == null) {
                sessionCart = new Cart();
            }

            if (!dbCart.isEmpty()) {
                // 如果数据库中有购物车数据，使用数据库中的数据
                sessionCart.clear();
                for (Map.Entry<Integer, Integer> entry : dbCart.entrySet()) {
                    sessionCart.addItem(entry.getKey(), entry.getValue());
                }
            } else if (!sessionCart.getItems().isEmpty()) {
                // 如果数据库中没有数据，但会话中有，保存到数据库
                DatabaseUtil.saveCartItems(username, sessionCart.getItems());
            }

            session.setAttribute("cart", sessionCart);
            response.sendRedirect(request.getContextPath() + "/shopping");
        } else {
            request.setAttribute("error", "Invalid username or password");
            request.getRequestDispatcher("/jsp/login.jsp").forward(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/jsp/login.jsp").forward(request, response);
    }
}





