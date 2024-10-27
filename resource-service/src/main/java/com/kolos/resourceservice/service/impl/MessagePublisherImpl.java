package com.kolos.resourceservice.service.impl;

import com.kolos.resourceservice.service.MessagePublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class MessagePublisherImpl  implements MessagePublisher {

    private final RabbitTemplate rabbitTemplate;

    @Override
    public void postId(Long id) {
        log.info("Publishing message {}", id);
        rabbitTemplate.convertAndSend("save_metadata", id);
    }

    @Override
    public void postIds(List<Long> ids) {
        log.info("Publishing message {}", ids);
        rabbitTemplate.convertAndSend("deleteIds", ids);
    }

    @Override
    public void healthCheck() {
        log.info("Publishing health check");
        rabbitTemplate.convertAndSend("healthCheckTopic","communicationHealthCheck","Communication Health-Check Event");
    }
}
