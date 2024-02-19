package com.miapi.monitor.controller;


import com.miapi.monitor.entities.UserInfo;
import com.miapi.monitor.repositories.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserInfoRepository userInfoRepository;

    // CREATE
    @PostMapping("/")
    public ResponseEntity<UserInfo> createUser(@RequestBody UserInfo userInfo) {
        UserInfo savedUser = userInfoRepository.save(userInfo);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

    // READ (all users)
    @GetMapping("/")
    public ResponseEntity<List<UserInfo>> getAllUsers() {
        List<UserInfo> users = userInfoRepository.findAll();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    // READ (user by id)
    @GetMapping("/{id}")
    public ResponseEntity<UserInfo> getUserById(@PathVariable Long id) {
        Optional<UserInfo> optionalUser = userInfoRepository.findById(id);
        return optionalUser.map(user -> new ResponseEntity<>(user, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<UserInfo> updateUser(@PathVariable Long id, @RequestBody UserInfo updatedUserInfo) {
        Optional<UserInfo> optionalUser = userInfoRepository.findById(id);
        if (optionalUser.isPresent()) {
            updatedUserInfo.setId(id); // Ensure the correct ID is set
            UserInfo savedUser = userInfoRepository.save(updatedUserInfo);
            return new ResponseEntity<>(savedUser, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        Optional<UserInfo> optionalUser = userInfoRepository.findById(id);
        if (optionalUser.isPresent()) {
            userInfoRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
