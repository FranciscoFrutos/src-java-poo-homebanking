package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.CardColor;
import com.mindhub.homebanking.models.CardType;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.CardRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.services.CardService;
import com.mindhub.homebanking.services.ClientService;
import com.mindhub.homebanking.utils.CardUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

import static com.mindhub.homebanking.utils.CardUtils.getRandomNumber;

@RestController
@RequestMapping("/api")
public class CardController {

    @Autowired
    private CardService cardService;

    @Autowired
    private ClientService clientService;

    @PostMapping("/clients/current/cards")
    public ResponseEntity<Object> createCard(
            Authentication authentication,
            @RequestParam CardType cardType, @RequestParam CardColor cardColor){
        String clientEmail = authentication.getName();
        Client client = clientService.findByEmail(clientEmail);

        if (cardType.equals(CardType.CREDIT) && client.getCards().stream().filter(card -> card.getType().equals(CardType.CREDIT)).count() >= 3){
            return new ResponseEntity<>("Maximum number of credit cards reached", HttpStatus.FORBIDDEN );
        }
        if (cardType.equals(CardType.DEBIT) && client.getCards().stream().filter(card -> card.getType().equals(CardType.DEBIT)).count() >= 3){
            return new ResponseEntity<>("Maximum number of credit cards reached", HttpStatus.FORBIDDEN );
        }

        Card card = new Card(
                client.getFirstName() + " " + client.getLastName(),
                cardType, cardColor, CardUtils.getCardNumber(), CardUtils.getRandomNumber(100, 999),
                LocalDate.now(), LocalDate.now().plusYears(5));

        client.addCard(card);

        cardService.save(card);

        return new ResponseEntity<>("Card created successfully", HttpStatus.CREATED);

    }





}
