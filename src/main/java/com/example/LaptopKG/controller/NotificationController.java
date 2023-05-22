package com.example.LaptopKG.controller;

import com.example.LaptopKG.dto.notification.ResponseNotificationDTO;
import com.example.LaptopKG.model.User;
import com.example.LaptopKG.service.implementations.NotificationServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
@AllArgsConstructor
@CrossOrigin(origins = "*")
@Tag(
        name = "Контроллер для работы с уведомлениями",
        description = "В этом контроллере есть возможность получения уведомлений"
)
public class NotificationController {
    private final NotificationServiceImpl notificationServiceImpl;

    @GetMapping()
    @SecurityRequirement(name = "JWT")
    @Operation(
            summary = "Получение всех уведомлений авторизованного пользователя"
    )
    public List<ResponseNotificationDTO> getAllNotificationsByUser(@AuthenticationPrincipal User user){
        return notificationServiceImpl.getAllNotificationsByUser(user);
    }

    @GetMapping("/{id}")
    @SecurityRequirement(name = "JWT")
    @Operation(
            summary = "Получение уведомления по айди"
    )
    public ResponseNotificationDTO getNotificationById(@PathVariable Long id,
                                                       @AuthenticationPrincipal User user){
        return notificationServiceImpl.getNotificationById(id, user);
    }

    @PutMapping()
    @SecurityRequirement(name = "JWT")
    @Operation(
            summary = "Отметить все уведомления как прочитанное"
    )
    public List<ResponseNotificationDTO> markAllNotificationsAsReadByUser(@AuthenticationPrincipal User user){
        return notificationServiceImpl.markAllNotificationsAsReadByUser(user);
    }

    @PutMapping("/{id}")
    @SecurityRequirement(name = "JWT")
    @Operation(
            summary = "Отметить уведомление как прочитанное"
    )
    public ResponseNotificationDTO markNotificationAsReadById(@PathVariable Long id,
                                                              @AuthenticationPrincipal User user){
        return notificationServiceImpl.markNotificationAsReadById(id, user);
    }

    @DeleteMapping("/{id}")
    @SecurityRequirement(name = "JWT")
    @Operation(
            summary = "Удалить уведомление по айди"
    )
    public ResponseEntity<String> deleteNotificationById(@PathVariable Long id,
                                                         @AuthenticationPrincipal User user){
        return notificationServiceImpl.deleteNotificationById(id, user);
    }

    @DeleteMapping()
    @SecurityRequirement(name = "JWT")
    @Operation(
            summary = "Удалить все уведомления авторизованного пользователя"
    )
    public ResponseEntity<String> deleteAllNotificationsOfUser(@AuthenticationPrincipal User user){
        return notificationServiceImpl.deleteAllNotificationsOfUser(user);
    }
}
