package br.com.cwbanking.infra.mongodb.repository;

import java.util.UUID;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import br.com.cwbanking.infra.mongodb.collection.CardholderCollection;
import reactor.core.publisher.Mono;

public interface MongoDBCardholderRepository extends ReactiveCrudRepository<CardholderCollection, UUID> {
	
	Mono<CardholderCollection> findFirstByDocument(String document);
}
