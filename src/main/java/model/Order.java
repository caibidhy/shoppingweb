package model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * 代表一个订单（orders 表里的一条记录）
 */
public class Order {

    private int id;                // 订单ID，对应 orders 表的主键
    private int userId;            // 下单用户ID
    private BigDecimal totalPrice; // 订单总价
    private Timestamp createTime;  // 下单时间

    // 可选：为了方便一次性获取订单的所有商品，可在这里预留一个字段
    private List<OrderItem> orderItems; // 订单包含的明细项

    // 无参构造
    public Order() {
        // 初始化 orderItems，避免为空时调用 .add() 报错
        this.orderItems = new ArrayList<>();
    }

    // 全参构造（看需要是否保留）
    public Order(int id, int userId, BigDecimal totalPrice, Timestamp createTime) {
        this.id = id;
        this.userId = userId;
        this.totalPrice = totalPrice;
        this.createTime = createTime;
        this.orderItems = new ArrayList<>();
    }

    // Getter / Setter

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }
    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }
    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }
    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }
}
