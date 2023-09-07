package com.mindhub.homebanking.services.implement;

import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Service
public class AccountServiceImplement implements AccountService {

    @Autowired
    AccountRepository accountRepository;

    @Override
    public Account findByNumber(String accountNumber) {
        return accountRepository.findByNumber(accountNumber);
    }

    @Override
    public AccountDTO findById(Long id) {
        Optional<Account> account = accountRepository.findById(id);
        return account.map(AccountDTO::new).orElse(null);
    }

    @Override
    public void save(Account account) {
        accountRepository.save(account);
    }

    @Override
    public List<AccountDTO> getAll() {
        return accountRepository.findAll().stream().map(AccountDTO::new).collect(toList());
    }
}
