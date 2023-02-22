package com.example.Instagramapp.service;
import com.example.Instagramapp.model.User;
import com.example.Instagramapp.dao.UserRepository;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public int saveUser(User user) {
        User userObj = userRepository.save(user);
        return userObj.getUserId();
    }

    public JSONArray getUser(String userId) {
        JSONArray userArray=new JSONArray();
        if(null!=userId){
            User user =userRepository.findById(Integer.valueOf(userId)).get();
            if(null!=user){
                JSONObject userObj=setUser(user);
                userArray.put(userObj);
            }
        }
        else{
            List<User> userList =userRepository.findAll();
            for(User user:userList){
                JSONObject userObj=setUser(user);
                userArray.put(userObj);
            }
        }
        return userArray;
    }
    private JSONObject setUser(User user){
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("userId",user.getUserId());
        jsonObject.put("firstName",user.getFirstName());
        jsonObject.put("lastName",user.getLastName());
        jsonObject.put("age",user.getAge());
        jsonObject.put("email",user.getEmail());
        jsonObject.put("phoneNumber",user.getPhoneNumber());
        return jsonObject;
    }

    public void updateUser(String userId, User newUser) {
     if(userRepository.findById(Integer.valueOf(userId)).isPresent()){
         User user=userRepository.findById(Integer.valueOf(userId)).get();
         newUser.setUserId(user.getUserId());
         userRepository.save(newUser);
//         user.setFirstName(newUser.getFirstName());
//         user.setLastName(newUser.getLastName());
//         user.setAge(newUser.getAge());
//         user.setPhoneNumber(newUser.getPhoneNumber());
//         user.setEmail(newUser.getEmail());
//         userRepository.save(user);
     }
    }
}
