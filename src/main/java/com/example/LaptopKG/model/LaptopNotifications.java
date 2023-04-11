package com.example.LaptopKG.model;


import com.example.LaptopKG.model.baseEntity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Entity
@Table(name = "laptop_notifications")
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LaptopNotifications extends BaseEntity {

    @OneToOne
    @JoinColumn(name = "user_id")
    User user;

    @ManyToMany
    @JoinTable(name = "laptop_notification_item",
            joinColumns = @JoinColumn(name="laptop_id"),
            inverseJoinColumns = @JoinColumn(name = "notification_id"))
    List<Laptop> laptops;
}
