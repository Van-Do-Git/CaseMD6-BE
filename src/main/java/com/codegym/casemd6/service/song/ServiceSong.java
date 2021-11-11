package com.codegym.casemd6.service.song;

import com.codegym.casemd6.model.Song;
import com.codegym.casemd6.repository.ISongRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class ServiceSong implements ISongService{
    @Autowired
    private ISongRepo songRepo;
    @Override
    public Iterable<Song> findAll() {
        return songRepo.findAll();
    }

    @Override
    public Optional<Song> findById(Long id) {
        return songRepo.findById(id);
    }

    @Override
    public void save(Song song) {
        songRepo.save(song);
    }

    @Override
    public void remove(Long id) {
        songRepo.deleteById(id);
    }

    public Iterable<Song> findByAcount(Long id){
        return songRepo.findAllByAccount_Id(id);
    }

    @Override
    public Page<Song> findAllLestes(Pageable pageable) {
        return songRepo.findAll(pageable);
    }

    @Override
    public Page<Song> findAllCount(Pageable pageable) {
        return songRepo.findAll(pageable);
    }

    @Override
    public Page<Song> findSongsByNameContaining(Pageable pageable, String name) {
        return songRepo.findSongsByNameContaining(name,pageable);
    }

    @Override
    public Page<Song> findSongsBySingerContaining(Pageable pageable, String name) {
        return songRepo.findSongsBySingerContaining(name,pageable);
    }


}
