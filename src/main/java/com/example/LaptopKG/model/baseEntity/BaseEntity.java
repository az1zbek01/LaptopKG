package com.example.LaptopKG.model.baseEntity;


import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
public class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    //TODO check identity, auto
//
//    @CreationTimestamp
//    private LocalDateTime created;
//    //    later
//    @UpdateTimestamp
//    private LocalDateTime updated;

}
