package model;

import java.math.BigDecimal;

public class Product {
    private int id;
    private String name;
    private String description;
    private BigDecimal price;    // 更精确地处理金额
    private int stockQuantity;
    private String imageUrl;

    // 无参构造（给 DAO 或框架使用）
    public Product() {
    }

    // 如果需要，也可添加一个全参构造
    public Product(int id, String name, BigDecimal price, String description,
                   int stockQuantity, String imageUrl) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.stockQuantity = stockQuantity;
        this.imageUrl = imageUrl;
    }

    // Getter / Setter —— 记得和表字段对应
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }

    public int getStockQuantity() { return stockQuantity; }
    public void setStockQuantity(int stockQuantity) { this.stockQuantity = stockQuantity; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
}
