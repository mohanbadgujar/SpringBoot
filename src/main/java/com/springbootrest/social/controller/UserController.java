package com.springbootrest.social.controller;

import com.springbootrest.social.exception.ResourceNotFoundException;
import com.springbootrest.social.dao.UserDao;
import com.springbootrest.social.entities.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
public class UserController {

	    @Autowired
	    private UserDao userDao;
	
	    @GetMapping("/users")
	    public Page<User> getAllUsers(Pageable pageable) {
	        return userDao.findAll(pageable);
	    }
	    
	    @PostMapping("/users")
	    public User createUser(@Valid @RequestBody User user) {
	        return userDao.save(user);
	    }
	    
	    @PutMapping("/users/{userId}")
	    public User updateUser(@PathVariable Long userId, @Valid @RequestBody User userRequest) {
	        return userDao.findById(userId).map(user -> {
	            
	            user.setEmail(userRequest.getEmail());
	            user.setName(userRequest.getName());
	            
	            return userDao.save(user);
	        }).orElseThrow(() -> new ResourceNotFoundException("UserId " + userId + " not found"));
	    }


	    @DeleteMapping("/users/{userId}")
	    public ResponseEntity<?> deleteUser(@PathVariable Long userId) {
	        return userDao.findById(userId).map(user -> {
	        	userDao.delete(user);
	            return ResponseEntity.ok().build();
	        }).orElseThrow(() -> new ResourceNotFoundException("UserId " + userId + " not found"));
	    }
}
