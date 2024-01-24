package com.ra.service;

import com.ra.dto.response.ResponseUserLoginDTO;
import com.ra.model.dao.OrderDAO;
import com.ra.model.entity.CartItem;
import com.ra.model.entity.Order;
import com.ra.model.entity.OrderDetail;
import com.ra.model.entity.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.List;

@Service
public class OrderServiceimpl implements OrderService{
    @Autowired
    private OrderDAO orderDAO;
    @Autowired
    HttpSession httpSession;
    @Override
    public int createOrder(Order order) {

        List<CartItem> cartItemList = (List<CartItem>) httpSession.getAttribute("cart");
        ResponseUserLoginDTO responseUserLoginDTO = (ResponseUserLoginDTO) httpSession.getAttribute("username");
        ModelMapper modelMapper = new ModelMapper();
        User user = modelMapper.map(responseUserLoginDTO, User.class);
        if(cartItemList != null){
            if(user != null){
                double totalAmount = 0;
                double totalPrice = 0;
                for (CartItem cartItem: cartItemList) {
                    totalAmount = totalAmount + cartItem.getQuantity();
                    totalPrice = totalPrice + (cartItem.getProduct().getPrice()*cartItem.getQuantity());
                }
                order.setTotalAmount(totalAmount);
                order.setUser(user);
                order.setTotalPrice(totalPrice);

                int idOrder = orderDAO.createOrder(order);

                Order order1 = orderDAO.findByIdOrder(idOrder);
                if( order1 != null){
                    for (CartItem cartItem : cartItemList) {
                        OrderDetail orderDetail = new OrderDetail();
                        orderDetail.setOrder(order1);
                        orderDetail.setProduct(cartItem.getProduct());
                        orderDetail.setQuantity(cartItem.getQuantity());
                        orderDetail.setPrice(cartItem.getProduct().getPrice());
                        orderDAO.addOrderDetail(orderDetail);
                    }
                }
                return idOrder;
            }
        }
        return orderDAO.createOrder(order );
    }




    @Override
    public List<Order> allOrder() {
        return orderDAO.allOrder();
    }
}
