package com.example.freshers.Assignment.Controller;

import com.example.freshers.Assignment.Producer.BankAccountProducer;
import com.example.freshers.Assignment.models.BankAccount;
import com.example.freshers.Assignment.services.BankAccountServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;


@RestController
@RequestMapping("/account")
public class BankAccountController {
    private BankAccountServices bankAccountServices;
    private BankAccountProducer bankAccountProducer;

    @Autowired
    public BankAccountController(BankAccountServices bankAccountServices,BankAccountProducer bankAccountProducer){
        this.bankAccountServices = bankAccountServices;
        this.bankAccountProducer = bankAccountProducer;
    }

    @RequestMapping(value = "/_create",method = RequestMethod.POST)
    public ResponseEntity<String> createBankAccount(@RequestBody BankAccount bankAccount) throws Exception {
        bankAccountServices.createAccount(bankAccount);
        return new ResponseEntity<String>("Created Bank Account with account No.: " + bankAccount.getAccountNumber(),
        HttpStatus.CREATED);
    }

    @RequestMapping(value = "/_debit/{accountNumber}",method = RequestMethod.PUT)
    public ResponseEntity<String> debitAmount(@PathVariable String accountNumber, @RequestBody BigDecimal debitAmount){
        bankAccountProducer.debitAmountByKafka(accountNumber,debitAmount);
        return new ResponseEntity<>("Successfully debited the amount from bank account with accountNumber: " + accountNumber,HttpStatus.ACCEPTED);
    }

    @RequestMapping(value = "/_credit/{accountNumber}",method = RequestMethod.PUT)
    public ResponseEntity<String> creditAmount(@PathVariable String accountNumber, @RequestBody BigDecimal creditAmount){
        bankAccountProducer.creditAmountByKafka(accountNumber,creditAmount);
        return new ResponseEntity<>("Successfully credited the amount from bank account with accountNumber: " + accountNumber,HttpStatus.ACCEPTED);
    }
}
