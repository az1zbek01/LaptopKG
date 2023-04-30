package com.example.LaptopKG.dto.order;

import com.example.LaptopKG.dto.laptop.ResponseLaptopDTO;
import com.example.LaptopKG.dto.user.GetUserDto;
import com.example.LaptopKG.model.Laptop;
import com.example.LaptopKG.model.Order;
import com.example.LaptopKG.model.enums.DeliveryType;
import com.example.LaptopKG.model.enums.PaymentType;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.LaptopKG.dto.laptop.ResponseLaptopDTO.toResponseLaptopDTO;
import static com.example.LaptopKG.dto.user.GetUserDto.getUserDto;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ResponseOrderDTO {
    long id;

    List<ResponseLaptopDTO> laptops;

    String deliveryType;

    String paymentType;

    String orderStatus;

    LocalDateTime createdAt;

    LocalDateTime updatedAt;

    GetUserDto user;

    public static ResponseOrderDTO toResponseOrderDTO(Order order){
        return ResponseOrderDTO.builder()
                .id(order.getId())
                .laptops(toResponseLaptopDTO(order.getLaptops()))
                .deliveryType(order.getDeliveryType().getDeliveryType())
                .paymentType(order.getPaymentType().getPaymentType())
                .orderStatus(order.getOrderStatus().getOrderStatus())
                .createdAt(order.getCreatedAt())
                .updatedAt(order.getUpdatedAt())
                .user(getUserDto(order.getUser()))
                .build();
    }

    public static List<ResponseOrderDTO> toResponseOrderDTO(List<Order> orders){
        return orders.stream().map(ResponseOrderDTO::toResponseOrderDTO).collect(Collectors.toList());
    }
}
