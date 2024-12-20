package com.kolos.songservice.service.impl;

import com.kolos.songservice.data.entity.MetaDataSong;
import com.kolos.songservice.data.repository.SongRepository;
import com.kolos.songservice.service.SongMapper;
import com.kolos.songservice.service.SongService;
import com.kolos.songservice.service.dto.MetaDataDto;
import com.kolos.songservice.service.dto.SongIdDto;
import com.kolos.songservice.service.dto.SongIdsDto;
import com.kolos.songservice.service.exception.InvalidInputException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class SongServiceImpl implements SongService {

    private final SongRepository songRepository;
    private final SongMapper songMapper;

    @Override
    @Transactional
    public SongIdDto save(MetaDataDto metaDataSaveDto) {
        if (metaDataSaveDto == null) {
            throw new InvalidInputException("MetaDataDto cannot be null");
        }
        MetaDataSong metaDataSong = songMapper.toEntity(metaDataSaveDto);
        metaDataSong = songRepository.save(metaDataSong);
        SongIdDto songIdDto = new SongIdDto();
        songIdDto.setId(metaDataSong.getResourceId());
        return songIdDto;
    }

    @Override
    public MetaDataDto getSong(Long id) {
        if (id == null) {
            throw new InvalidInputException("Id cannot be null");
        }
        MetaDataSong metaDataSong = songRepository.findById(id).orElse(null);
        if (metaDataSong == null) {
            throw new NoSuchElementException("Song not found with id:" + id);
        }
        return songMapper.toDto(metaDataSong);
    }

    @Override
    @Transactional
    public SongIdsDto delete(List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            throw new InvalidInputException("Ids cannot be null or empty");
        }
        List<Long> deletedListSong = new ArrayList<>();
        ids.forEach(id -> {
            if (songRepository.existsByResourceId(id)) {
                songRepository.deleteByResourceId(id);
                deletedListSong.add(id);
            }
        });
        SongIdsDto songIdsDto = new SongIdsDto();
        songIdsDto.setSongIds(deletedListSong);
        return songIdsDto;
    }

}

