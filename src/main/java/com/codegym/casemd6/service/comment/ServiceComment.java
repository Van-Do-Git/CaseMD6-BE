package com.codegym.casemd6.service.comment;



import com.codegym.casemd6.model.Comment;
import com.codegym.casemd6.repository.ICommentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class ServiceComment implements IServiceComment{
    @Autowired
    private ICommentRepo commentRepo;
    @Override
    public Iterable<Comment> findAll() {
        return commentRepo.findAll();
    }

    @Override
    public Optional<Comment> findById(Long id) {
        return commentRepo.findById(id);
    }

    @Override
    public void save(Comment comment) {
        commentRepo.save(comment);
    }

    @Override
    public void remove(Long id) {
        commentRepo.deleteById(id);
    }

    @Override
    public List<Comment> findAllComment(Long idSong) {
        return commentRepo.findAllBySong_Id(idSong);
    }

    @Override
    public List<Comment> findAllCommentOfPlaylist(Long idPlaylist) {
        return commentRepo.findAllByPlaylist_Id(idPlaylist);
    }
}
