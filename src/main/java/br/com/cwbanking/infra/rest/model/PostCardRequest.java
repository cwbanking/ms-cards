package br.com.cwbanking.infra.rest.model;

import java.time.LocalDate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.cwbanking.domain.model.Card;
import br.com.cwbanking.domain.model.CardBrand;
import br.com.cwbanking.domain.model.Cardholder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostCardRequest {

	@NotBlank
	private String cardholderDocument;

	@NotBlank
	@Size(min = 2, max = 64)
	private String cardholderFirstName;

	@NotBlank
	@Size(min = 2, max = 128)
	private String cardholderLastName;

	@NotNull
	private LocalDate cardholderBirthDate;
	
	@NotNull
	private String cardBrand;

	@JsonIgnore
	public Card getAsModel() {

		return Card.builder()
				.cardholder(Cardholder.builder()
						.birthDate(cardholderBirthDate)
						.document(cardholderDocument)
						.firstName(cardholderFirstName)
						.lastName(cardholderLastName)
						.build())
				.brand(CardBrand.valueOf(cardBrand))
				.build();
	}
}
