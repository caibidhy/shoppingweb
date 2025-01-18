package model;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * 表示订单中的一条商品信息。
 * 若你的实际数据表中并没有“id”、“createTime”等列，
 * 可以根据需要增减或修改字段。
 */
public class OrderItem {

    private int id;            // 主键ID，可在 order_items 表里设置 AUTO_INCREMENT
    private int orderId;       // 所属订单ID
    private int productId;     // 购买的产品ID
    private int quantity;      // 购买数量
    private BigDecimal unitPrice;  // 商品单价（下单时的价格）

    // (可选) 如果你的项目需要记录这个商品订单项的创建时间、或商品名称快照等，可以加字段：
    // private Timestamp createTime;
    // private String productName;

    // 无参构造
    public OrderItem() {
    }

    // 全参构造（根据需要选择是否使用）
    public OrderItem(int id, int orderId, int productId, int quantity, BigDecimal unitPrice) {
        this.id = id;
        this.orderId = orderId;
        this.productId = productId;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    // Getter / Setter
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public int getOrderId() {
        return orderId;
    }
    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getProductId() {
        return productId;
    }
    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }
    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

}
