package com.mindhub.homebanking.services;

import com.mindhub.homebanking.controllers.AccountController;
import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.models.Client;

import java.util.List;

public interface ClientService {

    List<ClientDTO> getAllClients();

    Client findByEmail(String email);

    ClientDTO findDTOByEmail(String email);
    ClientDTO findById(Long id);

    void save(Client client);
}
