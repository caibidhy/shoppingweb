package dao;

import util.DatabaseUtil;  // 你的数据库连接工具类
import java.sql.*;

public class UserDAO {


    public int getUserIdByUsername(String username) {
        int userId = -1;
        String sql = "SELECT id FROM users WHERE username = ?";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    userId = rs.getInt("id");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return userId;
    }


    public boolean checkLogin(String username, String password) {
        String sql = "SELECT id FROM users WHERE username=? AND password=?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, password);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next(); // 查到表示登录成功
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}

