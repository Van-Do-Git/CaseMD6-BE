package com.codegym.casemd6.service.image;


import com.codegym.casemd6.model.Image;
import com.codegym.casemd6.service.IGeneralService;

public interface IServiceImage extends IGeneralService<Image> {
    Image add(Image post);
}
