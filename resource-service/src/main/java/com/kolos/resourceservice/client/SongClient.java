package com.kolos.resourceservice.client;

import com.kolos.resourceservice.service.dto.MetaDataDto;

import java.util.List;

public interface SongClient {

    void create(MetaDataDto metaDataDto);

    void deleteByResourceId(List<Long> resourceId);

    MetaDataDto getSongByResourceId(Long resourceId);
}
