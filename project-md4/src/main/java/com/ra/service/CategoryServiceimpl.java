package com.ra.service;

import com.ra.dto.request.CategoryDTO;
import com.ra.model.dao.CategoryDAO;
import com.ra.model.entity.Category;
import com.ra.model.entity.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CategoryServiceimpl implements CategoryService{
    @Autowired
    private CategoryDAO categoryDAO;
    @Override
    public Boolean createCate(CategoryDTO categoryDTO) {
        ModelMapper modelMapper = new ModelMapper();
        Category category = modelMapper.map(categoryDTO, Category.class);
        return categoryDAO.createCate(category);
    }

    @Override
    public Boolean updateCate(Category category, Integer id) {
        return categoryDAO.updateCate(category,id);
    }

    @Override
    public Category findByIdCate(Integer id) {
        return categoryDAO.findByIdCate(id);
    }

    @Override
    public List<Category> listCate() {
        return categoryDAO.listCate();
    }
    public List<Category> listCateDisplay() {
        return categoryDAO.listCateDisplay();
    }

    @Override
    public void deleteCate(Integer id) {
        categoryDAO.deleteCate(id);
    }
}
