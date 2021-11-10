package com.codegym.casemd6.repository;


import com.codegym.casemd6.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IImageRepo extends JpaRepository<Image,Long> {
}
