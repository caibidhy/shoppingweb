package servlet;

import dao.CartDAO;
import dao.UserDAO;         // 新增：记得导入你的 UserDAO
import model.CartItemDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.math.BigDecimal;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

@WebServlet("/shopping/cart")
public class CartServlet extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(CartServlet.class.getName());
    private CartDAO cartDAO = new CartDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");

        if (username == null) {
            request.setAttribute("cartMessage", "Please login first to see your cart.");
            request.getRequestDispatcher("/jsp/cart.jsp").forward(request, response);
            return;
        }

        int userId = getUserIdByUsername(username);

        // 查到所有购物车项
        List<CartItemDTO> cartItems = cartDAO.findCartItemsByUser(userId);
        request.setAttribute("cartItems", cartItems);

        // 这里计算总价
        BigDecimal totalPrice = BigDecimal.ZERO;
        for (CartItemDTO item : cartItems) {
            // item.getPrice() * item.getQuantity()
            BigDecimal lineTotal = item.getPrice().multiply(new BigDecimal(item.getQuantity()));
            totalPrice = totalPrice.add(lineTotal);
        }
        // 存到 request 让 JSP 使用
        request.setAttribute("totalPrice", totalPrice);

        request.getRequestDispatcher("/jsp/cart.jsp").forward(request, response);
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");

        // 若未登录，则在cart.jsp提示
        if (username == null) {
            request.setAttribute("cartMessage", "Please login first!");
            request.getRequestDispatcher("/jsp/cart.jsp").forward(request, response);
            return;
        }

        int userId = getUserIdByUsername(username);

        if ("add".equals(action)) {
            // 添加商品到购物车
            try {
                int productId = Integer.parseInt(request.getParameter("productId"));
                int oldQty = cartDAO.findCartQuantity(userId, productId);
                if (oldQty >= 1) {
                    cartDAO.updateCartItemQuantity(userId, productId, oldQty + 1);
                } else {
                    cartDAO.addCartItem(userId, productId, 1);
                }
                session.setAttribute("cartMessage", "Product added to cart successfully!");
            } catch (NumberFormatException e) {
                LOGGER.warning("Invalid productId: " + e.getMessage());
                session.setAttribute("cartMessage", "Invalid product ID!");
            }
            response.sendRedirect(request.getContextPath() + "/shopping/cart");
            return;

        } else if ("remove".equals(action)) {
            // 移除购物车商品
            try {
                int productId = Integer.parseInt(request.getParameter("productId"));
                cartDAO.removeCartItem(userId, productId);
                session.setAttribute("cartMessage", "Product removed from cart successfully");
            } catch (NumberFormatException e) {
                LOGGER.warning("Invalid productId: " + e.getMessage());
                session.setAttribute("cartMessage", "Invalid product ID!");
            }
            response.sendRedirect(request.getContextPath() + "/shopping/cart");
            return;

        } else if ("update".equals(action)) {
            // 可自行扩展：更新商品数量等
            response.sendRedirect(request.getContextPath() + "/shopping/cart");
            return;

        } else {
            // 未知的action
            session.setAttribute("cartMessage", "Unknown action!");
            response.sendRedirect(request.getContextPath() + "/shopping/cart");
        }
    }


    private int getUserIdByUsername(String username) {
        UserDAO userDAO = new UserDAO();
        return userDAO.getUserIdByUsername(username);
    }
}




























