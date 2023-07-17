package com.example.freshers.Assignment.models;

import java.math.BigDecimal;
import java.util.UUID;

public class AccountTransactions {
    private String accountNumber;
    private BigDecimal amount;

    public AccountTransactions(String accountNumber, BigDecimal amount) {
        this.accountNumber = accountNumber;
        this.amount = amount;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
