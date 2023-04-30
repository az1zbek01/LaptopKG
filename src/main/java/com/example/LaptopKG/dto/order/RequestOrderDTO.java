package com.example.LaptopKG.dto.order;

import com.example.LaptopKG.model.enums.DeliveryType;
import com.example.LaptopKG.model.enums.PaymentType;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RequestOrderDTO {
    List<Long> laptops;

    String deliveryType;

    String paymentType;
}
