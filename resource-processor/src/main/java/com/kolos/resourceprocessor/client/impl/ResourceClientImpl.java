package com.kolos.resourceprocessor.client.impl;

import com.kolos.resourceprocessor.client.ResourceClient;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Slf4j
@Component
@RequiredArgsConstructor
public class ResourceClientImpl implements ResourceClient {

    private final WebClient webClient;
    private final LoadBalancerClient loadBalancerClient;

    @Value("${resource.service.id}")
    private String resourceServiceId;

    @Override
    @Retry(name = "MyRetry", fallbackMethod = "fallbackUploadSong")
    public byte[] uploadSong(Long id) {
        ServiceInstance choose = loadBalancerClient.choose(resourceServiceId);

        return webClient.get()
                .uri(choose.getUri() + "/resources/{id}", id)
                .retrieve()
                .bodyToMono(byte[].class)
                .block();
    }

    private byte[] fallbackUploadSong(Long id, Throwable e) {
        log.error("Fallback for uploadSong with id {}. Error: {}", id, e.getMessage());
        return new byte[0];
    }


}
