package com.example.Instagramapp.controller;

import com.example.Instagramapp.dao.UserRepository;
import com.example.Instagramapp.model.Post;
import com.example.Instagramapp.service.PostService;
import com.example.Instagramapp.service.UserService;
import org.json.JSONArray;
import com.example.Instagramapp.model.User;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;

@RestController
@RequestMapping("/app/v1")
public class PostController {
    @Autowired
    UserRepository userRepository;

    @Autowired
    PostService postService;
    @PostMapping("/post")
    public ResponseEntity<String> savePost(@RequestBody String postRequest) {
        Post post =setPost(postRequest);
        int postId =postService.savePost(post);
        return new ResponseEntity<String>(String.valueOf(postId), HttpStatus.CREATED);
    }
    @GetMapping("/post")
    public ResponseEntity<String> getPost(@RequestParam Integer userId ,@RequestParam String postId){
        JSONArray postArr=postService.getPost(Integer.valueOf(userId),postId);
        return new ResponseEntity<String>(postArr.toString(), HttpStatus.OK);
    }
    @PutMapping("/post/{postId}")
    public ResponseEntity<String> updatePost(@PathVariable String postId, @RequestBody String postRequest){
        Post post = setPost(postRequest);
        postService.updatePost(postId, post);
        return  new ResponseEntity<>("Post updated", HttpStatus.OK);
    }

    private Post setPost(String postRequest) {
        JSONObject jsonObject=new JSONObject(postRequest);
        User user=null;
      int userId=  jsonObject.getInt("userId");
      if(userRepository.findById(userId).isPresent()){
          user=userRepository.findById(userId).get();
      }
      else {
          return null;
      }
        Post post = new Post();
      post.setUser(user);
      post.setPostData(jsonObject.getString("post_data"));
        Timestamp createdTime=new Timestamp(System.currentTimeMillis());
      post.setCreatedDate(createdTime);
      return post;

    }

}