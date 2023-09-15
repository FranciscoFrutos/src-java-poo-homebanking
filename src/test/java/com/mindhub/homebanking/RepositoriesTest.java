package com.mindhub.homebanking;


import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

@DataJpaTest
@AutoConfigureTestDatabase(replace = NONE)
public class RepositoriesTest {

    @Autowired
    LoanRepository loanRepository;

    @Autowired
    AccountRepository accountRepository;
    @Autowired
    CardRepository cardRepository;
    @Autowired
    ClientRepository clientRepository;
    @Autowired
    TransactionRepository transactionRepository;




    @Test

    public void existLoans(){

        List<Loan> loans = loanRepository.findAll();

        assertThat(loans,is(not(empty())));

    }

    @Test

    public void existPersonalLoan(){

        List<Loan> loans = loanRepository.findAll();

        assertThat(loans, hasItem(hasProperty("name", is("Personal"))));

    }

    @Test
    public void existAccounts(){
        List<Account> accounts = accountRepository.findAll();
        assertThat(accounts, is(not(empty())));
    }

    @Test
    public void saveAccount(){
        Account account = new Account("0000", LocalDate.now(), 1000);
        Account savedAccount = accountRepository.save(account);
        assertThat(savedAccount.getId(), is(notNullValue()));
        assertThat(savedAccount.getNumber(), is("0000"));
        assertThat(savedAccount.getBalance(), is(1000d));
    }

    @Test
    public void existCards(){
        List<Card> cards = cardRepository.findAll();
        assertThat(cards, is(not(empty())));
    }

    @Test
    public void cardsHaveOwner(){
        List<Card> cards = cardRepository.findAll();

        for (Card card:cards
             ) {
            assertThat(card.getHolder(), is(notNullValue()));
        }
    }




    @Test
    public void existClients(){
        List<Client> clients = clientRepository.findAll();
        assertThat(clients, is(not(empty())));
    }

    @Test
    public void clientsHaveAccount(){
        List<Client> clients = clientRepository.findAll();
        for (Client client:clients
             ) {
            if (!client.getEmail().equals("admin@admin")){
                assertThat(client.getAccounts().size(), greaterThan(0));
            }

        }
    }

    @Test
    public void existTransactions(){
        List<Transaction> transactions = transactionRepository.findAll();
        assertThat(transactions, is(not(empty())));
    }

    @Test
    public void transactionsHaveValidType(){
        List<Transaction> transactions = transactionRepository.findAll();
        for (Transaction transaction: transactions
             ) {
            assertThat(transaction.getType(),oneOf(TransactionType.CREDIT, TransactionType.DEBIT));
        }
    }




}


