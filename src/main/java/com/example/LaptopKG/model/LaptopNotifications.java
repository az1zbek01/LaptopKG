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

    @OneToMany
    List<Laptop> laptops;
}
