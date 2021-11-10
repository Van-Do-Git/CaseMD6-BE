package com.codegym.casemd6.service.account;


import com.codegym.casemd6.model.Account;
import com.codegym.casemd6.service.IGeneralService;

public interface IServiceAccount extends IGeneralService<Account> {
     Account loadUserByUserName(String email);


     boolean checkLogin(Account account);

     boolean add(Account account);

}
