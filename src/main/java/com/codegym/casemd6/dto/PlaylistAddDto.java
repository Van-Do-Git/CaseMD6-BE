package com.codegym.casemd6.dto;

import lombok.Data;

import java.util.List;
@Data
public class PlaylistAddDto {
    private Long idPlaylist;
    private List<Long> idSongs;
}
