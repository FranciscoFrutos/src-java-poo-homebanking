package com.mindhub.homebanking.services.implement;

import com.mindhub.homebanking.dtos.LoanDTO;
import com.mindhub.homebanking.models.Loan;
import com.mindhub.homebanking.repositories.LoanRepository;
import com.mindhub.homebanking.services.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class LoanServiceImplement implements LoanService {

    @Autowired
    private LoanRepository loanRepository;
    @Override
    public List<LoanDTO> getAllLoans() {
        return loanRepository.findAll().stream().map(loan -> new LoanDTO(loan.getId(), loan.getName(), loan.getMaxAmount(), loan.getPayments())).collect(toList());
    }

    @Override
    public Loan findLoanById(long id) {
        return loanRepository.findById(id).orElse(null);
    }
}
