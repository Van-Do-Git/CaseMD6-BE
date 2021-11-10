package com.codegym.casemd6.service.accountlike;



import com.codegym.casemd6.model.AccountLike;
import com.codegym.casemd6.repository.ILikeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;


@Service
@Transactional
public class ServiceLike implements IServiceLike{
    @Autowired
    private ILikeRepo likeRepo;
    @Override
    public Iterable<AccountLike> findAll() {
        return likeRepo.findAll();
    }

    @Override
    public Optional<AccountLike> findById(Long id) {
        return likeRepo.findById(id);
    }

    @Override
    public void save(AccountLike accountLike) {
        likeRepo.save(accountLike);
    }

    @Override
    public void remove(Long id) {
        likeRepo.deleteById(id);
    }

    @Override
    public void delete(AccountLike accountLike) {
        likeRepo.delete(accountLike);
    }

}
