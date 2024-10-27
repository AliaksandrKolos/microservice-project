package com.kolos.resourceprocessor.client.impl;


import com.kolos.resourceprocessor.client.SongClient;
import com.kolos.resourceprocessor.service.dto.MetaDataDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;



@Slf4j
@Service
@RequiredArgsConstructor
public class SongClientImpl implements SongClient {

    private final WebClient webClient;
    private final LoadBalancerClient loadBalancerClient;


    @Value("${song.service.id}")
    private  String songServiceId;

    @Override
    public void create(MetaDataDto metaDataDto) {
        ServiceInstance choose = loadBalancerClient.choose(songServiceId);
        String response =
                webClient.post()
                        .uri(choose.getUri() + "/songs")
                        .bodyValue(metaDataDto)
                        .retrieve()
                        .bodyToMono(String.class)
                        .block();
        log.info("Created data song: {}", response);
    }
}
