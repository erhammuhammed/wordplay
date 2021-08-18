package com.example.wordplay.services;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.wordplay.entity.User;
import com.example.wordplay.repositories.UserRepository;

@Service
public class UserServices {
	
	private static final Logger logger = LoggerFactory.getLogger(UserServices.class);

	@Autowired
	UserRepository userRepository;
	
	public List<User> getAll(){
		logger.info("fetch all user entiites");
		List<User> userEntities = userRepository.findAll();
		return userEntities;
	}
	
	public User getById(String id) {
		Optional<User> option = userRepository.findById(id);
		if(option!=null) {
			 return option.get();
		} else {
			return null;
		}
	}
	
	public User save(User user) {
		 return userRepository.save(user);
	}
	
	public void deleteUser(String id) {
		Optional<User> option = userRepository.findById(id);
		if(option!=null) {
			 User user = option.get();
			 userRepository.delete(user);
		}
	}
	
}
