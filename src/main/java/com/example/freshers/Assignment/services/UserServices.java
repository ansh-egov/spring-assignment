package com.example.freshers.Assignment.services;

import com.example.freshers.Assignment.Mapper.UserRowMapper;
import com.example.freshers.Assignment.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class UserServices {
    JdbcTemplate jdbcTemplate;

    @Autowired
    public UserServices(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<User> getAllUsers(){
        String query = "SELECT * FROM MY_USERS";
        return jdbcTemplate.query(query, new UserRowMapper());
    }

    public User createUser(User user){
        String sql = "INSERT INTO my_users (name, gender, mobile_number, address) VALUES (?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[] { "id" });
            ps.setString(1, user.getName());
            ps.setString(2, user.getGender());
            ps.setString(3, user.getMobileNumber());
            ps.setString(4, user.getAddress());
            return ps;
        }, keyHolder);

        Long generatedId = Objects.requireNonNull(keyHolder.getKey()).longValue();
        user.setId(generatedId);
        return user;
    }

    public User searchUserById(Long id){
        String query = "SELECT * FROM MY_USERS WHERE ID = ?";
        return jdbcTemplate.queryForObject(query,new UserRowMapper(),id);
    }

    public User searchUserByMobileNumber(String mobileNumber){
        String query = "SELECT * FROM MY_USERS WHERE MOBILE_NUMBER= ?";
        return jdbcTemplate.queryForObject(query,new UserRowMapper(), mobileNumber);
    }

    public User updateUser(User user){
        StringBuilder sql = new StringBuilder("UPDATE my_users SET");
        List<Object> params = new ArrayList<>();

        if (user.getName() != null) {
            sql.append(" name = ?,");
            params.add(user.getName());
        }

        if (user.getGender() != null) {
            sql.append(" gender = ?,");
            params.add(user.getGender());
        }

        if (user.getMobileNumber() != null) {
            sql.append(" mobile_number = ?,");
            params.add(user.getMobileNumber());
        }

        if (user.getAddress() != null) {
            sql.append(" address = ?,");
            params.add(user.getAddress());
        }

        // Remove the trailing comma
        sql.deleteCharAt(sql.length() - 1);

        sql.append(" WHERE id = ?");
        params.add(user.getId());

        jdbcTemplate.update(sql.toString(), params.toArray());
        return user;
    }

    public void deleteUser(Long id) {
        String sql = "DELETE FROM MY_USERS WHERE ID = ?";
        jdbcTemplate.update(sql, id);
    }
}
