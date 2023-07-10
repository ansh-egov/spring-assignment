package com.example.freshers.Assignment.services;

import com.example.freshers.Assignment.Mapper.UserRowMapper;
import com.example.freshers.Assignment.Utils.UserCreationException;
import com.example.freshers.Assignment.models.Address;
import com.example.freshers.Assignment.models.Coordinates;
import com.example.freshers.Assignment.models.User;
import com.example.freshers.Assignment.models.UserSearchCriteria;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.sql.PreparedStatement;
import java.time.Instant;
import java.util.*;

@Service
public class UserServices {
    JdbcTemplate jdbcTemplate;
    RestTemplate restTemplate;

    @Autowired
    public UserServices(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
        this.restTemplate = new RestTemplate();
    }
    @PostConstruct
    public void createTable() {
        jdbcTemplate.execute(
                "CREATE TABLE IF NOT EXISTS my_users (" +
                        "id UUID DEFAULT uuid_generate_v4(), " +
                        "name VARCHAR(255), " +
                        "gender VARCHAR(255), " +
                        "mobileNumber VARCHAR(255), " +
                        "address JSON, " +
                        "isActive VARCHAR(255), " +
                        "createdTime BIGINT " +
                        ") PARTITION BY RANGE (isActive);"
        );
        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS users_active PARTITION OF my_users\n" +
                "  FOR VALUES FROM ('active') TO ('inactive');");
        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS users_inactive PARTITION OF my_users\n" +
                "  FOR VALUES FROM ('inactive') TO (MAXVALUE);");
    }


    public List<User> getAllUsers(){
        String query = "SELECT * FROM MY_USERS";
        return jdbcTemplate.query(query, new UserRowMapper());
    }

    public boolean getUserByNameAndMobileNumber(String name, String mobileNumber) {
        String sql = "SELECT COUNT(*) FROM my_users WHERE name = ? AND mobileNumber = ?";
        Integer x = jdbcTemplate.queryForObject(sql,Integer.class,name,mobileNumber);
        return x == 0;
    }

    public void createUsers(User user) throws UserCreationException {
        String sql = "INSERT INTO my_users (name, gender, mobileNumber, address, isActive, createdTime) VALUES (?, ?, ?, ?::json, ?, ?)";
        Long currentTime = Instant.now().getEpochSecond();
        try {
            if(getUserByNameAndMobileNumber(user.getName(),user.getMobileNumber())){
                User createdUser = createUser(user, sql, currentTime);
            }else{
                throw new IllegalArgumentException("User with the same name and mobile number already exists");
            }

        } catch (DataIntegrityViolationException e) {
            throw  new DataIntegrityViolationException(e.toString());
        }
    }

    private User createUser(User user, String sql, Long currentTime) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://random-data-api.com/api/v2/users?size=1";
        ResponseEntity<Map> response = restTemplate.getForEntity(url, Map.class);
        Map<String, Object> data = response.getBody();
        Map<String, Object> addressData = (Map<String, Object>) data.get("address");

//        System.out.println(addressData.toString());
        Address address = new Address();
        address.setCity((String) addressData.get("city"));
        address.setStreetName((String) addressData.get("street_name"));
        address.setStreetAddress((String) addressData.get("street_address"));
        address.setZipCode((String) addressData.get("zip_code"));
        address.setState((String) addressData.get("state"));
        address.setCountry((String) addressData.get("country"));

        Map<String, Object> coordinatesData = (Map<String, Object>) addressData.get("coordinates");
        Coordinates coordinates = new Coordinates();
        coordinates.setLat((Double) coordinatesData.get("lat"));
        coordinates.setLng((Double) coordinatesData.get("lng"));
        address.setCoordinates(coordinates);

        user.setAddress(address);
        String addressJson;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            addressJson = objectMapper.writeValueAsString(user.getAddress());
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error serializing address object to JSON", e);
        }

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, user.getName());
            ps.setString(2, user.getGender());
            ps.setString(3, user.getMobileNumber());
            ps.setString(4, addressJson);
            ps.setString(5, user.getIsActive());
            ps.setLong(6, currentTime);
            return ps;
        }, keyHolder);

        user.setId(UUID.randomUUID());
        user.setCreatedTime(currentTime);

        return user;
    }



    public List<User> searchUser(UserSearchCriteria userSearchCriteria){
        StringBuilder query = new StringBuilder("SELECT * FROM MY_USERS WHERE");
        List<Object> params = new ArrayList<>();

        if(userSearchCriteria.getId() != null){
            query.append(" ID=?");
            params.add(userSearchCriteria.getId());
        }else if(userSearchCriteria.getMobileNumber() != null){
            query.append(" MOBILENUMBER=?");
            params.add(userSearchCriteria.getMobileNumber());
        }
        return jdbcTemplate.query(query.toString(),new UserRowMapper(),params.toArray());
    }

    public void updateUser(User user){
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
            sql.append(" mobileNumber = ?,");
            params.add(user.getMobileNumber());
        }


        if (user.getIsActive() != null) {
            sql.append(" isActive = ?,");
            params.add(user.getIsActive());
        }
        // Remove the trailing comma
        sql.deleteCharAt(sql.length() - 1);

        sql.append(" WHERE id = ?");
        params.add(user.getId());

        jdbcTemplate.update(sql.toString(), params.toArray());
    }

    public void deleteUser(UUID id) {
        String sql = "DELETE FROM MY_USERS WHERE ID = ?";
        jdbcTemplate.update(sql, id);
    }
}
