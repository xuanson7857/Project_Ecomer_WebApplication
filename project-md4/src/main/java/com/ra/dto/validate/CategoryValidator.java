package com.ra.dto.validate;

import com.ra.model.dao.CategoryDAO;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class CategoryValidator implements ConstraintValidator<CategoryConstraint, String>{
    @Autowired
    private CategoryDAO categoryDAO;
    @Override
    public void initialize(CategoryConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (!value.isEmpty()) {
                List<String> list = categoryDAO.uniqueCate("categoryName");
                if (list.contains(value)) {
                    String m = "Category đã tồn tại";
                    context.buildConstraintViolationWithTemplate(m).addConstraintViolation();
                    return false;
                } else {
                    return true;
                }
        }
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate("Tên được để trống").addConstraintViolation();
        return false;
    }
}
