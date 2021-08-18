package com.example.wordplay.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.wordplay.entity.User;
import com.example.wordplay.entity.WordEntity;

@Repository
public interface UserRepository extends MongoRepository<User, String>{

	@Query("{'userName' : ?0}")
	public WordEntity findByUserName(String userName);
	
}
