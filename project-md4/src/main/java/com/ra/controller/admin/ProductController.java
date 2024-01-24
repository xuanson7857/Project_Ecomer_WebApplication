package com.ra.controller.admin;

import com.ra.dto.request.CategoryDTO;
import com.ra.dto.request.ProductDTO;
import com.ra.model.entity.Category;
import com.ra.model.entity.Product;
import com.ra.service.CategoryService;
import com.ra.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Controller
@PropertySource("classpath:config.properties")
@RequestMapping("/admin")
public class ProductController {
    @Value("${path}")
    private String pathUpload;

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ProductService productService;



    @RequestMapping("/productadmin")
    public String listproduct(Model model){
        List<Product> productList = productService.listProduct();
        model.addAttribute("productList",productList);
        return "admin/product/listproduct";
    }



    @GetMapping("/addproduct")
    public String addPro(Model model){
        ProductDTO  productDTO = new ProductDTO();
        List<Category> categoryList = categoryService.listCateDisplay();
        model.addAttribute("productDTO",productDTO);
        model.addAttribute("categoryList",categoryList);
        return "admin/product/addproduct";
    }

    @PostMapping("/addproduct")
    public String postAdd(@ModelAttribute("productDTO") ProductDTO productDTO,
                          @RequestParam("image") MultipartFile multipartFile){

        String fileName = multipartFile.getOriginalFilename();
        File file = new File(pathUpload+fileName);
        // kiểm tra thu muc có chưa chua có thì tạo
        if(!file.exists()){
            file.mkdir();
        }

        try {
            multipartFile.transferTo(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if(productService.createProduct(productDTO)){
            return "redirect:/admin/productadmin";
        }
        return "admin/product/addproduct";
    }


    @GetMapping("/updateproduct/{id}")
    public String editProduct(@PathVariable("id") Integer id, Model model){
        Product product = productService.findByIdPro(id);
        List<Category> categoryList = categoryService.listCateDisplay();
        model.addAttribute("product", product);
        model.addAttribute("categoryList",categoryList);
        return "admin/product/editproduct";
    }


    @PostMapping("/updateproduct/{id}")
    public String updateProduct(
            @PathVariable("id") int id,
            @ModelAttribute("product") Product product,
            @RequestParam(required = false, name ="imagess") MultipartFile multipartFile){

        // Lấy thông tin sản phẩm hiện tại từ cơ sở dữ liệu
        Product existingProduct = productService.findByIdPro(id);
        System.out.println(existingProduct);
        // Cập nhật chỉ các trường được nhập từ form
        if (product.getProductName() != null) {
            existingProduct.setProductName(product.getProductName());
        }

        if (product.getDescription() != null) {
            existingProduct.setDescription(product.getDescription());
        }

        if (product.getPrice() != null) {
            existingProduct.setPrice(product.getPrice());
        }
        existingProduct.setStock(product.getStock());
        existingProduct.setStatus(product.isStatus());
        existingProduct.setCategory(product.getCategory());


        // Kiểm tra và cập nhật ảnh nếu được nhập
        if (multipartFile != null && !multipartFile.isEmpty()) {
            String fileName = multipartFile.getOriginalFilename();
            File file = new File(pathUpload+fileName);
            // kiểm tra thu muc có chưa chua có thì tạo
            if(!file.exists()){
                file.mkdir();
            }

            try {
                multipartFile.transferTo(file);
                existingProduct.setImage(fileName);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }

        productService.updateProduct(existingProduct,id);
        return "redirect:/admin/productadmin";
    }



    @RequestMapping("deleteproduct/{id}")
    public String deleteCate(@PathVariable("id") Integer id){
        productService.deleteProduct(id);
        return "redirect:/admin/productadmin";
    }
}
