package com.kolos.resourceprocessor.config;

import com.kolos.resourceprocessor.service.ProcessService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class MessageListener {

    private final ProcessService processService;

    @RabbitListener(queues = "health_check_resource_processor")
    public void handleCommunicationHealthCheck(String message) {
        log.info("Resource Processor received message: {}", message);
    }

    @RabbitListener(queues = "save_metadata")
    public void processResourceUpload(long id) {
        log.info("Received message with id {}",  id);
        processService.saveData(id);
    }
}
