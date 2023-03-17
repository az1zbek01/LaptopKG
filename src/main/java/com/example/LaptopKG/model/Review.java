package com.example.LaptopKG.model;


import com.example.LaptopKG.model.baseEntity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "review")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Review extends BaseEntity {

    @CreationTimestamp
    private LocalDateTime created;

    @UpdateTimestamp
    private LocalDateTime updated;

    String text;

    int score;

    @ManyToOne
    @JoinColumn(name = "laptop_id")
    Laptop laptop;

    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;


}
