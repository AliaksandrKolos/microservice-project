package com.kolos.resourceservice.service.impl;

import com.kolos.resourceservice.client.SongClient;
import com.kolos.resourceservice.data.entity.Resource;
import com.kolos.resourceservice.data.repository.ResourceRepository;
import com.kolos.resourceservice.service.MetaDataService;
import com.kolos.resourceservice.service.ResourceMapper;
import com.kolos.resourceservice.service.ResourceService;
import com.kolos.resourceservice.client.StorageS3Client;
import com.kolos.resourceservice.service.dto.MetaDataDto;
import com.kolos.resourceservice.service.dto.ResourceIdDto;
import com.kolos.resourceservice.service.dto.ResourceIdsDto;
import com.kolos.resourceservice.service.exception.UnsupportedTypeException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ResourceServiceImpl implements ResourceService {

    private final ResourceRepository resourceRepository;
    private final MetaDataService metaDataService;
    private final SongClient songClient;
    private final StorageS3Client s3Client;
    private final ResourceMapper resourceMapper;


    @Override
    public ResourceIdDto upload(MultipartFile file) throws IOException {
        validationType(file);
        String location = getLocation();
        s3Client.upload(file.getBytes(), location);
        log.info("Uploaded ResourceId: {}", location);

        Resource resource = new Resource();
        resource.setLocation(location);
        resource = resourceRepository.save(resource);

        ResourceIdDto resourceIdDto = resourceMapper.toDto(resource);

        MetaDataDto metaDataDto = metaDataService.getMetaData(download(resourceIdDto.getResourceId()));
        metaDataDto.setResourceId(resource.getId());
        songClient.create(metaDataDto);
        return resourceIdDto;
    }

    @Override
    public MetaDataDto getSong(Long id) {
        if (getResourceById(id) == null) {
            throw new NoSuchElementException("Song not found with id:" + id);
        }
        return songClient.getSongByResourceId(id);
    }

    @Override
    public byte[] download(Long id) {
        Resource resource = resourceRepository.findById(id).orElse(null);
        if (resource == null) {
            throw new NoSuchElementException("Resource not found with id:" + id);
        }
        return s3Client.download(resource.getLocation());
    }

    @Override
    public ResourceIdsDto delete(List<Long> ids) {
        List<Long> deletedIdsList = new ArrayList<>();
        ids.forEach(id -> {
            if (resourceRepository.existsById(id)) {
                s3Client.delete(getResourceById(id).getLocation());
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

    private static String getLocation() {
        UUID key = UUID.randomUUID();
        return "/music/" + key;
    }

    public Resource getResourceById(Long id) {
        return resourceRepository.findById(id).orElseThrow(NoSuchElementException::new);
    }
}
