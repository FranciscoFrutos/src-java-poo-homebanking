package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.LoanApplicationDTO;
import com.mindhub.homebanking.dtos.LoanDTO;
import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.*;
import com.mindhub.homebanking.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api")
public class LoanController {

    @Autowired
    private LoanService loanService;
    @Autowired
    private ClientService clientService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private TransactionService transactionService;
    @Autowired
    private ClientLoanService clientLoanService;

    @RequestMapping("/loans")
    public List<LoanDTO> getLoans(){
        return loanService.getAllLoans();
    }

    @RequestMapping(value = "/loans", method = RequestMethod.POST)
    @Transactional
    public ResponseEntity<Object> createLoan(
            @RequestBody LoanApplicationDTO loanApplicationDTO,
            Authentication authentication
    ){

        if (loanApplicationDTO.getAmount() <= 0 || loanApplicationDTO.getPayments() == 0){
            return new ResponseEntity<>("Invalid amount or payments", HttpStatus.FORBIDDEN);
        }

        Loan loan = loanService.findLoanById(loanApplicationDTO.getLoanId());
        if (loan == null){
            return new ResponseEntity<>("Loan not found", HttpStatus.FORBIDDEN);
        }

        if (loanApplicationDTO.getAmount() > loan.getMaxAmount()){
            return new ResponseEntity<>("The requested amount exceeds the max amount", HttpStatus.FORBIDDEN);
        }
        if (!loan.getPayments().contains(loanApplicationDTO.getPayments())){
            return new ResponseEntity<>("Unavailable number of payments", HttpStatus.FORBIDDEN);
        }

        Client client = clientService.findByEmail(authentication.getName());

        Account destinationAccount = accountService.findByNumber(loanApplicationDTO.getToAccountNumber());

        if ( destinationAccount == null || destinationAccount.getClient().getId() != client.getId() ){
            return new ResponseEntity<>("Invalid destination account", HttpStatus.FORBIDDEN);
        }

        ClientLoan clientLoan = new ClientLoan(client, loan, loanApplicationDTO.getAmount() * 1.2, loanApplicationDTO.getPayments());

        clientLoanService.save(clientLoan);

        Transaction creditTransaction = new Transaction(TransactionType.CREDIT, loanApplicationDTO.getAmount(), loan.getName() + " - Loan Approved", LocalDate.now());
        transactionService.save(creditTransaction);

        destinationAccount.setBalance(destinationAccount.getBalance() + loanApplicationDTO.getAmount());

        accountService.save(destinationAccount);

        return new ResponseEntity<>("Loan approved", HttpStatus.CREATED);

    }

}
