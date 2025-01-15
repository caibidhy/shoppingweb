package dao;

import util.DatabaseUtil; // 你自己的数据库连接工具
import model.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.math.BigDecimal;

public class ProductDAO {

    public List<Product> findAll() {
        List<Product> products = new ArrayList<>();
        // 注意表名是否是 products 而不是 product
        String sql = "SELECT id, name, description, price, stock_quantity, image_url FROM products";

        try (Connection conn = DatabaseUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                // 用无参构造，然后 set 各字段
                Product p = new Product();
                p.setId(rs.getInt("id"));
                p.setName(rs.getString("name"));
                p.setDescription(rs.getString("description"));
                p.setPrice(rs.getBigDecimal("price"));  // 需和Product里匹配
                p.setStockQuantity(rs.getInt("stock_quantity"));
                p.setImageUrl(rs.getString("image_url"));
                products.add(p);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return products;
    }

    // 后面也可以加 findById、insert/update 等方法
}
