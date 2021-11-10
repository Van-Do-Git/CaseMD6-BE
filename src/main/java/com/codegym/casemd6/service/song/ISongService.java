package com.codegym.casemd6.service.song;

import com.codegym.casemd6.model.Song;
import com.codegym.casemd6.service.IGeneralService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ISongService extends IGeneralService<Song> {
    Iterable<Song> findByAcount(Long id);

    Page<Song> findAllLestes(Pageable pageable);
    Page<Song> findAllCount(Pageable pageable);
    Page<Song> findSongsByNameContaining(Pageable pageable, String name);

    Page<Song> findSongsBySingerContaining(Pageable pageable, String name);
}
