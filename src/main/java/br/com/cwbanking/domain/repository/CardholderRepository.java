package br.com.cwbanking.domain.repository;

import br.com.cwbanking.domain.model.Cardholder;
import reactor.core.publisher.Mono;

public interface CardholderRepository {
	
	Mono<Cardholder> register(Cardholder newCardholder);
	
	Mono<Cardholder> update(Cardholder newCardholder);
	
	Mono<Cardholder> findByDocument(String document);
}
