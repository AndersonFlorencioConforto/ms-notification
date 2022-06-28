package com.ead.notification.dtos;

import com.ead.notification.models.enums.NotificationStatus;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class NotificationDTO {

    @NotNull(message = "Status não pode ser nulo")
    private NotificationStatus notificationStatus;
}
