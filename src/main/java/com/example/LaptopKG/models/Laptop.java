package com.example.LaptopKG.models;


import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "laptop")
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Laptop extends BaseEntity{

    String model;
    String description;
    int price;
    int amount;

}
