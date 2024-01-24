package com.ra.controller.user;

import com.ra.dto.response.ResponseUserLoginDTO;
import com.ra.model.entity.CartItem;
import com.ra.model.entity.Category;
import com.ra.model.entity.Product;
import com.ra.service.CartService;
import com.ra.service.CategoryService;
import com.ra.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class CartController {
    @Autowired
    private ProductService productService;
    @Autowired
    private CartService cartService;
    @Autowired
    private CategoryService categoryService;
    @GetMapping("/cart")
    public String cart(Model model){
        List<CartItem> cart = cartService.getSession();
        model.addAttribute("cart",cart);
        int tatolquantity = cart.size();
        model.addAttribute("tatolquantity",tatolquantity);
        int tatolprice = 0;
        for (CartItem ca : cart) {
            tatolprice = (int) (tatolprice + (ca.getProduct().getPrice()*ca.getQuantity()));
        }
        model.addAttribute("tatolprice", tatolprice);
        List<Category> categoryList = categoryService.listCateDisplay();
        model.addAttribute("categoryList",categoryList);

        return "users/cart";
    }
    @PostMapping("/cart")
    public String postcart(@RequestParam("productId") int productID,
                           @RequestParam("quantity") Integer qty,
                           HttpSession httpSession){
        ResponseUserLoginDTO loginUser = (ResponseUserLoginDTO) httpSession.getAttribute("username");
        if (loginUser==null){
            return "redirect:/login";
        }
        Product product = productService.findByIdPro(productID);
        CartItem cartItem = new CartItem();
        cartItem.setProduct(product);
        cartItem.setQuantity(qty);
        cartService.save(cartItem);
        return "redirect:/cart";
    }


//    update cart

    @PostMapping("/update-cart")
    public String postUpdateCart(@RequestParam("productId") int productID, @RequestParam("quantity") Integer qty){

        Product product = productService.findByIdPro(productID);
        System.out.println(productID);
        CartItem cartItem = new CartItem();
        cartItem.setProduct(product);
        cartItem.setQuantity(qty);
        cartService.update(cartItem);
        return "redirect:/cart";
    }

    //delete
    @RequestMapping("/delete-cart/{id}")
    public String deleteCart(@PathVariable("id") int id){
        cartService.deletecart(id);
        return "redirect:/cart";
    }
}


