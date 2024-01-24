package com.ra.model.dao;

import com.ra.model.entity.Category;
import com.ra.model.entity.Order;
import com.ra.model.entity.OrderDetail;

import java.util.List;

public interface OrderDAO {
    int createOrder(Order order);
    Boolean addOrderDetail(OrderDetail orderDetail);
    Order findByIdOrder(Integer id);
    List<Order> allOrder();
}
