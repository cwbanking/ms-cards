package br.com.cwbanking.infra.rest.adapter;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.cwbanking.domain.service.CardService;
import br.com.cwbanking.infra.rest.model.GetCardResponse;
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
	public Mono<ResponseEntity<?>> post(@Valid @RequestBody PostCardRequest request) {

		log.info("[HTTP_POST] /cards request body: {}", request);

		return cardService.requestIssuance(request.getAsModel()).flatMap(card -> {

			PostCardResponse response = new PostCardResponse(card);

			log.info("[HTTP_POST] /cards response body: {}", response);

			EntityModel<PostCardResponse> responseResource = EntityModel.of(response,
					linkTo(methodOn(CardController.class).findOne(response.getId())).withSelfRel());

			try {

				return Mono.just(ResponseEntity
						.created(new URI(responseResource.getRequiredLink(IanaLinkRelations.SELF).getHref()))
						.body(responseResource));

			} catch (URISyntaxException e) {

				log.error("[HTTP_POST] /cards error: {}", e.getLocalizedMessage());

				return Mono.just(ResponseEntity.badRequest().body("Unable to create card " + request.toString()));
			}
		});
	}

	@GetMapping(path = "/{id}", produces = APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public Mono<ResponseEntity<?>> findOne(@PathVariable UUID id) {

		return cardService.findById(id).flatMap(card -> {

			GetCardResponse response = new GetCardResponse(card);

			EntityModel<GetCardResponse> responseResource = EntityModel.of(response,
					linkTo(methodOn(CardController.class).findOne(response.getId())).withSelfRel());
			
			return Mono.just(ResponseEntity.ok(responseResource));
		});
	}
}
