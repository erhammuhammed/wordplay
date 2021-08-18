package com.example.wordplay.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.wordplay.entity.WordEntity;
import com.example.wordplay.services.WordServices;

@RestController
@RequestMapping("/api")
public class WordController {

	private static final Logger logger = LoggerFactory.getLogger(WordController.class);
	
	@Autowired
	WordServices wordServices;
	
	@GetMapping("/getWordOfTheDay")
	private String getWordOftheDay() {
		try {
			WordEntity wordEntity = wordServices.getWordOfTheDay();
			String message = "Your word of the day is "+wordEntity.getTitle()+"\n\n"+
					"\""+wordEntity.getDefinition()+"\"";
			return message;
		} catch(Exception e) {
			logger.error("Error getting word of the day {}", e);
			return "There's no word for the day. Use /saveWord to add newWord";
		}
	}
	
	@GetMapping("/getDefByTitle/{title}")
	private String getDefByTitle(@PathVariable String title) {
		try {
			logger.info("get defnition for {}", title);
			WordEntity wordEntity = wordServices.getByTitle(title);
			String message = "Word: "+ wordEntity.getTitle()+"\nDefinition:"+wordEntity.getDefinition();
			return message;
		} catch(Exception e) {
			logger.error("getDefinition error: {}", e);
			return "Error occured:"+e.getMessage();
		}
	}
	
	@GetMapping("/getWords")
	private ResponseEntity<List> getWord() {
		try {
			List<WordEntity> listWordEntities = wordServices.getAll();
			String message = "";
			int index = 1;
			if(listWordEntities!=null && listWordEntities.size()>0) {
				for(WordEntity wordEntity: listWordEntities) {
					message += index+". "+ wordEntity.getTitle()+"\n\tDefinition: "+ wordEntity.getDefinition()+"\n";
					index++;
				}
			}
			return ResponseEntity.ok(listWordEntities);
		} catch (Exception e) {
			String message = "Error getting words "+e.getMessage();
			return ResponseEntity.ok(null);
		}
	}
	
	/* Saves WordEntity
	 * Request sample 
	 * {
	 * 		"title" : "abcd",
	 * 		"definition" : "efgh",
	 * }
	 * 
	 */
	@PostMapping(value="/saveWord")
	private String addNewWord(@RequestBody WordEntity request) {
		try {
			logger.info("add new word {}", request.getTitle());
			WordEntity entity = wordServices.save(request);
			String message = "New word saved successfully!";
			return message;
		} catch(Exception e) {
			logger.error("add new word error: {}", e);
			return "Error occured:"+e.getMessage();
		}
	}
	
	@PutMapping(value="/updateWord")
	private String updateWord(@RequestBody WordEntity request) {
		try {
			logger.info("update word :{}", request.getTitle());
			WordEntity entity = wordServices.getByTitle(request.getTitle());
			entity.setDefinition(request.getDefinition());
			wordServices.save(entity);
			String message = "Word updated successfully!";
			return message;
		} catch (Exception e){
			logger.error("Update word error {}", e);
			return "Error occured:"+e.getMessage();
		}
	}
	
	@DeleteMapping("/deleteWord/{title}")
	private String deleteByTitle(@PathVariable String title) {
		try {
			wordServices.deleteByTitle(title);
			return "Successfully deleted "+title;
		} catch(Exception e) {
			logger.error("Delete word error {}", e);
			return "Error "+ e.getMessage();
		}
	}
	
	
}
