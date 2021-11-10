package com.codegym.casemd6.service.comment;


import com.codegym.casemd6.model.Comment;
import com.codegym.casemd6.service.IGeneralService;

import java.util.List;

public interface IServiceComment extends IGeneralService<Comment> {
    List<Comment> findAllComment(Long idSong);

    List<Comment> findAllCommentOfPlaylist(Long idPlaylist);
}
