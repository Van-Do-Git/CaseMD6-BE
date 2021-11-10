package com.codegym.casemd6.repository;

import com.codegym.casemd6.model.Playlist;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IPlaylistRepo extends JpaRepository<Playlist,Long> {
    List<Playlist> findAllByAccount_Id(Long idAccount);
}
