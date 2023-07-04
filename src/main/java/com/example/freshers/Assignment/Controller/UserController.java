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
    public ResponseEntity<String> createUser(@RequestBody List<User> users) throws IOException{
        try {
            userServices.createUsers(users);
            return new ResponseEntity<String>("Users created Successfully",HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<String>("Cannot create users: "+ e.toString(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/_search", method = RequestMethod.GET)
    public ResponseEntity<List<User>> searchUser(@RequestBody UserSearchCriteria userSearchCriteria){
        try{
            List<User> users = userServices.searchUser(userSearchCriteria);
            System.out.println(users);
            return new ResponseEntity<List<User>>(users,HttpStatus.ACCEPTED);
        }catch (Exception e){
            System.out.println("Error in searching the user: "+ e.toString());
            return null;
        }
    }

    @RequestMapping(value = "/_update", method = RequestMethod.PATCH)
    public ResponseEntity<String> updateUser(@RequestBody List<User> users){
        try{
            for (User user: users){
//                System.out.println(user);
//                User isUser = userServices.searchUser(new UserSearchCriteria(user.getId(),user.getMobileNumber()));
//                if(isUser == null){
//                    System.out.println("User not found");
//                    return null;
//                }
                User user1 = userServices.updateUser(user);
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
