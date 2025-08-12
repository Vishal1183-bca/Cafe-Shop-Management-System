package org.project.cafeshopmanagmentsystem;

import java.util.Date;

public class ProductData
{
    private Integer id;
    private String productId;
    private String productName;
    private String stock;
    private Double price;
    private String status;
    private String type;
    private String image;
    private Date datel;
    public ProductData(Integer id, String productId, String productName, String stock, Double price, String status, String type, String image, Date datel) {
        this.id = id;
        this.productId = productId;
        this.productName = productName;
        this.stock = stock;
        this.price = price;
        this.status = status;
        this.type = type;
        this.image = image;
        this.datel = datel;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Date getDatel() {
        return datel;
    }

    public void setDatel(Date datel) {
        this.datel = datel;
    }
}
