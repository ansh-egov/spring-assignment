package com.example.freshers.Assignment.Producer;

import com.example.freshers.Assignment.models.AccountTransactions;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
public class BankAccountProducer {
    private final KafkaTemplate<String, AccountTransactions> kafkaTemplate;

    @Autowired
    public BankAccountProducer(KafkaTemplate<String,AccountTransactions> kafkaTemplate){
        this.kafkaTemplate = kafkaTemplate;
    }

    public void debitAmountByKafka(String accountNumber, BigDecimal debitAmount){
        kafkaTemplate.send("bank-account-debit",new AccountTransactions(accountNumber,debitAmount));
    }

    public void creditAmountByKafka(String accountNumber,BigDecimal creditAmount){
        kafkaTemplate.send("bank-account-credit",new AccountTransactions(accountNumber,creditAmount));
    }
}
