package com.kolos.resourceservice.web;

import com.kolos.resourceservice.service.ResourceService;
import com.kolos.resourceservice.service.dto.MetaDataDto;
import com.kolos.resourceservice.service.dto.ResourceIdDto;
import com.kolos.resourceservice.service.dto.ResourceIdsDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/resources")
public class ResourceController {

    private final ResourceService resourceService;


    @PostMapping
    public ResourceIdDto upload(@RequestParam("file") MultipartFile file) throws IOException {
        return resourceService.upload(file);
    }

    @GetMapping("/song/{id}")
    public MetaDataDto getMetaSong(@PathVariable Long id) {
        return resourceService.getSong(id);
    }


    @GetMapping("/{id}")
    public byte[] download(@PathVariable long id) {
        return resourceService.download(id);
    }

    @DeleteMapping
    public ResourceIdsDto delete(@RequestParam("id") List<Long> ids) {
        return resourceService.delete(ids);
    }
}
