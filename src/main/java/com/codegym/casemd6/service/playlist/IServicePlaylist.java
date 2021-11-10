package com.codegym.casemd6.service.playlist;

import com.codegym.casemd6.model.Playlist;
import com.codegym.casemd6.service.IGeneralService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IServicePlaylist extends IGeneralService<Playlist> {
    List<Playlist> findAllByAccount_Id(Long idAccount);
    Page<Playlist> findAllPlaylistLestes(Pageable pageable);
}
