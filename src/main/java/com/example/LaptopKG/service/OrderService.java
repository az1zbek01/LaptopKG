package com.example.LaptopKG.service;

import com.example.LaptopKG.dto.order.RequestOrderDTO;
import com.example.LaptopKG.dto.order.ResponseOrderDTO;
import com.example.LaptopKG.model.User;
import com.example.LaptopKG.model.enums.OrderStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface OrderService {
    List<ResponseOrderDTO> getAllOrdersOfUser(User user);
    List<ResponseOrderDTO> getAllOrders();
    List<ResponseOrderDTO> getAllDeletedOrders();
    ResponseOrderDTO addOrder(RequestOrderDTO orderDTO, User user);
    ResponseEntity<String> changeOrderStatus(Long id, OrderStatus orderStatus, String message);
    ResponseEntity<String> cancelOrder(Long id, User user);
    ResponseEntity<String> deleteOrder(Long id, User user);

}
