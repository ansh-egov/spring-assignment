package com.example.freshers.Assignment.Mapper;

import com.example.freshers.Assignment.models.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRowMapper implements RowMapper<User> {

    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        Long id = rs.getLong("id");
        String name = rs.getString("name");
        String gender = rs.getString("gender");
        String mobileNumber = rs.getString("mobile_number");
        String address = rs.getString("address");

        return new User(id, name, gender, mobileNumber, address);
    }
}