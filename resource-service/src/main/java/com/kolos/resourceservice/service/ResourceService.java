package com.kolos.resourceservice.service;

import com.kolos.resourceservice.service.dto.MetaDataDto;
import com.kolos.resourceservice.service.dto.ResourceIdDto;
import com.kolos.resourceservice.service.dto.ResourceIdsDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ResourceService {

    byte[] download(Long id);

    ResourceIdsDto delete(List<Long> ids);

    ResourceIdDto upload(MultipartFile file) throws IOException;

    MetaDataDto getSong(Long id);
}
