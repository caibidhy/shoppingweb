package servlet;

import dao.CartDAO;
import dao.OrderDAO;
import dao.UserDAO;
import model.CartItemDTO;
import dao.ProductDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.List;
import java.util.logging.Logger;

@WebServlet("/shopping/cart")
public class CartServlet extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(CartServlet.class.getName());

    private CartDAO cartDAO = new CartDAO();
    private OrderDAO orderDAO = new OrderDAO();
    private UserDAO userDAO = new UserDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");

        // 未登录
        if (username == null) {
            request.setAttribute("cartMessage", "Please login first to see your cart.");
            request.getRequestDispatcher("/jsp/cart.jsp").forward(request, response);
            return;
        }

        int userId = userDAO.getUserIdByUsername(username);

        List<CartItemDTO> cartItems = cartDAO.findCartItemsByUser(userId);
        request.setAttribute("cartItems", cartItems);

        // 计算总价
        BigDecimal totalPrice = BigDecimal.ZERO;
        for (CartItemDTO item : cartItems) {
            BigDecimal line = item.getPrice().multiply(new BigDecimal(item.getQuantity()));
            totalPrice = totalPrice.add(line);
        }
        request.setAttribute("totalPrice", totalPrice);

        request.getRequestDispatcher("/jsp/cart.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");

        // 未登录检查
        if (username == null) {
            sendErrorResponse(response, "Please login first");
            return;
        }
        int userId = userDAO.getUserIdByUsername(username);

        switch (action) {
            case "add":
                handleAddToCart(request, response, userId, session);
                break;

            case "remove":
                handleRemove(request, response, userId, session);
                break;

            case "checkout":
                handleCheckout(request, response, userId);
                break;

            default:
                sendErrorResponse(response, "Unknown action");
                break;
        }
    }

    private void handleAddToCart(HttpServletRequest request, HttpServletResponse response,
                                 int userId, HttpSession session) throws IOException {
        try {
            int productId = Integer.parseInt(request.getParameter("productId"));
            ProductDAO productDAO = new ProductDAO();

            // 添加库存检查
            if (!productDAO.hasEnoughStock(productId, 1)) {
                sendErrorResponse(response, "Sorry, this item is out of stock");
                return;
            }

            int oldQty = cartDAO.findCartQuantity(userId, productId);
            if (oldQty >= 1) {
                // 增加数量时也要检查库存
                if (!productDAO.hasEnoughStock(productId, oldQty + 1)) {
                    sendErrorResponse(response, "Not enough stock available");
                    return;
                }
                cartDAO.updateCartItemQuantity(userId, productId, oldQty + 1);
            } else {
                cartDAO.addCartItem(userId, productId, 1);
            }
            session.setAttribute("cartMessage", "Product added to cart successfully!");
            sendSuccessResponse(response, "Product added to cart");
        } catch (NumberFormatException e) {
            LOGGER.warning("Invalid productId: " + e.getMessage());
            sendErrorResponse(response, "Invalid product ID");
        }
    }

    private void handleRemove(HttpServletRequest request, HttpServletResponse response,
                              int userId, HttpSession session) throws IOException {
        try {
            int productId = Integer.parseInt(request.getParameter("productId"));
            cartDAO.removeCartItem(userId, productId);
            session.setAttribute("cartMessage", "Product removed from cart successfully");
            sendSuccessResponse(response, "Removed from cart");
        } catch (NumberFormatException e) {
            LOGGER.warning("Invalid productId: " + e.getMessage());
            sendErrorResponse(response, "Invalid product ID");
        }
    }

    private void handleCheckout(HttpServletRequest request, HttpServletResponse response,
                                int userId) throws IOException {
        List<CartItemDTO> cartItems = cartDAO.findCartItemsByUser(userId);
        if (cartItems == null || cartItems.isEmpty()) {
            sendErrorResponse(response, "Cart is empty");
            return;
        }

        // 添加库存检查
        ProductDAO productDAO = new ProductDAO();
        for (CartItemDTO item : cartItems) {
            if (!productDAO.hasEnoughStock(item.getProductId(), item.getQuantity())) {
                sendErrorResponse(response, "Some items in your cart are out of stock");
                return;
            }
        }

        // 计算总价
        BigDecimal totalPrice = BigDecimal.ZERO;
        for (CartItemDTO item : cartItems) {
            BigDecimal line = item.getPrice().multiply(new BigDecimal(item.getQuantity()));
            totalPrice = totalPrice.add(line);
        }

        // 创建订单
        int orderId = orderDAO.createOrder(userId, totalPrice);
        if (orderId <= 0) {
            sendErrorResponse(response, "Failed to create order");
            return;
        }

        // 把cartItems写进order_items
        orderDAO.addOrderItems(orderId, cartItems);

        // 减库存
        orderDAO.updateStock(cartItems);

        // 清空购物车
        cartDAO.clearCartByUser(userId);

        sendSuccessResponse(response, "Checkout successful");
    }


    private void sendErrorResponse(HttpServletResponse response, String msg)
            throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.write("{\"success\":false,\"message\":\"" + msg + "\"}");
        out.flush();
    }

    private void sendSuccessResponse(HttpServletResponse response, String msg)
            throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.write("{\"success\":true,\"message\":\"" + msg + "\"}");
        out.flush();
    }
}





























