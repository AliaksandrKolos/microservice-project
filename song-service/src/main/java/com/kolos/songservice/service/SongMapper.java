package com.kolos.songservice.service;

import com.kolos.songservice.data.entity.MetaDataSong;
import com.kolos.songservice.service.dto.MetaDataDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SongMapper {

    MetaDataSong toEntity(MetaDataDto metaDataDto);

    MetaDataDto toDto(MetaDataSong metaDataSong);

}
