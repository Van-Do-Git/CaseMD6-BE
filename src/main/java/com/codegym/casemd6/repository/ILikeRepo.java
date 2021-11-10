package com.codegym.casemd6.repository;



import com.codegym.casemd6.model.AccountLike;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ILikeRepo extends JpaRepository<AccountLike,Long> {

    @Modifying
    @Query("DELETE AccountLike WHERE id = ?1")
    void deleteById(Long id);
}
