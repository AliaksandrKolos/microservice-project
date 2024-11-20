package com.kolos.resourceservice.service.impl;

import com.kolos.resourceservice.client.StorageS3Client;
import com.kolos.resourceservice.data.entity.Resource;
import com.kolos.resourceservice.data.repository.ResourceRepository;
import com.kolos.resourceservice.service.MessagePublisher;
import com.kolos.resourceservice.service.ResourceMapper;
import com.kolos.resourceservice.service.ResourceService;
import com.kolos.resourceservice.service.dto.ResourceDto;
import com.kolos.resourceservice.service.dto.ResourceIdDto;
import com.kolos.resourceservice.service.dto.ResourceIdsDto;
import com.kolos.resourceservice.service.exception.InvalidInputException;
import com.kolos.resourceservice.service.exception.UnsupportedTypeException;
import jakarta.transaction.Transactional;
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
    private final StorageS3Client s3Client;
    private final ResourceMapper resourceMapper;
    private final MessagePublisher messagePublisher;

    private static final String SUPPORTED_CONTENT_TYPE = "audio/mpeg";


    @Override
    @Transactional
    public ResourceIdDto upload(MultipartFile file) throws IOException {
        validationType(file);
        String location = getLocation();
        s3Client.upload(file.getBytes(), location);
        log.info("Uploaded ResourceId: {}", location);

        Resource resource = new Resource();
        resource.setLocation(location);
        resource = resourceRepository.save(resource);

        ResourceIdDto resourceIdDto = resourceMapper.toDto(resource);
        messagePublisher.postId(resourceIdDto.getResourceId());

        return resourceIdDto;
    }

    @Override
    public byte[] download(Long id) {
        if (id == null) {
            throw new InvalidInputException("Id is null");
        }
        Resource resource = resourceRepository.findById(id).orElse(null);
        if (resource == null) {
            throw new NoSuchElementException("Resource not found with id:" + id);
        }
        return s3Client.download(resource.getLocation());
    }

    @Override
    public ResourceDto getSong(Long id) {
        if (id == null) {
            throw new InvalidInputException("Id is null");
        }
        Resource resource = resourceRepository.findById(id).orElse(null);
        if (resource == null) {
            throw new NoSuchElementException("Song not found with id:" + id);
        }
        return resourceMapper.toDtoResource(resource);
    }

    @Override
    public List<ResourceDto> getAllSong() {
        return resourceRepository.findAll().stream()
                .map(resourceMapper::toDtoResource)
                .toList();
    }

    @Override
    @Transactional
    public ResourceIdsDto delete(List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            throw new InvalidInputException("Ids is null or empty");
        }
        List<Long> deletedIdsList = new ArrayList<>();
        ids.forEach(id -> {
            if (resourceRepository.existsById(id)) {
                s3Client.delete(getSong(id).getLocation());
                resourceRepository.deleteById(id);
                deletedIdsList.add(id);
            }
        });
        ResourceIdsDto deletedIds = new ResourceIdsDto();
        deletedIds.setIds(deletedIdsList);
        messagePublisher.postIds(deletedIdsList);
        return deletedIds;
    }



    private static void validationType(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new InvalidInputException("File cannot be null or empty");
        }

        String contentType = file.getContentType();
        if (!SUPPORTED_CONTENT_TYPE.equals(contentType)) {
            throw new UnsupportedTypeException("Unsupported media type: " + (contentType != null ? contentType : "null"));
        }
    }


    private static String getLocation() {
        UUID key = UUID.randomUUID();
        return "/music/" + key;
    }

}
