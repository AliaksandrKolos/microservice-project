package com.kolos.songservice.service;

import com.kolos.songservice.service.dto.MetaDataDto;
import com.kolos.songservice.service.dto.SongIdDto;
import com.kolos.songservice.service.dto.SongIdsDto;

import java.util.List;

public interface SongService {

    SongIdDto save(MetaDataDto metaDataSaveDto);

    MetaDataDto getSong(Long id);

    SongIdsDto delete(List<Long> ids);
}
