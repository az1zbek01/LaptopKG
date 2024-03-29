package com.example.LaptopKG.model;

import com.example.LaptopKG.model.baseEntity.BaseEntity;
import com.example.LaptopKG.model.enums.DeliveryType;
import com.example.LaptopKG.model.enums.OrderStatus;
import com.example.LaptopKG.model.enums.PaymentType;
import com.example.LaptopKG.model.enums.Status;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;


@Entity
@Table(name = "_order")
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Order extends BaseEntity {
    @ManyToMany
    @JoinTable(name = "laptop_order_item",
            joinColumns = @JoinColumn(name="laptop_id"),
            inverseJoinColumns = @JoinColumn(name = "order_id"))
    List<Laptop> laptops;

    @Enumerated(EnumType.STRING)
    DeliveryType deliveryType;

    @Enumerated(EnumType.STRING)
    PaymentType paymentType;

    @Enumerated(EnumType.STRING)
    OrderStatus orderStatus;

    @Enumerated(EnumType.STRING)
    Status status;

    @CreationTimestamp
    LocalDateTime createdAt;

    @UpdateTimestamp
    LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;

}
