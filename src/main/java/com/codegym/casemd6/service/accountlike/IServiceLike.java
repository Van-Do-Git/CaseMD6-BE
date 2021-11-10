package com.codegym.casemd6.service.accountlike;


import com.codegym.casemd6.model.AccountLike;
import com.codegym.casemd6.service.IGeneralService;

public interface IServiceLike extends IGeneralService<AccountLike> {
    void delete(AccountLike accountLike);
}
