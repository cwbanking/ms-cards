package br.com.cwbanking.infra.mongodb.adapter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.cwbanking.domain.model.Cardholder;
import br.com.cwbanking.domain.repository.CardholderRepository;
import br.com.cwbanking.infra.mongodb.collection.CardholderCollection;
import br.com.cwbanking.infra.mongodb.repository.MongoDBCardholderRepository;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class CardholderRepositoryAdapter implements CardholderRepository {

	@Autowired
	private MongoDBCardholderRepository repository;

	@Override
	public Mono<Cardholder> findByDocument(String document) {

		return repository.findFirstByDocument(document).flatMap(collection -> {

			return Mono.just(collection.buildAsModel());
		});
	}
	
	@Override
	public Mono<Cardholder> register(final Cardholder newCardholder) {
		
		CardholderCollection cardholderCollection = new CardholderCollection(newCardholder);
		
		return repository.save(cardholderCollection).flatMap(collection -> {
			
			log.info("[CARDHOLDER_DATABASE] New cardholder registration: {}", collection);
			
			return Mono.just(newCardholder);
		});
	}

	@Override
	public Mono<Cardholder> update(final Cardholder cardholder) {
		
		CardholderCollection cardholderCollection = new CardholderCollection(cardholder);
		
		return repository.save(cardholderCollection).flatMap(collection -> {
			
			log.info("[CARDHOLDER_DATABASE] Updated cardholder registration: {}", collection);
			
			return Mono.just(cardholder);
		});
	}
}
