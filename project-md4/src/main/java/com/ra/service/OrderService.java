package com.ra.service;

import com.ra.model.entity.Order;
import com.ra.model.entity.OrderDetail;

import java.util.List;

public interface OrderService {
    int createOrder(Order order);
    List<Order> allOrder();
}
