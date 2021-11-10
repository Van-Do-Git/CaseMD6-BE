package com.codegym.casemd6.dto;

import com.codegym.casemd6.model.Image;
import lombok.Data;

@Data
public class LoginAccount {
    private Long id;
    private String fullName;
    private Image avatar;
    private String token;
}