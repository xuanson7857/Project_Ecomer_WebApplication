package com.ra.controller.admin;

import com.ra.model.dao.OrderDAO;
import com.ra.model.entity.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminOrderController {
    @Autowired
    private OrderDAO orderDAO;

// Hiển thị danh sách order
    @RequestMapping("/order")
    public String allOrder(Model model){
        List<Order> orderList = orderDAO.allOrder();
        model.addAttribute("orderList",orderList);
        return "admin/order/order";
    }
}
