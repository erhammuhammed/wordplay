package com.example.wordplay.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.example.wordplay.repositories.WordRepository;

@SpringBootApplication(scanBasePackages={"com.example"})
@EnableMongoRepositories(basePackageClasses = {WordRepository.class})
public class WordplayApplication {

	public static void main(String[] args) {
		SpringApplication.run(WordplayApplication.class, args);
	}

}
