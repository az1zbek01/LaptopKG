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

    @OneToMany
    List<Hardware> model;

    String description;

    int price;

    int amount;

    int discount;

    @Enumerated(EnumType.STRING)
    Guarantee guarantee;

    @Enumerated(EnumType.STRING)
    Status status;

    @Enumerated(EnumType.STRING)
    Category category;

    @ManyToOne
    @JoinColumn(name = "brand_id")
    Brand brand;

    @OneToMany
    List<Review> reviews = new ArrayList<>();

    @OneToMany
    List<Image> images = new ArrayList<>();


}
