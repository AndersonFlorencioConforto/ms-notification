package com.ead.notification.models;

import com.ead.notification.models.enums.NotificationStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL) // ignora os atributos com valores nulos, á nível de classe.
@Entity
@Table(name = "TB_NOTIFICATIONS")
public class NotificationModel implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID notificationId;

    @Column(nullable = false)
    private UUID userId;

    @Column(nullable = false,length = 150)
    private String title;

    @Column(nullable = false)
    private String message;

    @Column(nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    @CreationTimestamp
    private LocalDateTime creationDate;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private NotificationStatus notificationStatus;

}
