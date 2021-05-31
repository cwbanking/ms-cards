package br.com.cwbanking.domain.repository;

import java.util.UUID;

import br.com.cwbanking.domain.model.Card;
import reactor.core.publisher.Mono;

public interface CardRepository {

	Mono<Card> register(Card newCard);
	
	Mono<Card> findById(UUID uuid);
}
