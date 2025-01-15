package dao;

import model.CartItemDTO;
import util.DatabaseUtil;

import java.sql.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class CartDAO {

    /**
     * 查询某个用户(userId)的全部购物车项，并关联查询products表，
     * 以获取product的name, image_url, price等信息。
     */
    public List<CartItemDTO> findCartItemsByUser(int userId) {
        String sql = "SELECT c.product_id, c.quantity, "
                + "       p.name, p.image_url, p.price "
                + "  FROM cart_items c "
                + "  JOIN products p ON c.product_id = p.id "
                + " WHERE c.user_id = ?";

        List<CartItemDTO> result = new ArrayList<>();
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    CartItemDTO item = new CartItemDTO();
                    item.setProductId(rs.getInt("product_id"));
                    item.setQuantity(rs.getInt("quantity"));
                    item.setName(rs.getString("name"));
                    item.setImageUrl(rs.getString("image_url"));
                    item.setPrice(rs.getBigDecimal("price"));

                    result.add(item);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 查找某个用户在cart_items中是否已经有某个productId的记录。
     * 若存在，就返回其quantity，若不存在返回 -1（或其他标记）。
     */
    public int findCartQuantity(int userId, int productId) {
        String sql = "SELECT quantity FROM cart_items WHERE user_id = ? AND product_id = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ps.setInt(2, productId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("quantity");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;  // -1表示不存在
    }

    /**
     * 新增一条购物车记录
     */
    public void addCartItem(int userId, int productId, int quantity) {
        String sql = "INSERT INTO cart_items(user_id, product_id, quantity) VALUES(?,?,?)";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ps.setInt(2, productId);
            ps.setInt(3, quantity);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 更新购物车中某个商品的数量
     */
    public void updateCartItemQuantity(int userId, int productId, int newQuantity) {
        String sql = "UPDATE cart_items SET quantity=? WHERE user_id=? AND product_id=?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, newQuantity);
            ps.setInt(2, userId);
            ps.setInt(3, productId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除某个用户的某个商品
     */
    public void removeCartItem(int userId, int productId) {
        String sql = "DELETE FROM cart_items WHERE user_id=? AND product_id=?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ps.setInt(2, productId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
