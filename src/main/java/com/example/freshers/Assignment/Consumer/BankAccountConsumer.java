package com.example.freshers.Assignment.Consumer;

import com.example.freshers.Assignment.models.AccountTransactions;
import com.example.freshers.Assignment.models.BankAccount;
import com.example.freshers.Assignment.services.BankAccountServices;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class BankAccountConsumer {
    ObjectMapper objectMapper;
    BankAccountServices bankAccountServices;

    @Autowired
    public BankAccountConsumer(ObjectMapper objectMapper, BankAccountServices bankAccountServices) {
        this.objectMapper = objectMapper;
        this.bankAccountServices = bankAccountServices;
    }

    @KafkaListener(topics = "bank-account-debit")
    public void debitAmount(String message) throws JsonProcessingException {
        AccountTransactions accountTransactions = objectMapper.readValue(message,AccountTransactions.class);
        BankAccount bankAccount = bankAccountServices.findByAccountNumber(accountTransactions.getAccountNumber());
        BigDecimal currentBalance = bankAccount.getBalance();
        BigDecimal amountDebit = accountTransactions.getAmount();
        BigDecimal amount = currentBalance.subtract(amountDebit);
        bankAccount.setBalance(amount);
        bankAccountServices.update(bankAccount);
    }

    @KafkaListener(topics = "bank-account-credit")
    public void creditAmount(String message) throws JsonProcessingException {
        AccountTransactions accountTransactions = objectMapper.readValue(message,AccountTransactions.class);
        BankAccount bankAccount = bankAccountServices.findByAccountNumber(accountTransactions.getAccountNumber());
        BigDecimal currentBalance = bankAccount.getBalance();
        BigDecimal amountCredit = accountTransactions.getAmount();
        BigDecimal amount = currentBalance.add(amountCredit);
        bankAccount.setBalance(amount);
        bankAccountServices.update(bankAccount);
    }
}
