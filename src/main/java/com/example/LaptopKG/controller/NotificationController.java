package com.example.LaptopKG.controller;

import com.example.LaptopKG.dto.notification.GetNotificationDto;
import com.example.LaptopKG.dto.review.GetReviewDto;
import com.example.LaptopKG.model.User;
import com.example.LaptopKG.service.NotificationService;
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
@Tag(
        name = "Контроллер для работы с уведомлениями",
        description = "В этом контроллере есть возможность получения уведомлений"
)
public class NotificationController {
    private final NotificationService notificationService;

    @GetMapping()
    @SecurityRequirement(name = "JWT")
    @Operation(
            summary = "Получение всех уведомлений авторизованного пользователя"
    )
    public List<GetNotificationDto> getAllNotificationsByUser(@AuthenticationPrincipal User user){
        return notificationService.getAllNotificationsByUser(user);
    }

    @GetMapping("/{id}")
    @SecurityRequirement(name = "JWT")
    @Operation(
            summary = "Получение уведомления по айди"
    )
    public GetNotificationDto getNotificationById(@PathVariable long id,
                                                  @AuthenticationPrincipal User user){
        return notificationService.getNotificationById(id, user);
    }

    @PutMapping()
    @SecurityRequirement(name = "JWT")
    @Operation(
            summary = "Отметить все уведомления как прочитанное"
    )
    public List<GetNotificationDto> markAllNotificationsAsReadByUser(@AuthenticationPrincipal User user){
        return notificationService.markAllNotificationsAsReadByUser(user);
    }

    @PutMapping("/{id}")
    @SecurityRequirement(name = "JWT")
    @Operation(
            summary = "Отметить уведомление как прочитанное"
    )
    public GetNotificationDto markNotificationAsReadById(@PathVariable long id,
                                                         @AuthenticationPrincipal User user){
        return notificationService.markNotificationAsReadById(id, user);
    }

    @DeleteMapping("/{id}")
    @SecurityRequirement(name = "JWT")
    @Operation(
            summary = "Удалить уведомление по айди"
    )
    public ResponseEntity<String> deleteNotificationById(@PathVariable long id,
                                                         @AuthenticationPrincipal User user){
        return notificationService.deleteNotificationById(id, user);
    }

    @DeleteMapping()
    @SecurityRequirement(name = "JWT")
    @Operation(
            summary = "Удалить все уведомления авторизованного пользователя"
    )
    public ResponseEntity<String> deleteAllNotificationsOfUser(@AuthenticationPrincipal User user){
        return notificationService.deleteAllNotificationsOfUser(user);
    }
}
