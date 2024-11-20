package com.kolos.songservice.web;

import com.kolos.songservice.service.SongService;
import com.kolos.songservice.service.dto.MetaDataDto;
import com.kolos.songservice.service.dto.SongIdDto;
import com.kolos.songservice.service.dto.SongIdsDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/songs")
public class SongController {

    private final SongService songService;

    @PostMapping
    public SongIdDto create(@RequestBody MetaDataDto metaDataDto) {
        return songService.save(metaDataDto);
    }

    @GetMapping("/{id}")
    public MetaDataDto get(@PathVariable Long id) {
        return songService.getSong(id);
    }

    @DeleteMapping
    public SongIdsDto delete(@RequestParam("id") List<Long> ids) {
        return songService.delete(ids);
    }
}
