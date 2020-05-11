
package org.group2.petclinic.unitTests.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.group2.petclinic.unitTests.customasserts.PetclinicAssertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.group2.petclinic.model.Specialty;
import org.group2.petclinic.model.Vet;
import org.group2.petclinic.repository.VetRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class VetRepositoryTests {

	@Autowired
	private VetRepository vetRepository;


	// findAllVets() POSITIVE TEST
	@Test
	void shouldFindAllVets() {
		// 1. Arrange
		// 2. Act
		List<Vet> vets = (List<Vet>) this.vetRepository.findAll();
		// 3. Assert
		assertThat(vets).hasSize(6);
	}

	// findByUsername(@Param("username") String username) POSITIVE TEST
	@Test
	void shouldFindVetByUsername() {
		// 1. Arrange
		String username = "vet1";
		List<Specialty> specialties = new ArrayList<Specialty>();
		// 2. Act
		Vet vet = this.vetRepository.findByUsername(username);
		// 3. Assert
		assertThat(vet).isNotNull();
		assertThat(vet).hasSpecialties(specialties);
	}

	// findByUsername(@Param("username") String username) NEGATIVE TEST
	@Test
	void shouldNotFindVetByUsername() {
		// 1. Arrange
		String username = "FALSEUSERNAME";
		// 2. Act
		Vet vet = this.vetRepository.findByUsername(username);
		// 3. Assert
		assertThat(vet).isNull();
	}

}
