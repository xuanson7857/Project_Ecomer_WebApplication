package com.ra.service;

import com.ra.model.entity.CartItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Service
public class CartServiceimpl implements CartService{
    @Autowired
    HttpSession httpSession;
    private List<CartItem> cartItems = new ArrayList<>();
    @Override
    public List<CartItem> getSession(){
        // kiểm tra xem có session thì lấy, không thì bằng mảng rỗng
        cartItems = (List<CartItem>) httpSession.getAttribute("cart");
        return cartItems != null ? cartItems : new ArrayList<>();
    }


    @Override
    public boolean save(CartItem item) {
        cartItems=getSession();
        CartItem cartItem = check(item.getProduct().getProductId(),cartItems);
        if(cartItem == null){
            cartItems.add(item);
        }else{
            cartItem.setQuantity(cartItem.getQuantity()+item.getQuantity());
        }
        httpSession.setAttribute("cart",cartItems);
        return true;
    }


    @Override
    public boolean update(CartItem item) {
        cartItems = getSession();
        CartItem cartItem = check(item.getProduct().getProductId(),cartItems);
        if(cartItem == null){
            cartItems.add(item);
        }else{
            cartItem.setQuantity(item.getQuantity());
        }
        httpSession.setAttribute("cart",cartItems);
        return true;
    }
    @Override
    public void deletecart(int id){
        cartItems=getSession();
        for (CartItem cartItem : cartItems) {
            if (cartItem.getProduct().getProductId() == id) {
                cartItems.remove(cartItem);
                httpSession.setAttribute("cart",cartItems);
                break;
            }
        }
    }

    public CartItem check(Integer productId, List<CartItem> cartItems) {
        for (CartItem cartItem : cartItems) {
            if (cartItem.getProduct().getProductId() == productId) {
                return cartItem;
            }
        }
        return null;
    }




}
