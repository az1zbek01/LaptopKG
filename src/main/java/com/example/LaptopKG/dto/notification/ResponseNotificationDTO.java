package com.example.LaptopKG.dto.notification;

import com.example.LaptopKG.model.Notification;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ResponseNotificationDTO {
    Long id;
    String header;

    String message;

    boolean read;

    public static ResponseNotificationDTO toGetNotificationDto(Notification notification){
        return ResponseNotificationDTO.builder()
                .id(notification.getId())
                .header(notification.getHeader())
                .message(notification.getMessage())
                .read(notification.isRead())
                .build();
    }

    public static List<ResponseNotificationDTO> toGetNotificationDto(List<Notification> notifications){
        return notifications.stream().map(ResponseNotificationDTO::toGetNotificationDto).collect(Collectors.toList());
    }
}
