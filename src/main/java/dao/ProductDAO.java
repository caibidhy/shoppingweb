package dao;

import util.DatabaseUtil;
import model.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.math.BigDecimal;

public class ProductDAO {

    public List<Product> findAll() {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT id, name, description, price, stock_quantity, image_url FROM products";

        try (Connection conn = DatabaseUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
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

    public boolean hasEnoughStock(int productId, int requestedQuantity) {
        String sql = "SELECT stock_quantity FROM products WHERE id = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, productId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int stockQuantity = rs.getInt("stock_quantity");
                    return stockQuantity >= requestedQuantity;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void updateProduct(int oldId, int newId, String name, String description,
                              BigDecimal price, int stockQuantity, String imageUrl) {
        String sql = imageUrl != null ?
                "UPDATE products SET id=?, name=?, description=?, price=?, stock_quantity=?, image_url=? WHERE id=?" :
                "UPDATE products SET id=?, name=?, description=?, price=?, stock_quantity=? WHERE id=?";

        try (Connection conn = DatabaseUtil.getConnection()) {
            conn.setAutoCommit(false);  // 开启事务
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                int paramIndex = 1;
                ps.setInt(paramIndex++, newId);
                ps.setString(paramIndex++, name);
                ps.setString(paramIndex++, description);
                ps.setBigDecimal(paramIndex++, price);
                ps.setInt(paramIndex++, stockQuantity);

                if (imageUrl != null) {
                    ps.setString(paramIndex++, imageUrl);
                }

                ps.setInt(paramIndex, oldId);
                ps.executeUpdate();

                // 更新相关表中的引用
                updateReferences(conn, oldId, newId);

                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to update product", e);
        }
    }

    private void updateReferences(Connection conn, int oldId, int newId) throws SQLException {
        // 更新购物车引用
        String updateCartSql = "UPDATE cart_items SET product_id = ? WHERE product_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(updateCartSql)) {
            ps.setInt(1, newId);
            ps.setInt(2, oldId);
            ps.executeUpdate();
        }

        // 更新订单项引用
        String updateOrderItemsSql = "UPDATE order_items SET product_id = ? WHERE product_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(updateOrderItemsSql)) {
            ps.setInt(1, newId);
            ps.setInt(2, oldId);
            ps.executeUpdate();
        }
    }

    public void addProduct(String name, String description,
                           BigDecimal price, int stockQuantity, String imageUrl) {
        String sql = "INSERT INTO products (name, description, price, stock_quantity, image_url) " +
                "VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, name);
            ps.setString(2, description);
            ps.setBigDecimal(3, price);
            ps.setInt(4, stockQuantity);
            ps.setString(5, imageUrl);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteProduct(int id) {
        Connection conn = null;
        try {
            conn = DatabaseUtil.getConnection();
            conn.setAutoCommit(false);  // 开启事务

            // 删除购物车中的引用
            String deleteCartSql = "DELETE FROM cart_items WHERE product_id = ?";
            try (PreparedStatement ps = conn.prepareStatement(deleteCartSql)) {
                ps.setInt(1, id);
                ps.executeUpdate();
            }

            // 删除订单项中的引用
            String deleteOrderItemsSql = "DELETE FROM order_items WHERE product_id = ?";
            try (PreparedStatement ps = conn.prepareStatement(deleteOrderItemsSql)) {
                ps.setInt(1, id);
                ps.executeUpdate();
            }

            // 删除商品
            String deleteProductSql = "DELETE FROM products WHERE id = ?";
            try (PreparedStatement ps = conn.prepareStatement(deleteProductSql)) {
                ps.setInt(1, id);
                ps.executeUpdate();
            }

            conn.commit();  // 提交事务
        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback();  // 发生错误时回滚
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
            throw new RuntimeException("Failed to delete product", e);
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
