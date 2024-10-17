package com.kolos.resourceservice.service.impl;

import com.kolos.resourceservice.client.SongClient;
import com.kolos.resourceservice.data.entity.Resource;
import com.kolos.resourceservice.data.repository.ResourceRepository;
import com.kolos.resourceservice.service.MetaDataService;
import com.kolos.resourceservice.service.ResourceService;
import com.kolos.resourceservice.service.dto.MetaDataDto;
import com.kolos.resourceservice.service.dto.ResourceIdDto;
import com.kolos.resourceservice.service.dto.ResourceIdsDto;
import com.kolos.resourceservice.service.exception.UnsupportedTypeException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class ResourceServiceImpl implements ResourceService {

    private final ResourceRepository resourceRepository;
    private final MetaDataService metaDataService;
    private final SongClient songClient;


    @Override
    public ResourceIdDto upload(MultipartFile file) throws IOException {
        validationType(file);
        Resource resource = new Resource();
        resource.setAudio(file.getBytes());
        resource = resourceRepository.save(resource);

        ResourceIdDto resourceIdDto = new ResourceIdDto();
        resourceIdDto.setResourceId(resource.getId());

        MetaDataDto metaDataDto = metaDataService.getMetaData(download(resourceIdDto.getResourceId()));
        metaDataDto.setResourceId(resource.getId());
        songClient.create(metaDataDto);

        return resourceIdDto;
    }


    @Override
    public byte[] download(Long id) {
        Resource resource = resourceRepository.findById(id).orElse(null);
        if (resource == null) {
            throw new NoSuchElementException("Resource not found with id:" + id);
        }
        return resource.getAudio();
    }

    @Override
    public ResourceIdsDto delete(List<Long> ids) {
        List<Long> deletedIdsList = new ArrayList<>();
        ids.forEach(id -> {
            if (resourceRepository.existsById(id)) {
                resourceRepository.deleteById(id);
                deletedIdsList.add(id);
            }
        });

        ResourceIdsDto deletedIds = new ResourceIdsDto();
        deletedIds.setIds(deletedIdsList);
        songClient.deleteByResourceId(deletedIdsList);
        return deletedIds;
    }

    private static void validationType(MultipartFile file) {
        String contentType = file.getContentType();
        if (!"audio/mpeg".equals(contentType)) {
            throw new UnsupportedTypeException("Unsupported media type: " + contentType);
        }
    }
}
