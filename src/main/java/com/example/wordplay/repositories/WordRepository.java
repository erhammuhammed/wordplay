package com.example.wordplay.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.wordplay.entity.WordEntity;

@Repository
public interface WordRepository extends MongoRepository<WordEntity, String> {
	@Query("{'title' : ?0}")
	public WordEntity findByTitle(String title);
	
	@Query("{'expiry' : ?0 }")
	public WordEntity findByExpiry(Long expiry);

	@Query("{ 'expiry': {$gt : ?0} }")
	public List<WordEntity> findFutureWords(Long expiry);
}
