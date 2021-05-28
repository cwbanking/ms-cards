package br.com.cwbanking.infra.mongodb.collection;

import java.time.LocalDate;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import br.com.cwbanking.domain.model.Cardholder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Document(collection = "Cardholders")
public class CardholderCollection {
	
	@Id
	private UUID id;
	
	private String document;
	
	private String firstName;
	
	private String lastName;
	
	private LocalDate birthDate;
	
	public CardholderCollection(final Cardholder cardholder) {
		
		id = cardholder.getId();
		document = cardholder.getDocument();
		firstName = cardholder.getFirstName();
		lastName = cardholder.getLastName();
		birthDate = cardholder.getBirthDate();
	}

	@Transient
	public Cardholder buildAsModel() {
		
		return Cardholder.builder()
				.id(id)
				.document(document)
				.firstName(firstName)
				.lastName(lastName)
				.birthDate(birthDate)
				.build();
	}	
}
