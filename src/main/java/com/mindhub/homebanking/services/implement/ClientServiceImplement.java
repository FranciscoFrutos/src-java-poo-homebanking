package com.mindhub.homebanking.services.implement;

import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Service
public class ClientServiceImplement implements ClientService {

    @Autowired
    ClientRepository clientRepository;

    @Override
    public List<ClientDTO> getAllClients() {
        return clientRepository.findAll().stream().map(ClientDTO::new).collect(toList());
    }



    @Override
    public Client findByEmail(String email) {
        return clientRepository.findByEmail(email);
    }

    @Override
    public ClientDTO findDTOByEmail(String email) {
        Optional<Client> client = Optional.ofNullable(findByEmail(email));
        return client.map(ClientDTO::new).orElse(null);
    }

    @Override
    public ClientDTO findById(Long id) {
        Optional<Client> client = clientRepository.findById(id);
        return client.map(ClientDTO::new).orElse(null);
    }

    @Override
    public void save(Client client) {
        clientRepository.save(client);
    }
}
