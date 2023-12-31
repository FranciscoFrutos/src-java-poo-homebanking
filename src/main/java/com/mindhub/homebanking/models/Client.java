package com.mindhub.homebanking.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


import static java.util.stream.Collectors.toList;

@Entity
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;
    private String firstName;
    private String lastName;
    private String email;

    private String password;
    @OneToMany(mappedBy = "owner", fetch = FetchType.EAGER)
    private Set<Account> accounts = new HashSet<>();

    @OneToMany(mappedBy = "client", fetch = FetchType.EAGER)
    private Set<ClientLoan> loans;



    @OneToMany(mappedBy = "holder", fetch = FetchType.EAGER)
    private Set<Card> cards = new HashSet<>();


    public Client(){

    }
    public Client(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    public List<Loan> getLoans() {
        return loans.stream().map(ClientLoan::getLoan).collect(toList());
    }

    public Set<ClientLoan> getClientLoans(){
        return this.loans;
    }

    public Set<Card> getCards() {
        return cards;
    }

    public void setCards(Set<Card> cards) {
        this.cards = cards;
    }

    public void setLoans(Set<ClientLoan> loans) {
        this.loans = loans;
    }

    public void addClientLoan(ClientLoan clientLoan){
        clientLoan.setClient(this);
        this.loans.add(clientLoan);
    }

    public void addCard(Card card){
        card.setHolder(this);
        this.cards.add(card);
    }
    public void setAccounts(Set<Account> accounts) {
        this.accounts = accounts;
    }

    public Set<Account> getAccounts() {
        return accounts;
    }

    public void addAccount(Account account){
        account.setClient(this);
        this.accounts.add(account);
    }

    public long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
