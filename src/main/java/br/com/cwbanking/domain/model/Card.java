package br.com.cwbanking.domain.model;

import java.time.LocalDate;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Card {

	private UUID id;
	
	private String cardNumber;
	
	private LocalDate expirationDate;
	
	private String securityCode;
	
	private String cardName;	
	
	private CardBrand brand;
	
	private Cardholder cardholder;
}
