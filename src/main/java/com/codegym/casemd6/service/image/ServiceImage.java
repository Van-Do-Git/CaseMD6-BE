package com.codegym.casemd6.service.image;



import com.codegym.casemd6.model.Image;
import com.codegym.casemd6.repository.IImageRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ServiceImage implements IServiceImage{
    @Autowired
    private IImageRepo iImageRepo;
    @Override
    public Iterable<Image> findAll() {
        return iImageRepo.findAll();
    }

    @Override
    public Optional<Image> findById(Long id) {
        return iImageRepo.findById(id);
    }

    @Override
    public void save(Image image) {
        iImageRepo.save(image);
    }

    @Override
    public void remove(Long id) {
        iImageRepo.deleteById(id);
    }

    @Override
    public Image add(Image post) {
        return iImageRepo.save(post);
    }
}
