
package org.group2.petclinic.unitTests.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.group2.petclinic.model.Pet;
import org.group2.petclinic.model.PetType;
import org.group2.petclinic.repository.PetRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class PetRepositoryTests {

	@Autowired
	private PetRepository petRepository;


	// findPetTypes() POSITIVE TEST
	@Test
	void shouldFindPetTypes() {
		//1. Arrange
		//2. Act
		List<PetType> visitTypes = this.petRepository.findPetTypes();
		//3. Assert
		assertThat(visitTypes).hasSize(6);
	}

	// findPetsByOwnerId(final int ownerId) POSITIVE TEST
	@Test
	void shouldFindPetsByOwnerId() {
		//1. Arrange
		int ownerId = 3;
		//2. Act
		List<Pet> pets = this.petRepository.findPetsByOwnerId(ownerId);
		//3. Assert
		assertThat(pets).hasSize(2);
	}

	// findPetsByOwnerId(final int ownerId) NEGATIVE TEST
	@Test
	void shouldNotFindPetsByOwnerId() {
		//1. Arrange
		int ownerId = 995;
		//2. Act
		List<Pet> pets = this.petRepository.findPetsByOwnerId(ownerId);
		//3. Assert
		assertThat(pets).hasSize(0);
	}

	// findAllPets() POSITIVE TEST
	@Test
	void shouldFindAllPets() {
		//1. Arrange
		//2. Act
		List<Pet> visitTypes = this.petRepository.findAllPets();
		//3. Assert
		assertThat(visitTypes).hasSize(13);
	}

}
