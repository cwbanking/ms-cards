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
public class Cardholder {
	
	private UUID id;
	
	private String document;
	
	private String firstName;
	
	private String lastName;
	
	private LocalDate birthDate;	
}
