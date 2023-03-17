package com.example.LaptopKG.model;

import com.example.LaptopKG.model.baseEntity.BaseEntity;
import com.example.LaptopKG.model.enums.DeliveryType;
import com.example.LaptopKG.model.enums.OrderStatus;
import com.example.LaptopKG.model.enums.PaymentType;
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
@ToString
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Order extends BaseEntity {

    @CreationTimestamp
    LocalDateTime created;

    @UpdateTimestamp
    LocalDateTime updated;

    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;

    @ManyToMany
    @JoinTable(name = "laptop_order_item",
            joinColumns = @JoinColumn(name="laptop_id"),
            inverseJoinColumns = @JoinColumn(name = "order_id"))
    List<Laptop> laptops;

    @Enumerated(EnumType.STRING)
    @Column(name = "delivery_type")
    DeliveryType deliveryType;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_type")
    PaymentType paymentType;

    @Enumerated(EnumType.STRING)
    OrderStatus status;

}
