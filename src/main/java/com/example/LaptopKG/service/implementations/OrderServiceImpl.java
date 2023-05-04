package com.example.LaptopKG.service.implementations;

import com.example.LaptopKG.dto.order.RequestOrderDTO;
import com.example.LaptopKG.dto.order.ResponseOrderDTO;
import com.example.LaptopKG.exception.LaptopNotFoundException;
import com.example.LaptopKG.exception.NotFoundException;
import com.example.LaptopKG.model.*;
import com.example.LaptopKG.model.enums.DeliveryType;
import com.example.LaptopKG.model.enums.OrderStatus;
import com.example.LaptopKG.model.enums.PaymentType;
import com.example.LaptopKG.model.enums.Status;
import com.example.LaptopKG.repository.LaptopRepository;
import com.example.LaptopKG.repository.NotificationRepository;
import com.example.LaptopKG.repository.OrderRepository;
import com.example.LaptopKG.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.example.LaptopKG.dto.order.ResponseOrderDTO.toResponseOrderDTO;

@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final LaptopRepository laptopRepository;
    private final NotificationRepository notificationRepository;

    // Getting all orders of current user
    public List<ResponseOrderDTO> getAllOrdersOfUser(User user){
        return toResponseOrderDTO(orderRepository.findAllByUser(user)
                .stream().filter(order -> order.getStatus() == Status.ACTIVE)
                .collect(Collectors.toList())
        );
    }

    // Getting all orders in DB
    public List<ResponseOrderDTO> getAllOrders(){
        return toResponseOrderDTO(orderRepository.findAll()
                .stream().filter(order -> order.getStatus() == Status.ACTIVE)
                .collect(Collectors.toList())
        );
    }

    // Getting all deleted orders in DB
    public List<ResponseOrderDTO> getAllDeletedOrders(){
        return toResponseOrderDTO(orderRepository.findAll()
                .stream().filter(order -> order.getStatus() == Status.DELETED)
                .collect(Collectors.toList())
        );
    }

    // Order adding
    public ResponseOrderDTO addOrder(RequestOrderDTO orderDTO, User user){
        // Get set of laptops by ids
        Set<Laptop> laptopsSet = new HashSet<>();
        for(long id:orderDTO.getLaptops()) {
            laptopsSet.add(laptopRepository.findById(id)
                    .orElseThrow(() -> new LaptopNotFoundException("Ноутбук с айди " + id + " не найден"))
            );
        }

        // Mapping from DTO to Entity
        Order order = Order.builder()
                .laptops(new ArrayList<>(laptopsSet))
                .deliveryType(DeliveryType.of(orderDTO.getDeliveryType()))
                .paymentType(PaymentType.of(orderDTO.getPaymentType()))
                .orderStatus(OrderStatus.NEW)
                .status(Status.ACTIVE)
                .user(user)
                .build();

        // Save order in DB
        orderRepository.save(order);

        // Send notification to user about new order
        notificationRepository.save(
                Notification.builder()
                        .header("Оформлен новый заказ!")
                        .message("Вы оформили новый заказ на ноутбуки, подробнее можете узнать в истории заказов.")
                        .status(Status.ACTIVE)
                        .user(user)
                        .build()
        );

        // Return order mapping it to DTO
        return toResponseOrderDTO(order);
    }

    // Order status changing
    public ResponseEntity<String> changeOrderStatus(Long id, OrderStatus orderStatus, String message){
        // Get order by id or throw exception if it doesn't exist
        Order order = orderRepository.findById(id)
                .filter(o -> o.getStatus() == Status.ACTIVE)
                .orElseThrow(
                        () -> new NotFoundException("Заказ с айди " + id + " не найден")
        );

        // Change order status
        order.setOrderStatus(orderStatus);
        // Save updated order
        orderRepository.save(order);

        // If administrator wrote a message, send notification to user
        if(message != null){
            notificationRepository.save(
              Notification.builder()
                      .header("Обновлён статус заказа!")
                      .message(message)
                      .user(order.getUser())
                      .status(Status.ACTIVE)
                      .build()
            );
        }

        // return status 200 and message
        return ResponseEntity.ok("Заказ обновлён");
    }


    // Order canceling by user
    public ResponseEntity<String> cancelOrder(Long id, User user){
        // Get order if exists
        Order order = orderRepository.findById(id)
                .filter(o -> o.getStatus() == Status.ACTIVE)
                .filter(o -> o.getUser().equals(user))
                .orElseThrow(
                        () -> new NotFoundException("Заказ с айди " + id + " не найден")
                );

        // Make order canceled and save it
        order.setOrderStatus(OrderStatus.CANCELED);
        orderRepository.save(order);

        return ResponseEntity.ok("Заказ отменён");
    }

    // Order deleting
    public ResponseEntity<String> deleteOrder(Long id, User user){
        // Get order if exists
        Order order = orderRepository.findById(id)
                .filter(o -> o.getStatus() == Status.ACTIVE)
                .filter(o -> o.getUser().equals(user))
                .orElseThrow(
                        () -> new NotFoundException("Заказ с айди " + id + " не найден")
                );

        // Make order deleted and save it
        order.setStatus(Status.DELETED);
        orderRepository.save(order);

        return ResponseEntity.ok("Заказ удалён");
    }

}
