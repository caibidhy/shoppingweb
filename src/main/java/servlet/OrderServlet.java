package servlet;

import dao.OrderDAO;
import dao.UserDAO;
import model.Order;
import model.OrderItem; // 你可自行创建, 或返回DTO
// import other needed classes

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/shopping/orders")
public class OrderServlet extends HttpServlet {

    private OrderDAO orderDAO = new OrderDAO();
    private UserDAO userDAO = new UserDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        // 未登录跳转
        if (username == null) {
            request.setAttribute("message", "Please login first.");
            request.getRequestDispatcher("/jsp/login.jsp").forward(request, response);
            return;
        }

        int userId = userDAO.getUserIdByUsername(username);

        // 从 orders 表拿到该用户所有订单
        List<Order> orders = orderDAO.findOrdersByUser(userId);
        for (Order o : orders) {
            List<OrderItem> items = orderDAO.findOrderItems(o.getId());
            o.setOrderItems(items);
        }

        request.setAttribute("orders", orders);
        request.getRequestDispatcher("/jsp/orders.jsp").forward(request, response);
    }
}
