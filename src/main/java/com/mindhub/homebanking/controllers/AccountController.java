package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.services.AccountService;
import com.mindhub.homebanking.services.ClientService;
import com.mindhub.homebanking.utils.CardUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/accounts")
    public List<AccountDTO> getAccounts(){
        return accountService.getAll();
    }

    @GetMapping("/accounts/{code}")
    public AccountDTO getAccount(@PathVariable Long code, Authentication authentication){
        Set<Account> accounts = clientService.findByEmail(authentication.getName()).getAccounts();
            return accountService.findById(code);
    }

    @GetMapping("/clients/current/accounts/{code}")
    public AccountDTO getCurrentAccount(@PathVariable Long code, Authentication authentication)
    {
        Set<Account> accounts = clientService.findByEmail(authentication.getName()).getAccounts();
        Account currentAccount = accounts.stream().filter(account -> account.getId() == code).findFirst().orElse(null);
        return new AccountDTO(currentAccount);
    }

    @GetMapping("/clients/current/accounts")
    public List<AccountDTO> getAccounts(Authentication authentication){
        Set<Account> accounts = clientService.findByEmail(authentication.getName()).getAccounts();
        return accounts.stream().map(AccountDTO::new).collect(toList());
    }
    @PostMapping("/clients/current/accounts")
    public ResponseEntity<Object> createAccount(Authentication authentication){
        String clientEmail = authentication.getName();
        Client client = clientService.findByEmail(clientEmail);

        if (client == null){
            return new ResponseEntity<>("Client not found", HttpStatus.FORBIDDEN);
        }

        if (client.getAccounts().size() >= 3){
            return new ResponseEntity<>("Maximum number of accounts reached", HttpStatus.FORBIDDEN);
        }

        String accountNumber = "VIN-" + CardUtils.getRandomNumber(0, 9999999);

        Account account = new Account(accountNumber, LocalDate.now(), 0);

        client.addAccount(account);

        accountService.save(account);

        return new ResponseEntity<>("Account created", HttpStatus.CREATED);



    }





}
