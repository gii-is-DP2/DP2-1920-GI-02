
package org.group2.petclinic.unitTests.validator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.group2.petclinic.unitTests.customasserts.PetclinicAssertions.assertThat;

import java.time.LocalDate;

import org.group2.petclinic.model.Pet;
import org.group2.petclinic.model.PetType;
import org.group2.petclinic.web.PetValidator;
import org.junit.jupiter.api.Test;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

public class PetValidatorTests {

	private PetValidator petValidator;


	// validate POSITIVE TEST
	@Test
	void shouldAcceptPet() {
		//1. Arrange
		PetType petType = new PetType();
		petType.setId(1);
		petType.setName("dog");

		Pet pet = new Pet();
		pet.setBirthDate(LocalDate.parse("2018-03-12"));
		pet.setId(1);
		pet.setName("Juanito");

		Errors errors = new BeanPropertyBindingResult(pet, "pet");

		this.petValidator = new PetValidator();
		//2. Act
		petValidator.validate(pet, errors);
		//3. Assert
		assertThat(errors.getErrorCount()).isEqualTo(0);
		assertThat(pet).hasBirthDate(LocalDate.parse("2018-03-12"));
	}

	// validate NEGATIVE TEST
	// pet name is two short (< 3 characters)
	@Test
	void shouldRejectPet1() {
		//1. Arrange
		PetType petType = new PetType();
		petType.setId(1);
		petType.setName("dog");

		Pet pet = new Pet();
		pet.setBirthDate(LocalDate.parse("2018-03-12"));
		pet.setId(1);
		pet.setName("j");

		Errors errors = new BeanPropertyBindingResult(pet, "pet");

		this.petValidator = new PetValidator();
		//2. Act
		petValidator.validate(pet, errors);
		//3. Assert
		assertThat(errors.getErrorCount()).isEqualTo(1);
		assertThat(errors.getFieldError("name")).isNotNull();
		assertThat(pet).hasBirthDate(LocalDate.parse("2018-03-12"));
	}

	// validate NEGATIVE TEST
	// pet type is null
	@Test
	void shouldRejectPet2() {
		//1. Arrange
		Pet pet = new Pet();
		pet.setBirthDate(LocalDate.parse("2018-03-12"));
		pet.setId(1);
		pet.setName("j");
		pet.setType(null);

		Errors errors = new BeanPropertyBindingResult(pet, "pet");

		this.petValidator = new PetValidator();
		//2. Act
		petValidator.validate(pet, errors);
		//3. Assert
		assertThat(errors.getErrorCount()).isEqualTo(1);
		assertThat(pet).hasBirthDate(LocalDate.parse("2018-03-12"));
	}

}
