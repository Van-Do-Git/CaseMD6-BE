package com.codegym.casemd6.dto;

import lombok.Data;

@Data
public class ChangePasswordDto {
    private Long idAccount;
    private String oldPassword;
    private String newPassword;
}
