package com.kolos.resourceservice.service.impl;

import com.kolos.resourceservice.service.MessagePublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class MessagePublisherImpl  implements MessagePublisher {

    private final RabbitTemplate rabbitTemplate;

    @Value("${routing.key.save.metaData}")
    private String routingKeySaveMetaData;

    @Value("${routing.key.deleteIds}")
    private String routingKeyDeleteIds;

    @Value("${routing.key.communication.health.check}")
    private String routingKeyCommunicationHealthCheck;

    @Value("${exchange.name}")
    private String nameExchange;

    @Value("${send.object}")
    private String sendObject;


    @Override
    public void postId(Long id) {
        log.info("Publishing message {}", id);
        rabbitTemplate.convertAndSend(routingKeySaveMetaData, id);
    }

    @Override
    public void postIds(List<Long> ids) {
        log.info("Publishing message {}", ids);
        rabbitTemplate.convertAndSend(routingKeyDeleteIds, ids);
    }

    @Override
    public void healthCheck() {
        log.info("Publishing health check");
        rabbitTemplate.convertAndSend(nameExchange, routingKeyCommunicationHealthCheck, sendObject);
    }
}
