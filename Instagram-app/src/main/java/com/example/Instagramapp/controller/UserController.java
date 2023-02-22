package com.example.Instagramapp.controller;

import com.example.Instagramapp.service.UserService;
//import org.apache.catalina.User;
import com.example.Instagramapp.model.User;
import jakarta.annotation.Nullable;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/app/v1")
public class UserController {
    @Autowired
    UserService userService;
    @PostMapping("/add-user")
    public ResponseEntity<String> saveUser(@RequestBody String userData){
        User user = setUser(userData);

        int userId = userService.saveUser(user);
        return new ResponseEntity("user saved with id- " +userId, HttpStatus.CREATED);

    }
    @GetMapping("find-user")
    public ResponseEntity<String>  getUser(@Nullable @RequestParam  String userId){
       JSONArray userDetails  = userService.getUser(userId);
       return new ResponseEntity(userDetails.toString(),HttpStatus.OK);
    }

    @PutMapping("/user/{userId}")
    public  ResponseEntity<String> updateUser(@PathVariable String userId,@RequestBody String userData){
        User user = setUser(userData);
        userService.updateUser(userId,user);
        return new ResponseEntity("user updated",HttpStatus.OK);
    }
      private   User setUser(String userData){
        JSONObject jsonObject=new JSONObject(userData);
        User user=new User();
        user.setAge(jsonObject.getInt("age"));
        user.setEmail(jsonObject.getString("email"));
        user.setFirstName(jsonObject.getString("firstName"));
        user.setLastName(jsonObject.getString("lastName"));
        user.setPhoneNumber(jsonObject.getString("phoneNumber"));
        return user;
    }
}
