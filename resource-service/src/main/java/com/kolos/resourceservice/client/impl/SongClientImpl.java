package com.kolos.resourceservice.client.impl;

import com.kolos.resourceservice.client.SongClient;
import com.kolos.resourceservice.service.dto.MetaDataDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class SongClientImpl implements SongClient {

    private final WebClient webClient;

    @Value("${song.service.url}")
    private  String songServiceUrl;

    @Override
    public void create(MetaDataDto metaDataDto) {
        String response =
                webClient.post()
                        .uri(songServiceUrl + "/songs")
                        .bodyValue(metaDataDto)
                        .retrieve()
                        .bodyToMono(String.class)
                        .block();
        log.info("Created data song: {}", response);
    }


    @Override
    public void deleteByResourceId(List<Long> resourceIds) {
        String response =
                webClient.delete()
                        .uri(songServiceUrl + "/songs?id=" + resourceIds.stream().
                                map(String::valueOf).
                                collect(Collectors.joining(",")))
                        .retrieve()
                        .bodyToMono(String.class)
                        .block();
        log.info("Deleted data song: {}", response);

    }
}
