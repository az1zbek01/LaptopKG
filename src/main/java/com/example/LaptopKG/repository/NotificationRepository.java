package com.example.LaptopKG.repository;

import com.example.LaptopKG.model.Notification;
import com.example.LaptopKG.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findAllByUser(User user);
}
