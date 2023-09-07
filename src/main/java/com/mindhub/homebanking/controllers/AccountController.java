package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.services.AccountService;
import com.mindhub.homebanking.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private ClientService clientService;

    @RequestMapping("/accounts")
    public List<AccountDTO> getAccounts(){
        return accountService.getAll();
    }

    @RequestMapping("/accounts/{code}")
    public AccountDTO getAccount(@PathVariable Long code){
        return accountService.findById(code);
    }

    @RequestMapping("/clients/current/accounts")
    public List<AccountDTO> getAccounts(Authentication authentication){
        Set<Account> accounts = clientService.findByEmail(authentication.getName()).getAccounts();
        return accounts.stream().map(AccountDTO::new).collect(toList());
    }
    @RequestMapping(value = "/clients/current/accounts", method = RequestMethod.POST)
    public ResponseEntity<Object> createAccount(Authentication authentication){
        String clientEmail = authentication.getName();
        Client client = clientService.findByEmail(clientEmail);

        if (client == null){
            return new ResponseEntity<>("Client not found", HttpStatus.FORBIDDEN);
        }

        if (client.getAccounts().size() >= 3){
            return new ResponseEntity<>("Maximum number of accounts reached", HttpStatus.FORBIDDEN);
        }

        String accountNumber = "VIN-" + getRandomNumber(0, 9999999);

        Account account = new Account(accountNumber, LocalDate.now(), 0);

        client.addAccount(account);

        accountService.save(account);

        return new ResponseEntity<>("Account created", HttpStatus.CREATED);



    }

    public int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }



}
