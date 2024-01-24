package com.ra.controller.admin;

import com.ra.dto.request.CategoryDTO;
import com.ra.dto.request.UserRegisterDTO;
import com.ra.model.entity.Category;
import com.ra.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    @RequestMapping("/categoryadmin")
    public String listcategory(Model model){
        List<Category> categoryList = categoryService.listCate();
        model.addAttribute("categoryList",categoryList);
        return "admin/category/listcategory";
    }

    //add category
    @GetMapping("/add")
    public String add(Model model){
        CategoryDTO categoryDTO =  new CategoryDTO();
        model.addAttribute("categoryDTO",categoryDTO);
        return "admin/category/addcategory";
    }

    @PostMapping("/add")
    public String postAdd(@Valid @ModelAttribute("categoryDTO") CategoryDTO categoryDTO, BindingResult result) {
        if (!result.hasErrors()) {
            if (categoryService.createCate(categoryDTO)) {
                return "redirect:/admin/categoryadmin";
            }
        }
        return "admin/category/addcategory";
    }

    @GetMapping("/editcate/{id}")
    public String editCate(@PathVariable("id") Integer id, Model model){
        Category category = categoryService.findByIdCate(id);
        model.addAttribute("category", category);
        return "admin/category/editcategory";
    }
    @PostMapping("/update-category")
    public String updateCate(@ModelAttribute("category") Category category){
        categoryService.updateCate(category,category.getCategoryId());
        return "redirect:/admin/categoryadmin";
    }

    @RequestMapping("deletecate/{id}")
    public String deleteCate(@PathVariable("id") Integer id){
        categoryService.deleteCate(id);
        return "redirect:/admin/categoryadmin";
    }

}
