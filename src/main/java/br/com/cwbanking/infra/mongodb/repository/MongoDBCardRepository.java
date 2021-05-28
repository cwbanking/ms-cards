package br.com.cwbanking.infra.mongodb.repository;

import java.util.UUID;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import br.com.cwbanking.infra.mongodb.collection.CardCollection;

public interface MongoDBCardRepository extends ReactiveCrudRepository<CardCollection, UUID> {

}
