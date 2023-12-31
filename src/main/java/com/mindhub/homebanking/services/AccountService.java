package com.mindhub.homebanking.services;

import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.models.Account;

import java.util.List;


public interface AccountService {
    Account findByNumber(String accountNumber);
    AccountDTO findById(Long id);
    void save(Account account);
    List<AccountDTO> getAll();
}
