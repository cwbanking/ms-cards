package br.com.cwbanking.infra.mongodb.collection;

import java.time.LocalDate;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import br.com.cwbanking.domain.model.Card;
import br.com.cwbanking.domain.model.CardBrand;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Document(collection = "Cards")
public class CardCollection {

	@Id
	private UUID id;

	private String cardNumber;

	private LocalDate expirationDate;

	private String securityCode;

	private String cardName;

	private String brand;

	private UUID cardholderId;

	public CardCollection(final Card card) {

		id = card.getId();
		cardNumber = card.getCardNumber();
		expirationDate = card.getExpirationDate();
		cardName = card.getCardName();
		securityCode = card.getSecurityCode();
		cardholderId = card.getCardholder().getId();
		brand = card.getBrand().name();
	}

	public Card getAsModel() {

		return Card.builder()
				.id(id)
				.cardName(cardName)
				.cardNumber(cardNumber)
				.brand(CardBrand.valueOf(brand))
				.build();
	}
}
