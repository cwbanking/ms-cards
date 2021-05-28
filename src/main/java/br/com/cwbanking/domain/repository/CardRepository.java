package br.com.cwbanking.domain.repository;

import br.com.cwbanking.domain.model.Card;
import reactor.core.publisher.Mono;

public interface CardRepository {

	Mono<Card> register(Card newCard);
}
