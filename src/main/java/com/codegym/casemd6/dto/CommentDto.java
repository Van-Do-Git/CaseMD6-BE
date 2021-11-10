package com.codegym.casemd6.dto;

import lombok.Data;

@Data
public class CommentDto {
    private String text;
    private Long idAccount;
    private Long idSong;
    private Long idPlaylist;
}
