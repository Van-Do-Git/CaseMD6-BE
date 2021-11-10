package com.codegym.casemd6.repository;


import com.codegym.casemd6.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ICommentRepo extends JpaRepository<Comment, Long> {
    @Modifying
    @Query("DELETE Comment WHERE id = ?1")
    void deleteById(Long id);

    List<Comment> findAllBySong_Id(Long id);
    List<Comment> findAllByPlaylist_Id(Long id);
}
