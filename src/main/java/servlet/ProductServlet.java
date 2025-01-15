package servlet;

import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Product;

@WebServlet("/shopping/products")
public class ProductServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        List<Product> products = getProductList();
        request.setAttribute("products", products);
        request.getRequestDispatcher("/jsp/product-list.jsp").forward(request, response);
    }
    
    private List<Product> getProductList() {
        return List.of(
            new Product(1, "Product 1", 19.99, "Description 1"),
            new Product(2, "Product 2", 29.99, "Description 2"),
            new Product(3, "Product 3", 39.99, "Description 3")
        );
    }
}

