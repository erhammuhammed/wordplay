package com.example.wordplay.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Document(collection="users")
public class User {

	@Id
	private String id;
	
	String userName;
	String givenName;
	String password;
}
