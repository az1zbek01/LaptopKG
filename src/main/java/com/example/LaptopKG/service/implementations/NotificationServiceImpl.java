package com.example.LaptopKG.service.implementations;

import com.example.LaptopKG.dto.notification.ResponseNotificationDTO;
import com.example.LaptopKG.exception.NotFoundException;
import com.example.LaptopKG.model.Notification;
import com.example.LaptopKG.model.User;
import com.example.LaptopKG.model.enums.Status;
import com.example.LaptopKG.repository.NotificationRepository;
import com.example.LaptopKG.service.NotificationService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.LaptopKG.dto.notification.ResponseNotificationDTO.toGetNotificationDto;

@Service
@AllArgsConstructor
public class NotificationServiceImpl implements NotificationService {
    private final NotificationRepository notificationRepository;

    // Getting all notification by certain user
    public List<ResponseNotificationDTO> getAllNotificationsByUser(User user){
        // Get all notifications, map from entity to dto and return them
        return toGetNotificationDto(notificationRepository.findAllByUser(user)
                // Find active notifications
                .stream().filter(notification -> notification.getStatus() == Status.ACTIVE)
                        .toList()
                );
    }

    // Get notification by id
    public ResponseNotificationDTO getNotificationById(Long id, User user){
        // Get notification by id and return it or throw exception if it doesn't exist
        return toGetNotificationDto(notificationRepository.findById(id)
                // Check if notification is active
                .filter(notification -> notification.getUser().equals(user))
                .filter(notification -> notification.getStatus() == Status.ACTIVE)
                .orElseThrow(
                        () -> new NotFoundException("Уведомление с айди " + id + " не найдено, либо оно отправлено не вам")
                )
        );
    }

    // Mark all notifications of certain user as read
    public List<ResponseNotificationDTO> markAllNotificationsAsReadByUser(User user){
        // Get all notifications of user
        List<Notification> notifications = notificationRepository.findAllByUser(user)
                .stream()
                .filter(notification -> notification.getStatus() == Status.ACTIVE)
                .filter(notification -> !notification.isRead())
                .toList();

        // Go through notifications
        for(Notification notification:notifications){
            // Mark every notification as read and save it
            notification.setRead(true);
            notificationRepository.save(notification);
        }

        // Return updated notifications
        return toGetNotificationDto(notifications);
    }

    // Marking notification as read
    public ResponseNotificationDTO markNotificationAsReadById(Long id, User user){
        // Get notification by id or throw exception if it doesn't exist
        Notification notification = notificationRepository.findById(id)
                .filter(n -> n.getUser().equals(user))
                .orElseThrow(
                () -> new NotFoundException("Уведомление с айди " + id + " не найдено")
        );

        // Mark notification as read and save it
        notification.setRead(true);
        notificationRepository.save(notification);

        // Return updated notification
        return toGetNotificationDto(notification);
    }

    // Notification deleting by id
    public ResponseEntity<String> deleteNotificationById(Long id, User user){
        // Get notification by id or throw exception if it doesn't exist
        Notification notification = notificationRepository.findById(id)
                .filter(n -> n.getUser().equals(user))
                .orElseThrow(
                () -> new NotFoundException("Уведомление с айди " + id + " не найдено")
        );

        // Mark notification as deleted
        notification.setStatus(Status.DELETED);
        notificationRepository.save(notification);

        // Return status 200 and message
        return ResponseEntity.ok("Уведомление удалено");
    }

    // Notification deleting
    public ResponseEntity<String> deleteAllNotificationsOfUser(User user){
        // Get all existing notifications of user
        List<Notification> notifications = notificationRepository.findAllByUser(user)
                .stream()
                .filter(notification -> notification.getStatus() == Status.ACTIVE)
                        .toList();

        // Mark notifications as deleted
        for(Notification notification:notifications){
            notification.setStatus(Status.DELETED);
            notificationRepository.save(notification);
        }

        // Return status 200 and message
        return ResponseEntity.ok("Уведомления удалены");
    }
}
