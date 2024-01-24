package com.ra.service;

import com.ra.dto.request.CategoryDTO;
import com.ra.model.entity.Category;

import java.util.List;

public interface CategoryService {
    Boolean createCate(CategoryDTO categoryDTO);

    Boolean updateCate(Category category,Integer id);

    Category findByIdCate(Integer id);

    List<Category> listCate();
    List<Category> listCateDisplay();

    void deleteCate(Integer id);
}
