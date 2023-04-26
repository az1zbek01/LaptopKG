package com.example.LaptopKG.model;

import com.example.LaptopKG.model.baseEntity.BaseEntity;
import com.example.LaptopKG.model.enums.Status;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "notification")
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Notification extends BaseEntity {
    String header;

    String message;

    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;

    @Enumerated(EnumType.STRING)
    Status status;

    @Column(columnDefinition = "boolean default false")
    boolean read;
}
