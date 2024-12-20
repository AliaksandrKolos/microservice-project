package com.kolos.resourceservice.web;

import com.kolos.resourceservice.service.MessagePublisher;
import com.kolos.resourceservice.service.ResourceService;
import com.kolos.resourceservice.service.dto.ResourceDto;
import com.kolos.resourceservice.service.dto.ResourceIdDto;
import com.kolos.resourceservice.service.dto.ResourceIdsDto;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/resources")
public class ResourceController {

    private final ResourceService resourceService;
    private final MessagePublisher messagePublisher;


    @PostMapping
    @RateLimiter(name = "resourceService")
    public ResourceIdDto upload(@RequestParam("file") MultipartFile file) throws IOException {
        return resourceService.upload(file);
    }

    @GetMapping("/song/{id}")
    @RateLimiter(name = "resourceService")
    public ResourceDto getSong(@PathVariable Long id) {
        return resourceService.getSong(id);
    }

    @GetMapping("/communication-health-check")
    @RateLimiter(name = "resourceService")
    public void communicationHealthCheck() {
        messagePublisher.healthCheck();
    }

    @GetMapping("/all_songs")
    @RateLimiter(name = "resourceService")
    public List<ResourceDto> getAllSong() {
        return resourceService.getAllSong();
    }

    @GetMapping("/{id}")
    @RateLimiter(name = "resourceService")
    public byte[] download(@PathVariable Long id) {
        return resourceService.download(id);
    }

    @DeleteMapping
    @RateLimiter(name = "resourceService")
    public ResourceIdsDto delete(@RequestParam("id") List<Long> ids) {
        return resourceService.delete(ids);
    }
}
