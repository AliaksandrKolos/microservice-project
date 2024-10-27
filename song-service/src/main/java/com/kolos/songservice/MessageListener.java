package com.kolos.songservice;


import com.kolos.songservice.service.SongService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class MessageListener {

    private final SongService songService;

    @RabbitListener(queues = "health_check_song_service")
    public void handleCommunicationHealthCheck(String message) {
        log.info("Song Service received message: {}", message);
    }


    @RabbitListener(queues = "deleteIds")
    public void processResourceDelete(List<Long> resourceIds) {
        songService.delete(resourceIds);
        log.info("Deleted resourceIds {}", resourceIds);
    }
}
