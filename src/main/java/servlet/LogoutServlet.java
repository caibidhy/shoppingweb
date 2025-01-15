package servlet;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Cart;

@WebServlet("/shopping/logout")
public class LogoutServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session != null) {
            // 清空购物车
            Cart cart = (Cart) session.getAttribute("cart");
            if (cart != null) {
                cart.clear();
            }
            // 使会话失效
            session.invalidate();
        }
        response.sendRedirect(request.getContextPath() + "/shopping");
    }
}



