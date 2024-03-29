package com.example.LaptopKG.model;


import com.example.LaptopKG.model.baseEntity.BaseEntity;
import com.example.LaptopKG.model.enums.Category;
import com.example.LaptopKG.model.enums.Guarantee;
import com.example.LaptopKG.model.enums.Status;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "laptop")
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Laptop extends BaseEntity {
    String name;

    String description;

    int price;

    int amount;

    @Enumerated(EnumType.STRING)
    Guarantee guarantee;

    @Enumerated(EnumType.STRING)
    Status status;

    @Enumerated(EnumType.STRING)
    Category category;

    @ManyToMany
    @JoinTable(
            name = "laptop_hardware",
            joinColumns = @JoinColumn(name = "laptop_id"),
            inverseJoinColumns = @JoinColumn(name = "hardware_id")
    )
    List<Hardware> hardwareList;

    @ManyToOne
    @JoinColumn(name = "brand_id")
    Brand brand;

    String imageUrl;

    Double averageScore;
}
