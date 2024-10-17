package com.kolos.songservice.data.repository;

import com.kolos.songservice.data.entity.MetaDataSong;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SongRepository extends JpaRepository <MetaDataSong, Long>{

    boolean existsByResourceId(Long resourceId);

    Long deleteByResourceId(Long resourceId);
}
