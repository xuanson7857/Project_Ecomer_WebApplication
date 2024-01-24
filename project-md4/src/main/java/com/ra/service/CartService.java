package com.ra.service;

import com.ra.model.entity.CartItem;

import java.util.List;

public interface CartService {
    List<CartItem> getSession();
    boolean save(CartItem item);
    boolean update(CartItem item);
    void deletecart(int id);

}
