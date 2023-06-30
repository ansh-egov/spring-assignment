package com.example.freshers.Assignment.Controller;

import com.example.freshers.Assignment.models.User;
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

    @Autowired
    public UserController(UserServices userServices){
        this.userServices = userServices;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public List<User> getAllUsers(){
        return userServices.getAllUsers();
    }

    @RequestMapping(value = "/_create",method = RequestMethod.POST)
    public ResponseEntity<User> createUser(@RequestBody User user) throws IOException{
        try {
            User createdUser = userServices.createUser(user);
            return new ResponseEntity<User>(createdUser, HttpStatus.CREATED);
        }catch (Exception e){
            System.out.println("Cannot create user: " + e.toString());
            return null;
        }
    }

    @RequestMapping(value = "/_search", method = RequestMethod.GET)
    public ResponseEntity<User> searchUser(@RequestBody UserSearchCriteria userSearchCriteria){
        try{
            User user;

            if(userSearchCriteria.getId() != null){
                user = userServices.searchUserById(userSearchCriteria.getId());
            }else{
                user = userServices.searchUserByMobileNumber(userSearchCriteria.getMobileNumber());
            }
            if(user == null){
                System.out.println("User not found");
                return null;
            }
            System.out.println(user);
            return new ResponseEntity<User>(user,HttpStatus.ACCEPTED);
        }catch (Exception e){
            System.out.println("Error in searching the user: "+ e.toString());
            return null;
        }
    }

    @RequestMapping(value = "/_update", method = RequestMethod.PATCH)
    public ResponseEntity<User> updateUser(@RequestBody User user){
        try{
            User isUser = userServices.searchUserById(user.getId());

            if(isUser == null){
                System.out.println("User not found");
                return null;
            }
            User user1 = userServices.updateUser(user);
            return new ResponseEntity<User>(user1,HttpStatus.ACCEPTED);
        }catch (Exception e){
            System.out.println("Cannot update user: " + e.toString());
            return new ResponseEntity<User>(new User(),HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/_delete",method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteUser(@RequestBody User user){
        try{
            User isUser = userServices.searchUserById(user.getId());

            if(isUser == null){

                return new ResponseEntity<>("User not found",HttpStatus.BAD_REQUEST);
            }
            userServices.deleteUser(user.getId());
            return new ResponseEntity<>("Deleted",HttpStatus.ACCEPTED);
        }catch (Exception e){
            return new ResponseEntity<>("Cannot delete user: " + e.toString(),HttpStatus.BAD_REQUEST);
        }
    }
}
