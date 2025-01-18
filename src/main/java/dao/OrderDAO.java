package dao;

import model.CartItemDTO;
import model.Order;
import model.OrderItem;
import util.DatabaseUtil;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO {

    /**
     * 在 orders 表插入一条记录
     * @param userId  用户ID
     * @param totalPrice 总价
     * @return 新订单的 orderId
     */
    public int createOrder(int userId, BigDecimal totalPrice) {
        int orderId = -1;
        String sql = "INSERT INTO orders (user_id, total_price, create_time) VALUES (?, ?, NOW())";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, userId);
            ps.setBigDecimal(2, totalPrice);
            ps.executeUpdate();

            // 获取自增主键
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    orderId = rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orderId;
    }

    /**
     * 在 order_items 表插入多条记录
     * @param orderId 新创建的订单ID
     * @param items   购物车中商品(含 productId, quantity, price)
     */
    public void addOrderItems(int orderId, List<CartItemDTO> items) {
        String sql = "INSERT INTO order_items (order_id, product_id, quantity, unit_price) VALUES (?,?,?,?)";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            for (CartItemDTO item : items) {
                ps.setInt(1, orderId);
                ps.setInt(2, item.getProductId());
                ps.setInt(3, item.getQuantity());
                ps.setBigDecimal(4, item.getPrice()); // 如果CartItemDTO里的price是BigDecimal
                ps.addBatch();
            }
            ps.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 减少库存
     */
    public void updateStock(List<CartItemDTO> items) {
        String sql = "UPDATE products SET stock_quantity = stock_quantity - ? WHERE id = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            for (CartItemDTO item : items) {
                ps.setInt(1, item.getQuantity());
                ps.setInt(2, item.getProductId());
                ps.addBatch();
            }
            ps.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 查询某个用户的所有订单
     * @param userId 用户ID
     * @return 订单列表
     */
    public List<Order> findOrdersByUser(int userId) {
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT id, user_id, total_price, create_time FROM orders WHERE user_id=? ORDER BY create_time DESC";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Order o = new Order();
                    o.setId(rs.getInt("id"));
                    o.setUserId(rs.getInt("user_id"));
                    o.setTotalPrice(rs.getBigDecimal("total_price"));
                    o.setCreateTime(rs.getTimestamp("create_time"));
                    // orderItems 在外部再查询
                    orders.add(o);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }

    /**
     * 查询某个订单的订单项
     * @param orderId 订单ID
     * @return 该订单下的全部 OrderItem
     */
    public List<OrderItem> findOrderItems(int orderId) {
        List<OrderItem> items = new ArrayList<>();
        String sql = "SELECT id, order_id, product_id, quantity, unit_price FROM order_items WHERE order_id=?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, orderId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    OrderItem item = new OrderItem();
                    item.setId(rs.getInt("id"));
                    item.setOrderId(rs.getInt("order_id"));
                    item.setProductId(rs.getInt("product_id"));
                    item.setQuantity(rs.getInt("quantity"));
                    item.setUnitPrice(rs.getBigDecimal("unit_price"));
                    items.add(item);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return items;
    }
}

