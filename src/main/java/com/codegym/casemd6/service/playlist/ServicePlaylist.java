package com.codegym.casemd6.service.playlist;

import com.codegym.casemd6.model.Playlist;
import com.codegym.casemd6.repository.IPlaylistRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ServicePlaylist implements IServicePlaylist {
    @Autowired
    IPlaylistRepo playlistRepo;

    @Override
    public Iterable<Playlist> findAll() {
        return playlistRepo.findAll();
    }

    @Override
    public Optional<Playlist> findById(Long id) {
        return playlistRepo.findById(id);
    }

    @Override
    public void save(Playlist playlist) {
        playlistRepo.save(playlist);
    }

    @Override
    public void remove(Long id) {
        playlistRepo.deleteById(id);
    }

    @Override
    public List<Playlist> findAllByAccount_Id(Long idAccount) {
        return playlistRepo.findAllByAccount_Id(idAccount);
    }

    @Override
    public Page<Playlist> findAllPlaylistLestes(Pageable pageable) {
        return playlistRepo.findAll(pageable);
    }
}
