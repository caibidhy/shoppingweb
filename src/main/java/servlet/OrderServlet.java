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

        if (username == null) {
            response.sendRedirect(request.getContextPath() + "/shopping/login");
            return;
        }

        try {
            int userId = userDAO.getUserIdByUsername(username);
            List<Order> orders = orderDAO.findOrdersByUser(userId);

            if (orders != null) {
                for (Order order : orders) {
                    List<OrderItem> items = orderDAO.findOrderItems(order.getId());
                    order.setOrderItems(items);
                }
            }

            request.setAttribute("orders", orders);
            request.getRequestDispatcher("/jsp/orders.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Failed to load orders. Please try again later.");
            request.getRequestDispatcher("/jsp/orders.jsp").forward(request, response);
        }
    }
}
