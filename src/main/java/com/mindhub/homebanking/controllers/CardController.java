package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.CardColor;
import com.mindhub.homebanking.models.CardType;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.CardRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/api")
public class CardController {

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private ClientRepository clientRepository;

    @RequestMapping(value = "/clients/current/cards", method = RequestMethod.POST)
    public ResponseEntity<Object> createCard(
            Authentication authentication,
            @RequestParam CardType cardType, @RequestParam CardColor cardColor){
        String clientEmail = authentication.getName();
        Client client = clientRepository.findByEmail(clientEmail);

        if (cardType.equals(CardType.CREDIT) && client.getCards().stream().filter(card -> card.getType().equals(CardType.CREDIT)).count() >= 3){
            return new ResponseEntity<>("Maximum number of credit cards reached", HttpStatus.FORBIDDEN );
        }
        if (cardType.equals(CardType.DEBIT) && client.getCards().stream().filter(card -> card.getType().equals(CardType.DEBIT)).count() >= 3){
            return new ResponseEntity<>("Maximum number of credit cards reached", HttpStatus.FORBIDDEN );
        }

        Card card = new Card(
                client.getFirstName() + " " + client.getLastName(),
                cardType, cardColor, getCardNumber(), getRandomNumber(100, 999),
                LocalDate.now(), LocalDate.now().plusYears(5));

        client.addCard(card);

        cardRepository.save(card);

        return new ResponseEntity<>("Card created successfully", HttpStatus.CREATED);

    }

    public int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    public  String getCardNumber(){
        StringBuilder cardNumber = new StringBuilder();
        for (int i = 0; i<4; i++){
            cardNumber.append(Integer.toString(getRandomNumber(1000, 9999)));
            cardNumber.append(" ");
        }
        return cardNumber.toString();
    }

}
