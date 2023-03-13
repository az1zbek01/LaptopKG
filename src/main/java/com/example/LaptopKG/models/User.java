package com.example.LaptopKG.models;

import com.example.LaptopKG.models.enums.Role;
import lombok.AccessLevel;
import lombok.Data;

import jakarta.persistence.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "user")
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User extends BaseEntity{

    @Column(unique = true)
    String username;

    @Column(unique = true)
    String email;

    @Column
    String password;

    String address;
    @Column(name = "phone_number")
    String phoneNumber;

    @Column(name = "first_name")
    String firstName;

    @Column(name = "last_name")
    String lastName;

    @Enumerated(EnumType.STRING)
    Role roles;

}
