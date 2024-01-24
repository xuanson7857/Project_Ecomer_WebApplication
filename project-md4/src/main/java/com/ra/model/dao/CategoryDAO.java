package com.ra.model.dao;

import com.ra.dto.request.UserLoginDTO;
import com.ra.model.entity.Category;
import com.ra.model.entity.User;

import java.util.List;

public interface CategoryDAO {
    Boolean createCate(Category category);

    Boolean updateCate(Category category,Integer id);

    Category findByIdCate(Integer id);

    List<Category> listCate();
    List<Category> listCateDisplay();

    List<String> uniqueCate(String string);

    void deleteCate(Integer id);

}
