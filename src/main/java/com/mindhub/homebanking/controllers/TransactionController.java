package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.repositories.TransactionRepository;
import com.mindhub.homebanking.services.AccountService;
import com.mindhub.homebanking.services.ClientService;
import com.mindhub.homebanking.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.time.LocalDate;

@RestController
@RequestMapping("/api")
public class TransactionController {

    @Autowired
    AccountService accountService;
    @Autowired
    TransactionService transactionService;
    @Autowired
    ClientService clientService;

    @Transactional
    @PostMapping("/transactions")
    public ResponseEntity<Object> createTransaction(
            Authentication authentication,
            @RequestParam double amount,
            @RequestParam String description,
            @RequestParam String fromAccountNumber,
            @RequestParam String toAccountNumber
            ){


        if (amount <= 0 || fromAccountNumber.isEmpty() || toAccountNumber.isEmpty()){
            return new ResponseEntity<>("Missing parameters.", HttpStatus.FORBIDDEN);
        }

        Account sourceAccount = accountService.findByNumber(fromAccountNumber);
        Account destinationAccount = accountService.findByNumber(toAccountNumber);
        Client client = clientService.findByEmail(authentication.getName());

        if (fromAccountNumber.equals(toAccountNumber)){
            return  new ResponseEntity<>("Source and destination accounts cannot be the same.", HttpStatus.FORBIDDEN);
        }
        if ( sourceAccount == null){
            return new ResponseEntity<>("The source account does not exist.", HttpStatus.FORBIDDEN);
        }
        if (client.getId() != sourceAccount.getClient().getId()){
            return new ResponseEntity<>("The source account does not belong to the authenticated client", HttpStatus.FORBIDDEN);
        }
        if ( destinationAccount == null){
            return new ResponseEntity<>("The destination account does not exist.", HttpStatus.FORBIDDEN);
        }
        if (sourceAccount.getBalance() < amount){
            return new ResponseEntity<>("Insufficient balance", HttpStatus.FORBIDDEN);
        }

        Transaction debitTransaction = new Transaction(TransactionType.DEBIT, -amount, description + " " + fromAccountNumber, LocalDate.now());
        Transaction creditTransaction = new Transaction(TransactionType.CREDIT, amount, description + " " + toAccountNumber, LocalDate.now());
        sourceAccount.addTransaction(debitTransaction);
        destinationAccount.addTransaction(creditTransaction);
        transactionService.save(debitTransaction);
        transactionService.save(creditTransaction);

        sourceAccount.setBalance(sourceAccount.getBalance() - amount);
        destinationAccount.setBalance(destinationAccount.getBalance() + amount);

        accountService.save(sourceAccount);
        accountService.save(destinationAccount);

        return new ResponseEntity<>("Successful transaction", HttpStatus.CREATED);

    }


}
