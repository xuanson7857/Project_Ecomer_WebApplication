package com.ra.dto.request;

import com.ra.dto.validate.CategoryConstraint;

import javax.validation.constraints.NotEmpty;

public class CategoryDTO {
    private int categoryId;

    @CategoryConstraint
    private String categoryName;
    private boolean status = true;

    public CategoryDTO() {
    }

    public CategoryDTO(int categoryId, String categoryName, boolean status) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.status = status;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
