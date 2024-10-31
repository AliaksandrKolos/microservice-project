package com.kolos.resourceprocessor.client.impl;


import com.kolos.resourceprocessor.client.SongClient;
import com.kolos.resourceprocessor.service.dto.MetaDataDto;
import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.retry.annotation.Retry;
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

    @Value("${gateway.service.id}")
    private String gatewayServiceId;

    @Override
    @Retry(name = "MyRetry")
    @Bulkhead(name = "MyBulkhead", fallbackMethod = "fallbackCreate")
    public void create(MetaDataDto metaDataDto) {
        ServiceInstance choose = loadBalancerClient.choose(gatewayServiceId);

        String response =
                webClient.post()
                        .uri(choose.getUri() + "/songs")
                        .bodyValue(metaDataDto)
                        .retrieve()
                        .bodyToMono(String.class)
                        .block();
        log.info("Created data song: {}", response);
    }

    private void fallbackCreate(MetaDataDto metaDataDto, Throwable throwable) {
        log.error("Fallback create failed for meta data: {}. Cause: {}", metaDataDto, throwable.getMessage());
    }

}
