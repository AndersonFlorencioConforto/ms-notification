package com.ead.notification.consumers;

import com.ead.notification.dtos.NotificationCommandDTO;
import com.ead.notification.models.NotificationModel;
import com.ead.notification.models.enums.NotificationStatus;
import com.ead.notification.services.NotificationService;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.BeanUtils;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class NotificationConsumer {

    private final NotificationService notificationService;

    public NotificationConsumer(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "${ead.broker.queue.notificationCommandQueue.name}", durable = "true"),
            exchange = @Exchange(value = "${ead.broker.exchange.notificationCommandExchange}", type = ExchangeTypes.TOPIC, ignoreDeclarationExceptions = "true"),
            key = "${ead.broker.key.notificationCommandKey}"))
    public void listen(@Payload NotificationCommandDTO notificationCommandDTO) {
        NotificationModel notificationModel = new NotificationModel();
        BeanUtils.copyProperties(notificationCommandDTO,notificationModel);
        notificationModel.setNotificationStatus(NotificationStatus.CREATED);
        notificationService.saveNotification(notificationModel);
    }
}
