package com.ra.controller.user;

import com.ra.model.entity.CartItem;
import com.ra.model.entity.Category;
import com.ra.model.entity.Product;
import com.ra.service.CartService;
import com.ra.service.CategoryService;
import com.ra.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class HomeController {
    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private CartService cartService;
    @RequestMapping("/")
    public String home(Model model){
        List<Product> productBestSeler = productService.listProductByCategory(9);
        List<Product> productArrivals = productService.listProductByCategory(10);
        List<Product> productRated = productService.listProductByCategory(11);
        List<Product> productRequest = productService.listProductByRequest(6);

        List<Category> categoryList = categoryService.listCateDisplay();
        model.addAttribute("productBestSeler", productBestSeler);

        List<CartItem> cart = cartService.getSession();
        int tatolquantity = cart.size();
        model.addAttribute("tatolquantity",tatolquantity);
        model.addAttribute("productArrivals", productArrivals);
        model.addAttribute("productRated", productRated);
        model.addAttribute("productRequest", productRequest);
        model.addAttribute("categoryList",categoryList);
        return "users/home";
    }


//about
    @RequestMapping("/about")
    public String about(Model model){
        List<CartItem> cart = cartService.getSession();
        int tatolquantity = cart.size();
        model.addAttribute("tatolquantity",tatolquantity);
        List<Category> categoryList = categoryService.listCateDisplay();
        model.addAttribute("categoryList",categoryList);
        return "users/about";
    }

//    details
    @RequestMapping("/productdetails/{id}")
    public String details(@PathVariable("id") int id, Model model){
        List<CartItem> cart = cartService.getSession();
        int tatolquantity = cart.size();
        model.addAttribute("tatolquantity",tatolquantity);

        Product product = productService.findByIdPro(id);
        model.addAttribute("product",product);
        List<Category> categoryList = categoryService.listCateDisplay();
        model.addAttribute("categoryList",categoryList);
        return "users/productdetails";
    }

//    //phân trang
    @RequestMapping("/allproduct/{id}")
    public  String allproduct(@PathVariable("id") Integer id, Model model){
        List<Product> productlist = productService.listProduct();
        // Lấy độ dài của danh sách allproduct
        int size = productlist.size();
        int number_page = (int) Math.ceil((double) size / 8);
        // Tạo một danh sách chứa dãy số từ 1 đến number_page
        List<Integer> pageNumbers = IntStream.rangeClosed(1, number_page).boxed().collect(Collectors.toList());
        model.addAttribute("pageNumbers",pageNumbers);


        List<Product> pagination = productService.Pagination(id,8);
        model.addAttribute("pagination", pagination);


        model.addAttribute("id",id);


        List<CartItem> cart = cartService.getSession();
        int tatolquantity = cart.size();
        model.addAttribute("tatolquantity",tatolquantity);

        List<Category> categoryList = categoryService.listCateDisplay();
        model.addAttribute("categoryList",categoryList);
        return "users/allproduct";
    }




//  GorisProduct
    @RequestMapping("/gorisproduct/{id}")
    public String gorisProduct(@PathVariable("id") Integer id, Model model){
        List<Product> productbyCate = productService.listAllProductByCategory(id);
        List<Category> categoryList = categoryService.listCateDisplay();
        model.addAttribute("categoryList",categoryList);
        model.addAttribute("productbyCate",productbyCate);

        List<CartItem> cart = cartService.getSession();
        int tatolquantity = cart.size();
        model.addAttribute("tatolquantity",tatolquantity);

        return "users/gorisproduct";
    }

}
