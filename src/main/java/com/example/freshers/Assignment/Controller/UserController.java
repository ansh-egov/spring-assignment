package com.example.freshers.Assignment.Controller;

import com.example.freshers.Assignment.Consumer.KafkaConsumerService;
import com.example.freshers.Assignment.Producer.KafkaProducerService;
import com.example.freshers.Assignment.models.User;
import com.example.freshers.Assignment.models.UserList;
import com.example.freshers.Assignment.models.UserSearchCriteria;
import com.example.freshers.Assignment.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserServices userServices;
    private final KafkaProducerService kafkaProducerService;
    private final KafkaConsumerService kafkaConsumerService;

    @Autowired
    public UserController(UserServices userServices, KafkaProducerService kafkaProducerService, KafkaConsumerService kafkaConsumerService){
        this.userServices = userServices;
        this.kafkaProducerService = kafkaProducerService;
        this.kafkaConsumerService = kafkaConsumerService;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public List<User> getAllUsers(){
        return userServices.getAllUsers();
    }

    @RequestMapping(value = "/_create",method = RequestMethod.POST)
    public ResponseEntity<String> createUser(@RequestBody UserList users) throws IOException{
        try {
            for (User user:users.getUsers()){
                kafkaProducerService.createUserByKafka(user);
            }
            return new ResponseEntity<String>("Users created Successfully and Pushed to the kafka Topic",HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<String>("Cannot create users: "+ e.toString(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/_search", method = RequestMethod.GET)
    public ResponseEntity<List<User>> searchUser(@RequestBody UserSearchCriteria userSearchCriteria){
        try{
            List<User> users = userServices.searchUser(userSearchCriteria);
            return new ResponseEntity<List<User>>(users,HttpStatus.ACCEPTED);
        }catch (Exception e){
            System.out.println("Error in searching the user: "+ e.toString());
            return null;
        }
    }

    @RequestMapping(value = "/_update", method = RequestMethod.PATCH)
    public ResponseEntity<String> updateUser(@RequestBody UserList users){
        try{
            for (User user: users.getUsers()){
                if(user.getId() == null){
                    List<User> user1 = userServices.searchUser(new UserSearchCriteria(user.getId(),user.getMobileNumber()));
                    user.setId(user1.get(0).getId());
                    kafkaProducerService.updateUserByKafka(user);
                }else {
                    kafkaProducerService.updateUserByKafka(user);
                }
            }
            return new ResponseEntity<String>("Users updated successfully to: " + users.toString(),HttpStatus.ACCEPTED);
        }catch (Exception e){
            return new ResponseEntity<String>("Cannot update user: " + e.toString(),HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/_delete",method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteUser(@RequestBody User user){
        try{
            List<User> users = userServices.searchUser(new UserSearchCriteria(user.getId(),user.getMobileNumber()));

            if(users.isEmpty()){
                return new ResponseEntity<>("User not found",HttpStatus.BAD_REQUEST);
            }
            userServices.deleteUser(users.get(0).getId());
            return new ResponseEntity<>("Deleted",HttpStatus.ACCEPTED);
        }catch (Exception e){
            return new ResponseEntity<>("Cannot delete user: " + e.toString(),HttpStatus.BAD_REQUEST);
        }
    }
}
