package com.example.freshers.Assignment.Mapper;

import com.example.freshers.Assignment.models.Address;
import com.example.freshers.Assignment.models.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class UserRowMapper implements RowMapper<User> {

    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        User user = new User();
        user.setId(UUID.fromString(rs.getString("id")));
        user.setName(rs.getString("name"));
        user.setGender(rs.getString("gender"));
        user.setMobileNumber(rs.getString("mobileNumber"));
        String addressJson = rs.getString("address");
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            Address address = objectMapper.readValue(addressJson, Address.class);
            user.setAddress(address);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error parsing address JSON", e);
        }
        user.setIsActive(rs.getString("isActive"));
        user.setCreatedTime(rs.getLong("createdTime"));
        return user;
    }
}