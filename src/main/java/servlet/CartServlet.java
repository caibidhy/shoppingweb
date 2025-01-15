package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Cart;
import model.Product;
import util.DatabaseUtil;
import java.util.logging.Logger;
import java.util.logging.Level;

@WebServlet("/shopping/cart")
public class CartServlet extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(CartServlet.class.getName());

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart == null) {
            cart = new Cart();
            session.setAttribute("cart", cart);
        }
        LOGGER.info("Cart items in doGet: " + cart.getItems());
        request.getRequestDispatcher("/jsp/cart.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String action = request.getParameter("action");
            LOGGER.info("Received action: " + action);
            LOGGER.info("Product ID: " + request.getParameter("productId"));
            if (action == null) {
                sendErrorResponse(response, "Invalid action");
                return;
            }

            HttpSession session = request.getSession();
            Cart cart = (Cart) session.getAttribute("cart");

            if (cart == null) {
                cart = new Cart();
                session.setAttribute("cart", cart);
            }

            LOGGER.info("Cart items before action: " + cart.getItems());

            String username = (String) session.getAttribute("username");
            String responseMessage = "";
            boolean success = false;

            switch (action) {
                case "add":
                    try {
                        int productId = Integer.parseInt(request.getParameter("productId"));
                        cart.addItem(productId, 1);

                        if (username != null) {
                            DatabaseUtil.saveCartItems(username, cart.getItems());
                        }

                        success = true;
                        responseMessage = "Product added to cart successfully!";
                        session.setAttribute("cartMessage", responseMessage);
                        response.sendRedirect(request.getContextPath() + "/shopping/cart");
                        return;
                    } catch (NumberFormatException e) {
                        responseMessage = "Invalid product ID";
                        sendErrorResponse(response, responseMessage);
                        return;
                    }

                case "remove":
                    try {
                        int productId = Integer.parseInt(request.getParameter("productId"));
                        LOGGER.info("Removing product: " + productId);
                        cart.removeItem(productId);

                        if (username != null) {
                            DatabaseUtil.saveCartItems(username, cart.getItems());
                        }

                        success = true;
                        responseMessage = "Product removed from cart successfully";
                        LOGGER.info("Cart items after removal: " + cart.getItems());
                        sendSuccessResponse(response, responseMessage);
                        return;
                    } catch (NumberFormatException e) {
                        LOGGER.log(Level.SEVERE, "Invalid product ID", e);
                        responseMessage = "Invalid product ID";
                        sendErrorResponse(response, responseMessage);
                        return;
                    } catch (Exception e) {
                        LOGGER.log(Level.SEVERE, "Failed to remove product from cart", e);
                        responseMessage = "Failed to remove product from cart";
                        sendErrorResponse(response, responseMessage);
                        return;
                    }

                case "checkout":
                    if (username == null) {
                        responseMessage = "Please login first";
                        sendErrorResponse(response, responseMessage);
                        return;
                    } else {
                        success = true;
                        responseMessage = "Checkout successful";
                        session.setAttribute("cartMessage", responseMessage);
                        response.sendRedirect(request.getContextPath() + "/shopping/cart");
                        return;
                    }

                default:
                    sendErrorResponse(response, "Invalid action");
                    return;
            }

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "An error occurred", e);
            sendErrorResponse(response, "An error occurred");
        }
    }

    private void sendErrorResponse(HttpServletResponse response, String message)
            throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_OK);
        PrintWriter out = response.getWriter();
        out.write("{\"success\":false,\"message\":\"" + message + "\"}");
        out.flush();
    }

    private void sendSuccessResponse(HttpServletResponse response, String message)
            throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_OK);
        PrintWriter out = response.getWriter();
        out.write("{\"success\":true,\"message\":\"" + message + "\"}");
        out.flush();
    }
}


























