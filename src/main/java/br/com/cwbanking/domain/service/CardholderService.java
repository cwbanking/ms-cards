package br.com.cwbanking.domain.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.cwbanking.domain.model.Cardholder;
import br.com.cwbanking.domain.repository.CardholderRepository;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class CardholderService {
	
	@Autowired
	private CardholderRepository cardholderRepository;

	public Mono<Cardholder> register(final Cardholder cardholder) {
		
		log.info("[CARDHOLDER_DOMAIN] Registering a new cardholder...");
		
		cardholder.setId(UUID.randomUUID());
		
		return cardholderRepository.register(cardholder);
	}
	
	public Mono<Cardholder> update(final Cardholder cardholder) {
		
		log.info("[CARDHOLDER_DOMAIN] Updating a cardholder registration...");
		
		return cardholderRepository.update(cardholder);
	}

	public Mono<Cardholder> findByDocument(String document) {
		
		log.info("[CARDHOLDER_DOMAIN] Finding the cardholder by the document...");
		
		return cardholderRepository.findByDocument(document);
	}
}
