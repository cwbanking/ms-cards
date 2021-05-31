package br.com.cwbanking.infra.rest.model;

import java.time.LocalDate;
import java.util.UUID;

import br.com.cwbanking.domain.model.Card;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GetCardResponse {

	public GetCardResponse(Card card) {
		
		this.id = card.getId();
		this.cardExpirationDate = card.getExpirationDate();
		this.cardNumber = card.getCardNumber();
		this.securityCode = card.getSecurityCode();
		this.cardName = card.getCardName();
	}

	private UUID id;

	private String cardNumber;

	private LocalDate cardExpirationDate;

	private String securityCode;

	private String cardName;
}
