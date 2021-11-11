package com.codegym.casemd6.repository;

import com.codegym.casemd6.model.Song;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ISongRepo extends JpaRepository<Song,Long> {
    Iterable<Song> findAllByAccount_Id(Long id);
    Page<Song> findAll(Pageable pageable);

    @Query ("select s from Song s where s.singer like %?1%")
    Page<Song> findSongsByNameContaining(String name,Pageable page);

    @Query ("select s from Song s where s.singer like %?1%")
    Page<Song> findSongsBySingerContaining(String name,Pageable page);


}
