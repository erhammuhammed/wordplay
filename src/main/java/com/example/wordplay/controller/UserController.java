package com.example.wordplay.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.wordplay.entity.User;
import com.example.wordplay.services.UserServices;

@RestController
@RequestMapping("/api")
public class UserController {
	
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	UserServices userService;
	
	@GetMapping("/getUser/{id}")
	private ResponseEntity<User> getUser(@PathVariable String id) {
		try {
			User user = userService.getById(id);
			return ResponseEntity.ok(user);
		} catch(Exception e) {
			logger.error("Error getting user {}", e);
			return ResponseEntity.ok(null);
		}
	}
	
	@GetMapping("/getUsers")
	private ResponseEntity<List<User>> getAllUsers(){
		try {
			List<User> listUsers = userService.getAll();
			return ResponseEntity.ok(listUsers);
		}catch(Exception e) {
			logger.error("Error getting users {}", e);
			return ResponseEntity.ok(null);
		}
	}
	
	@PostMapping(value="/saveUser")
	private String saveUser(@RequestBody User user) {
		try {
			userService.save(user);
			return "Successfully saved user "+user.getGivenName();
		} catch(Exception e) {
			logger.error("Error saving user");
			return "Error saving user "+e.getMessage();
		}
	}
	
	@PutMapping(value="/updateUser")
	private String updateUser(@RequestBody User user) {
		try {
			User entity = userService.getById(user.getId());
			entity.setGivenName(user.getGivenName());
			entity.setPassword(user.getPassword());
			entity.setGivenName(user.getGivenName());
			userService.save(user);
			return "Successfully saved user "+user.getGivenName();
		} catch(Exception e) {
			logger.error("Error saving user");
			return "Error saving user "+e.getMessage();
		}
	}
	
	@DeleteMapping("/deleteUser/{id}")
	private String deleteUser(@PathVariable String id) {
		try {
			userService.deleteUser(id);
			return "Successfully deleted user"+ id;
		}catch(Exception e) {
			logger.error("Error deleting user {}", e);
			return "Deleting user error "+ e.getMessage();
		}
	}
	
}
