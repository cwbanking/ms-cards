package br.com.cwbanking.infra.rest.adapter;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.cwbanking.domain.service.CardService;
import br.com.cwbanking.infra.rest.model.PostCardRequest;
import br.com.cwbanking.infra.rest.model.PostCardResponse;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequestMapping("/cards")
public class CardController {
	
	@Autowired
	private CardService cardService;
	
	@PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	public Mono<PostCardResponse> post(@Valid @RequestBody PostCardRequest request) {
		
		log.info("[HTTP_POST] /cards request body: {}", request);
		
		return cardService.requestIssuance(request.getAsModel()).flatMap(card -> {
			
			PostCardResponse response = new PostCardResponse(card);
			
			log.info("[HTTP_POST] /cards response body: {}", response);
			
			return Mono.just(response);
		});
	}
}
