package com.mindhub.homebanking;

import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api")
public class AppController {
    @Autowired
    private ClientRepository clientRepository;

    @RequestMapping("/clients/{code}")
    public ClientDTO getClient(@PathVariable Long code){
        Optional<Client> client = clientRepository.findById(code);

        return client.map(ClientDTO::new).orElse(null);
    }



}
