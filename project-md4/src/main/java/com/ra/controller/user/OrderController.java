package com.ra.controller.user;

import com.ra.model.entity.CartItem;
import com.ra.model.entity.Category;
import com.ra.model.entity.Order;
import com.ra.service.CartService;
import com.ra.service.CategoryService;
import com.ra.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;


@Controller
public class OrderController {
    @Autowired
    private CartService cartService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private OrderService orderService;
    @GetMapping("/checkout")
    public String checkout(Model model){
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

        Order order = new Order();
        model.addAttribute("order",order);
        return "users/checkout";
    }
    @PostMapping("/checkout")
    public String postCheckout(@ModelAttribute("order") Order order, HttpSession httpSession){
        if(orderService.createOrder(order) > 0){
            httpSession.removeAttribute("cart");
            return "redirect:/";
        }
        return "redirect:/checkout";
    }
}
