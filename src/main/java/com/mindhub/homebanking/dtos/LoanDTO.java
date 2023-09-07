package com.mindhub.homebanking.dtos;

import javax.persistence.ElementCollection;
import java.util.ArrayList;
import java.util.List;

public class LoanDTO {

    private long id;

    private String name;
    private double maxAmount;

    private List<Integer> payments;

    public LoanDTO(long id, String name, double maxAmount, List<Integer> payments) {
        this.id = id;
        this.name = name;
        this.maxAmount = maxAmount;
        this.payments = payments;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getMaxAmount() {
        return maxAmount;
    }

    public List<Integer> getPayments() {
        return payments;
    }
}
