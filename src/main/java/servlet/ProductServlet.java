package servlet;

import dao.ProductDAO;  // 需要import
import model.Product;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/shopping/products")
public class ProductServlet extends HttpServlet {

    private ProductDAO productDAO = new ProductDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 1. 通过DAO获取数据库中的商品列表
        List<Product> products = productDAO.findAll();

        // 2. 放进 request attribute
        request.setAttribute("products", products);

        // 3. 转发到 JSP 显示
        request.getRequestDispatcher("/jsp/product-list.jsp").forward(request, response);
    }
}


