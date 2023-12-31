package com.mindhub.homebanking.dtos;

public class LoanApplicationDTO {

    private long loanId;
    private double amount;
    private int payments;
    private String toAccountNumber;

    public LoanApplicationDTO(long loanTypeId, double amount, int payments, String accountToNumber) {
        this.loanId = loanTypeId;
        this.amount = amount;
        this.payments = payments;
        this.toAccountNumber = accountToNumber;
    }

    public long getLoanId() {
        return loanId;
    }

    public void setLoanId(long loanId) {
        this.loanId = loanId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getPayments() {
        return payments;
    }

    public void setPayments(int payments) {
        this.payments = payments;
    }

    public String getToAccountNumber() {
        return toAccountNumber;
    }

    public void setToAccountNumber(String toAccountNumber) {
        this.toAccountNumber = toAccountNumber;
    }
}
