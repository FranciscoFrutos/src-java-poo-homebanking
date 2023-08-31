package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.models.TransactionType;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import java.time.LocalDate;

@RestController
@RequestMapping("/api")
public class TransactionController {

    @Autowired
    AccountRepository accountRepository;
    @Autowired
    TransactionRepository transactionRepository;
    @Autowired
    ClientRepository clientRepository;

    @Transactional
    @RequestMapping(value = "/transactions", method = RequestMethod.POST)
    public ResponseEntity<Object> createTransaction(
            Authentication authentication,
            @RequestParam double amount,
            @RequestParam String description,
            @RequestParam String fromAccountNumber,
            @RequestParam String toAccountNumber
            ){

        Account sourceAccount = accountRepository.findByNumber(fromAccountNumber);
        Account destinationAccount = accountRepository.findByNumber(toAccountNumber);
        Client client = clientRepository.findByEmail(authentication.getName());

        if (amount <= 0 || fromAccountNumber.isEmpty() || toAccountNumber.isEmpty()){
            return new ResponseEntity<>("Missing parameters.", HttpStatus.FORBIDDEN);
        }
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

        Transaction debitTransaction = new Transaction(TransactionType.DEBIT, amount, description + " " + fromAccountNumber, LocalDate.now());
        Transaction creditTransaction = new Transaction(TransactionType.CREDIT, amount, description + " " + toAccountNumber, LocalDate.now());
        transactionRepository.save(debitTransaction);
        transactionRepository.save(creditTransaction);

        sourceAccount.setBalance(sourceAccount.getBalance() - amount);
        destinationAccount.setBalance(destinationAccount.getBalance() + amount);

        accountRepository.save(sourceAccount);
        accountRepository.save(destinationAccount);

        return new ResponseEntity<>("Successful transaction", HttpStatus.CREATED);

    }


}
