package com.kolos.resourceprocessor.service.impl;

import com.kolos.resourceprocessor.client.ResourceClient;
import com.kolos.resourceprocessor.client.SongClient;
import com.kolos.resourceprocessor.service.MetaDataService;
import com.kolos.resourceprocessor.service.ProcessService;
import com.kolos.resourceprocessor.service.dto.MetaDataDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProcessServiceImpl implements ProcessService {

    private final ResourceClient resourceClient;
    private final SongClient songClient;
    private final MetaDataService metaDataService;


    @Override
    public void saveData(Long id) {
        byte[] song = resourceClient.uploadSong(id);
        log.info("song uploaded");
        MetaDataDto metaDataDto = metaDataService.getMetaData(song);
        songClient.create(metaDataDto);
    }
}
