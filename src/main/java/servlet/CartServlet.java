package servlet;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Cart;
import model.Product;

@WebServlet("/shopping/cart")
public class CartServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart == null) {
            cart = new Cart(0);
            session.setAttribute("cart", cart);
        }
        request.getRequestDispatcher("/jsp/cart.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart == null) {
            cart = new Cart(0);
            session.setAttribute("cart", cart);
        }

        String action = request.getParameter("action");
        int productId = Integer.parseInt(request.getParameter("productId"));

        if ("add".equals(action)) {
            Product product = getProductById(productId);
            if (product != null) {
                cart.addItem(product);
            }
        } else if ("remove".equals(action)) {
            cart.removeItem(productId);
        }

        response.sendRedirect(request.getContextPath() + "/shopping/cart");
    }

    private Product getProductById(int id) {
        return new Product(id, "Product " + id, 19.99, "Description " + id);
    }
}
