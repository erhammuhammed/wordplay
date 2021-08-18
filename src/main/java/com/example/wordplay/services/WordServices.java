package com.example.wordplay.services;

import java.time.Instant;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.wordplay.entity.WordEntity;
import com.example.wordplay.repositories.WordRepository;

@Service
public class WordServices {

	private static final Logger logger = LoggerFactory.getLogger(WordServices.class);
	
	@Autowired
	WordRepository wordRepository;	
	
	public WordEntity getByTitle(String title) {
		logger.info("fetch definition for {}", title);
		WordEntity result = new WordEntity();
		result = wordRepository.findByTitle(title);
		return result;
	}
	
	public WordEntity save(WordEntity entity) {
		logger.info("save word {}", entity.getTitle());
		scheduleNewWord(entity);
		WordEntity wordEntity = wordRepository.save(entity);
		return wordEntity;
	}
	
	public WordEntity getWordOfTheDay() {
		Instant now = Instant.now();
		logger.info("getting word of the day {}", now.getEpochSecond());
		Long expiry = now.plus( 0 , ChronoUnit.DAYS ).atZone(ZoneId.of("Asia/Kolkata")).truncatedTo(ChronoUnit.DAYS).toInstant().getEpochSecond();
		WordEntity wordEntity = wordRepository.findByExpiry(expiry);
		return wordEntity;
	}
	
	public void deleteByTitle(String title) {
		logger.info("delete word {}", title);
		WordEntity wordEntity = wordRepository.findByTitle(title);
		rescheduleWords(wordEntity);
		wordRepository.delete(wordEntity);
	}
	
	public List<WordEntity> getAll(){
		logger.info("fetch all word entiites");
		List<WordEntity> wordEntities = wordRepository.findAll();
		return wordEntities;
	}
	
	//Assign an expiry time to newly created or edited words
	public void scheduleNewWord(WordEntity wordEntity) {
		Instant now = Instant.now();
		Long expiry = now.plus( 0 , ChronoUnit.DAYS ).atZone(ZoneId.of("Asia/Kolkata")).truncatedTo(ChronoUnit.DAYS).toInstant().getEpochSecond();
		WordEntity todaysWord = getWordOfTheDay();
		//If there's no existing word for today, saves this one as today's word.
		if(todaysWord==null) {
			wordEntity.setExpiry(expiry);
		} else {
			//Checks if entity is being created
			if(wordEntity.getId()==null) {
				//Gets all words sorted by expiry, latest being the first.
				List<WordEntity> listEntity = wordRepository.findAll(Sort.by(Sort.Direction.DESC, "expiry"));
				if(listEntity!=null && listEntity.size()>0) {
					WordEntity latestWord = listEntity.get(0);
					wordEntity.setExpiry(latestWord.getExpiry()+86400l);
				}
			}
		}
	}
	
	//Re-adjust the expiry times if one of the words is deleted
	public void rescheduleWords(WordEntity wordEntity) {
		List<WordEntity> futureWords = wordRepository.findFutureWords(wordEntity.getExpiry()); 
		if(futureWords!=null && futureWords.size()>0) {
			for(WordEntity entity: futureWords) {
				entity.setExpiry(entity.getExpiry()-86400l);		
			}
			wordRepository.saveAll(futureWords);
		}
	}
}
