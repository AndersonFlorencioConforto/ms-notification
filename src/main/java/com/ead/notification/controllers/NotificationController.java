package com.ead.notification.controllers;

import com.ead.notification.dtos.NotificationDTO;
import com.ead.notification.models.NotificationModel;
import com.ead.notification.services.NotificationService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping("/users/{userId}/notifications")
    public ResponseEntity<Page<NotificationModel>> findAllNotificationByUser(@PathVariable(value = "userId") UUID userId, @PageableDefault(page = 0, size = 10, sort = "notificationId") Pageable pageable) {
        return ResponseEntity.ok().body(notificationService.findAllNotificationByUser(userId, pageable));
    }

    @PutMapping("/users/{userId}/notifications/{notificationId}")
    public ResponseEntity<Object> updateNotification(@PathVariable(value = "userId") UUID userId,
                                                     @PathVariable(value = "notificationId") UUID notificationId,
                                                     @RequestBody @Valid NotificationDTO notificationDTO) {

        Optional<NotificationModel> notificationModel = notificationService.findByNotificationIdAndUserId(notificationId, userId);
        if (notificationModel.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Notification not found!");
        }
        notificationModel.get().setNotificationStatus(notificationDTO.getNotificationStatus());
        notificationService.saveNotification(notificationModel.get());
        return ResponseEntity.status(HttpStatus.OK).body(notificationModel.get());
    }
}
