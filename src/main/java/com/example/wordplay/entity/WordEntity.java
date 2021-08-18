package com.example.wordplay.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Document(collection = "word")
public class WordEntity {
	
	@Id
	protected String id;
	
	String title;
	String definition;
	
	Long expiry;
	
	
}
