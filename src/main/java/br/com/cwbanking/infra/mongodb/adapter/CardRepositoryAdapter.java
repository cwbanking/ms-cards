package br.com.cwbanking.infra.mongodb.adapter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.cwbanking.domain.model.Card;
import br.com.cwbanking.domain.repository.CardRepository;
import br.com.cwbanking.infra.mongodb.collection.CardCollection;
import br.com.cwbanking.infra.mongodb.repository.MongoDBCardRepository;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class CardRepositoryAdapter implements CardRepository {

	@Autowired
	private MongoDBCardRepository repository;

	@Override
	public Mono<Card> register(final Card newCard) {
		
		CardCollection cardCollection = new CardCollection(newCard);
		
		return repository.save(cardCollection).flatMap(collection -> {
			
			log.info("[CARD_DATABASE] New card registration: {}", collection);
			
			return Mono.just(newCard);
		});
	}
}
