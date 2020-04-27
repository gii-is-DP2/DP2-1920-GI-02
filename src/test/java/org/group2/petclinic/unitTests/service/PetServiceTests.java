
package org.group2.petclinic.unitTests.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.group2.petclinic.model.Pet;
import org.group2.petclinic.model.PetType;
import org.group2.petclinic.repository.PetRepository;
import org.group2.petclinic.service.PetService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PetServiceTests {

	@Mock
	private PetRepository	stubPetRepository;

	protected PetService	petService;

	// savePet(final Pet pet) NOT TESTED (implemented by springframework)


	// findPetById(final int id) POSITIVE TEST
	@Test
	void shouldFindPetById() {
		//1. Arrange
		Pet toReturn = new Pet();
		toReturn.setId(1);
		toReturn.setName("Fofo");

		when(stubPetRepository.findById(1)).thenReturn(toReturn);

		petService = new PetService(stubPetRepository);
		//2. Act
		Pet pet = petService.findPetById(1);
		//3. Assert
		assertThat(pet).isEqualTo(toReturn);
		assertThat(pet.getName()).isEqualTo("Fofo");
	}

	// findPetById(final int id) NEGATIVE TEST
	// id for which no pet exists in the repository. Should return null.
	@Test
	void shouldNotFindPetById() {
		//1. Arrange
		when(stubPetRepository.findById(1)).thenReturn(null);

		petService = new PetService(stubPetRepository);
		//2. Act
		Pet pet = petService.findPetById(1);
		//3. Assert
		assertThat(pet).isNull();
	}

	// findAllPets() POSITIVE TEST
	@Test
	void shouldFindAllPets() {
		//1. Arrange
		Pet p1 = new Pet();
		p1.setId(1);
		Pet p2 = new Pet();
		p2.setId(2);
		Pet p3 = new Pet();
		p3.setId(3);

		List<Pet> toReturn = Arrays.asList(p1, p2, p3);

		when(stubPetRepository.findAllPets()).thenReturn(toReturn);

		petService = new PetService(stubPetRepository);
		//2. Act
		Collection<Pet> pets = petService.findAllPets();
		//3. Assert
		assertThat(pets).hasSize(3);
		assertThat(pets).contains(p1, p2, p3);
	}

	// findAllPets() NEGATIVE TEST
	// no pets in repository. Should return empty collection of pets.
	@Test
	void shouldNotFindAllPets() {
		//1. Arrange
		List<Pet> toReturn = Arrays.asList();

		when(stubPetRepository.findAllPets()).thenReturn(toReturn);

		petService = new PetService(stubPetRepository);
		//2. Act
		Collection<Pet> pets = petService.findAllPets();
		//3. Assert
		assertThat(pets).hasSize(0);
	}

	// findPetsByOwnerId(final int ownerId) POSITIVE TEST
	@Test
	void shouldFindPetsByOwner() {
		//1. Arrange
		Pet p1 = new Pet();
		p1.setId(1);
		Pet p2 = new Pet();
		p2.setId(2);
		Pet p3 = new Pet();
		p3.setId(3);

		List<Pet> toReturn = Arrays.asList(p1, p2);

		when(stubPetRepository.findPetsByOwnerId(1)).thenReturn(toReturn);

		petService = new PetService(stubPetRepository);
		//2. Act
		Collection<Pet> pets = petService.findPetsByOwnerId(1);
		//3. Assert
		assertThat(pets).hasSize(2);
		assertThat(pets).contains(p1, p2);
		assertThat(pets).doesNotContain(p3);
	}

	// findPetsByOwnerId(final int ownerId) NEGATIVE TEST
	// owner without pets. Should return empty collection of pets.
	@Test
	void shouldNotFindPetsByOwner() {
		//1. Arrange
		List<Pet> toReturn = Arrays.asList();

		when(stubPetRepository.findPetsByOwnerId(1)).thenReturn(toReturn);

		petService = new PetService(stubPetRepository);
		//2. Act
		Collection<Pet> pets = petService.findPetsByOwnerId(1);
		//3. Assert
		assertThat(pets).hasSize(0);
	}

	// findPetTypes() POSITIVE TEST
	@Test
	void shouldFindPetTypes() {
		//1. Arrange
		PetType pt1 = new PetType();
		pt1.setId(1);
		PetType pt2 = new PetType();
		pt2.setId(2);
		PetType pt3 = new PetType();
		pt3.setId(3);

		List<PetType> toReturn = Arrays.asList(pt1, pt2, pt3);

		when(stubPetRepository.findPetTypes()).thenReturn(toReturn);

		petService = new PetService(stubPetRepository);
		//2. Act
		Collection<PetType> petTypes = petService.findPetTypes();
		//3. Assert
		assertThat(petTypes).hasSize(3);
		assertThat(petTypes).contains(pt1, pt2, pt3);
	}

	// findPetTypes() NEGATIVE TEST
	// no pet types in repository. Should return empty collection of pet types.
	@Test
	void shouldNotFindPetTypes() {
		//1. Arrange
		List<PetType> toReturn = Arrays.asList();

		when(stubPetRepository.findPetTypes()).thenReturn(toReturn);

		petService = new PetService(stubPetRepository);
		//2. Act
		Collection<PetType> petTypes = petService.findPetTypes();
		//3. Assert
		assertThat(petTypes).hasSize(0);
	}

}
