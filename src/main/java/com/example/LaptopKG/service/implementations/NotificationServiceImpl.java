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

    public List<ResponseNotificationDTO> getAllNotificationsByUser(User user){
        return toGetNotificationDto(findNotificationsByUser(user));
    }

    public ResponseNotificationDTO getNotificationById(Long id, User user){
        return toGetNotificationDto(notificationRepository.findById(id)
                .filter(notification -> notification.getUser().equals(user))
                .filter(notification -> notification.getStatus() == Status.ACTIVE)
                .orElseThrow(
                        () -> new NotFoundException("Уведомление с айди " + id + " не найдено, либо оно отправлено не вам")
                )
        );
    }

    public List<ResponseNotificationDTO> markAllNotificationsAsReadByUser(User user){
        List<Notification> notifications = notificationRepository.findAllByUser(user)
                .stream()
                .filter(notification -> notification.getStatus() == Status.ACTIVE)
                .filter(notification -> !notification.isRead())
                .toList();

        for(Notification notification:notifications){
            notification.setRead(true);
            notificationRepository.save(notification);
        }

        return toGetNotificationDto(notifications);
    }

    public ResponseNotificationDTO markNotificationAsReadById(Long id, User user){
        Notification notification = findNotificationByIdAndUser(id, user);

        notification.setRead(true);
        notificationRepository.save(notification);

        return toGetNotificationDto(notification);
    }

    public ResponseEntity<String> deleteNotificationById(Long id, User user){
        Notification notification = findNotificationByIdAndUser(id, user);

        notification.setStatus(Status.DELETED);
        notificationRepository.save(notification);

        return ResponseEntity.ok("Уведомление удалено");
    }

    public ResponseEntity<String> deleteAllNotificationsOfUser(User user){
        List<Notification> notifications = findNotificationsByUser(user);

        for(Notification notification:notifications){
            notification.setStatus(Status.DELETED);
            notificationRepository.save(notification);
        }

        return ResponseEntity.ok("Уведомления удалены");
    }

    private List<Notification> findNotificationsByUser(User user) {
        return notificationRepository.findAllByUser(user)
                .stream()
                .filter(notification -> notification.getStatus() == Status.ACTIVE)
                .toList();
    }

    private Notification findNotificationByIdAndUser(Long id, User user) {
        return notificationRepository.findById(id)
                .filter(n -> n.getUser().equals(user))
                .orElseThrow(
                        () -> new NotFoundException("Уведомление с айди " + id + " не найдено")
                );
    }
}
