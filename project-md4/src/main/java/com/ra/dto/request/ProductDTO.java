package com.ra.dto.request;

import com.ra.model.entity.Category;
import org.springframework.web.multipart.MultipartFile;

public class ProductDTO {
    private int productId;
    private String productName;
    private  String description;
    private Double price;
    private MultipartFile image;
    private int stock;
    private boolean status;
    private Category category;

    public ProductDTO() {
    }

    public ProductDTO(int productId, String productName, String description, Double price, MultipartFile image, int stock, boolean status, Category category) {
        this.productId = productId;
        this.productName = productName;
        this.description = description;
        this.price = price;
        this.image = image;
        this.stock = stock;
        this.status = status;
        this.category = category;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public MultipartFile getImage() {
        return image;
    }

    public void setImage(MultipartFile image) {
        this.image = image;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
