package com.example.freshers.Assignment.Producer;

import com.example.freshers.Assignment.models.AccountTransactions;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
public class BankAccountProducer {
    private final KafkaTemplate<String, AccountTransactions> kafkaTemplate;

    @Value("${kafka.topic.bank-account-debit}")
    private String bankAccountDebitTopic;

    @Value("${kafka.topic.bank-account-credit}")
    private String bankAccountCreditTopic;


    @Autowired
    public BankAccountProducer(KafkaTemplate<String,AccountTransactions> kafkaTemplate){
        this.kafkaTemplate = kafkaTemplate;
    }

    public void debitAmountByKafka(String accountNumber, BigDecimal debitAmount,String status){
        kafkaTemplate.send(bankAccountDebitTopic,new AccountTransactions(accountNumber,debitAmount,status));
    }

    public void creditAmountByKafka(String accountNumber,BigDecimal creditAmount,String status){
        kafkaTemplate.send(bankAccountCreditTopic,new AccountTransactions(accountNumber,creditAmount,status));
    }
}
