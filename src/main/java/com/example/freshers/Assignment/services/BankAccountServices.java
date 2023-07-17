package com.example.freshers.Assignment.services;

import com.example.freshers.Assignment.Mapper.BankAccountRowMapper;
import com.example.freshers.Assignment.models.BankAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

@Service
public class BankAccountServices {
    JdbcTemplate jdbcTemplate;
    private Set<String> generatedNumbers;
    private Random random;

    @Autowired
    public BankAccountServices(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
        generatedNumbers = new HashSet<>();
        random = new Random();
    }

    @PostConstruct
    public void createTable(){
        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS BANK(" +
                "id UUID DEFAULT uuid_generate_v4() PRIMARY KEY," +
                "name VARCHAR(255), " +
                "accountNumber VARCHAR(255)," +
                "balance DECIMAL" +
                ");");
    }

    public String generateUniqueNumberString(int length) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int digit = random.nextInt(10);
            sb.append(digit);
        }

        String randomNumberString = sb.toString();
        while (generatedNumbers.contains(randomNumberString)) {
            sb.setLength(0);
            for (int i = 0; i < length; i++) {
                int digit = random.nextInt(10);
                sb.append(digit);
            }
            randomNumberString = sb.toString();
        }

        generatedNumbers.add(randomNumberString);
        return randomNumberString;
    }

    public BankAccount createAccount(BankAccount bankAccount) throws Exception {
        String accountNumber = generateUniqueNumberString(12);
        bankAccount.setAccountNumber(accountNumber);
        String sql = "INSERT INTO BANK (name,accountNumber, balance) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, bankAccount.getName(), accountNumber, bankAccount.getBalance());
        return bankAccount;
    }

    public BankAccount findByAccountNumber(String accountNumber){
        String sql = "SELECT * FROM BANK WHERE accountNumber = ?;";
        return jdbcTemplate.queryForObject(sql,new BankAccountRowMapper(),accountNumber);
    }

    public BankAccount update(BankAccount bankAccount){
        String sql = "UPDATE BANK SET id = ?, name = ?, accountNumber = ?, balance = ?;";
        jdbcTemplate.update(sql,bankAccount.getId(),bankAccount.getName(),bankAccount.getAccountNumber(),bankAccount.getBalance());
        return bankAccount;
    }
}
