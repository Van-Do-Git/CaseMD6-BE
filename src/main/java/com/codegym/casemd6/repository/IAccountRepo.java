package com.codegym.casemd6.repository;



import com.codegym.casemd6.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IAccountRepo extends JpaRepository<Account,Long> {
        Account findByUsername(String username);
}
