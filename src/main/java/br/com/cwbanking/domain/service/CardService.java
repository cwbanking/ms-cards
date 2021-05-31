package br.com.cwbanking.domain.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.cwbanking.domain.model.Card;
import br.com.cwbanking.domain.repository.CardRepository;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class CardService {

	@Autowired
	private CardRepository cardRepository;

	@Autowired
	private CardholderService cardholderService;

	/**
	 * Solicita a emissão de um novo cartão.
	 * 
	 * @param card
	 * @return
	 */
	public Mono<Card> requestIssuance(final Card card) {
		
		log.info("[CARD_DOMAIN] Requesting the issuance of a new card...");

		return cardholderService.findByDocument(card.getCardholder().getDocument()).switchIfEmpty(Mono.defer(() -> {

			log.info("[CARD_DOMAIN] Cardholder not found. Registering a new: {}", card.getCardholder());

			return cardholderService.register(card.getCardholder());

		})).flatMap(cardholder -> {

			log.info("[CARD_DOMAIN] Found cardholder: {}", cardholder);

			boolean updateCardholder = false;

			if (!cardholder.getFirstName().equals(card.getCardholder().getFirstName())) {

				log.info("[CARD_DOMAIN] Change of the cardholder's first name: {} -> {}", cardholder.getFirstName(),
						card.getCardholder().getFirstName());

				cardholder.setFirstName(card.getCardholder().getFirstName());

				updateCardholder = true;
			}

			if (!cardholder.getLastName().equals(card.getCardholder().getLastName())) {

				log.info("[CARD_DOMAIN] Change of the cardholder's last name: {} -> {}", cardholder.getLastName(),
						card.getCardholder().getLastName());

				cardholder.setLastName(card.getCardholder().getLastName());

				updateCardholder = true;
			}

			if (updateCardholder) {

				log.info("[CARD_DOMAIN] Requesting change of cardholder data: {}", cardholder);
				
				cardholderService.update(cardholder);
			}

			card.setId(UUID.randomUUID());
			card.setSecurityCode(generateSecurityCode());
			card.setCardNumber(generateCardNumber());
			card.setExpirationDate(LocalDate.now().plusYears(6));
			card.setCardName(handleCardHolderName(cardholder.getFirstName(), cardholder.getLastName()));
			card.setCardholder(cardholder);

			return cardRepository.register(card).doOnSuccess(cardIssued -> {
				
				log.info("[CARD_DOMAIN] Card successfully issued: {}", cardIssued);
			});
		});
	}
	
	public Mono<Card> findById(UUID cardId) {
		
		log.info("[CARD_DOMAIN] Finding a card by identifier...");
		
		return cardRepository.findById(cardId).doOnSuccess(existingCard -> {
			
			log.info("[CARD_DOMAIN] Card found successfully: {}", existingCard);
		});
	}

	/**
	 * Gera um número de cartão 
	 * 
	 * @return
	 */
	private String generateCardNumber() {
		
		log.info("[CARD_DOMAIN] Generating card number...");

		UUID uuid = UUID.randomUUID();
		String cardNumber = uuid.toString().toUpperCase()
				.replace("-", "")
				.substring(0, 15)
				.replace("A", "0")
				.replace("B", "2")
				.replace("C", "4")
				.replace("D", "6")
				.replace("E", "8")
				.replace("F", "9");

		return "52951" + cardNumber;
	}

	/**
	 * Gera o código de segurança
	 * 
	 * @return
	 */
	private String generateSecurityCode() {
		
		log.info("[CARD_DOMAIN] Generating secutiry code...");

		return UUID.randomUUID().toString().substring(0, 4).toUpperCase();
	}

	/**
	 * Formata o nome do portador do cartão como impresso no cartão.
	 * 
	 * @param personFistName
	 * @param personLastName
	 * @return
	 */
	private String handleCardHolderName(String personFistName, String personLastName) {
		
		log.info("[CARD_DOMAIN] Generating card name...");
		
		List<String> names = new ArrayList<>();

		for (String firstName : personFistName.replaceAll("  ", " ").toUpperCase().split(" ")) {
			
			names.add(firstName.length() >= 12 ? firstName.substring(0, 12) : firstName);
		}

		for (String lastName : personLastName.replaceAll("  ", " ").toUpperCase().split(" ")) {
			
			names.add(lastName.length() >= 12 ? lastName.substring(0, 12) : lastName);
		}

		StringBuilder builder = new StringBuilder();

		for (int i = 0; i < names.size(); i++) {

			String name = names.get(i);

			if (i == 0 || i == names.size() - 1) {

				builder.append(names.get(i));

				if (i < names.size() - 1) {

					builder.append(" ");
				}

			} else if (name.length() > 2 && i < 4) {

				builder.append(names.get(i).substring(0, 1));

				if (i < names.size() - 1) {

					builder.append(" ");
				}
			}
		}

		return builder.toString();
	}
}
