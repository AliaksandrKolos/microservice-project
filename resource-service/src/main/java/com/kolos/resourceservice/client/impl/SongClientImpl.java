package com.kolos.resourceservice.client.impl;

import com.kolos.resourceservice.client.SongClient;
import com.kolos.resourceservice.service.dto.MetaDataDto;
import com.netflix.discovery.EurekaClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.stream.Collectors;

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


    @Override
    public void deleteByResourceId(List<Long> resourceIds) {
        ServiceInstance choose = loadBalancerClient.choose(songServiceId);
        String response =
                webClient.delete()
                        .uri(choose.getUri() + "/songs?id=" + resourceIds.stream().
                                map(String::valueOf).
                                collect(Collectors.joining(",")))
                        .retrieve()
                        .bodyToMono(String.class)
                        .block();
        log.info("Deleted data song: {}", response);
    }

    @Override
    public MetaDataDto getSongByResourceId(Long resourceId) {
        ServiceInstance choose = loadBalancerClient.choose(songServiceId);
        return webClient.get()
                .uri(choose.getUri() + "/songs/{id}", resourceId)
                .retrieve()
                .bodyToMono(MetaDataDto.class)
                .block();
    }
}
