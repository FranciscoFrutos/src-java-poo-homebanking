package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api")
public class AccountController {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ClientRepository clientRepository;

    @RequestMapping("/accounts")
    public List<AccountDTO> getAccounts(){
        return accountRepository.findAll().stream().map(AccountDTO::new).collect(toList());
    }

    @RequestMapping("/accounts/{code}")
    public AccountDTO getAccount(@PathVariable Long code){
        Optional<Account> account = accountRepository.findById(code);
        return account.map(AccountDTO::new).orElse(null);
    }

    @RequestMapping("/clients/current/accounts")
    public ResponseEntity<Object> createAccount(Authentication authentication){
        String clientEmail = authentication.getName();
        Client client = clientRepository.findByEmail(clientEmail);

        if (client == null){
            return new ResponseEntity<>("Client not found", HttpStatus.FORBIDDEN);
        }

        if (client.getAccounts().size() >= 3){
            return new ResponseEntity<>("Maximum number of accounts reached", HttpStatus.FORBIDDEN);
        }

        String accountNumber = "VIN-" + getRandomNumber(0, 9999999);

        Account account = new Account(accountNumber, LocalDate.now(), 0);

        client.addAccount(account);

        accountRepository.save(account);

        return new ResponseEntity<>("Account created", HttpStatus.CREATED);



    }

    public int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }



}
