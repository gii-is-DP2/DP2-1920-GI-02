
package org.group2.petclinic.repository;

import static org.assertj.core.api.Assertions.assertThat;

import org.group2.petclinic.model.Vet;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class VetRepositoryTests {

	@Autowired
	private VetRepository vetRepository;


	// findByUsername(@Param("username") String username) POSITIVE TEST
	@Test
	void shouldFindVetByUsername() {
		//1. Arrange
		String username = "vet1";
		//2. Act
		Vet vet = this.vetRepository.findByUsername(username);
		//3. Assert
		assertThat(vet).isNotNull();
	}

	//  findByUsername(@Param("username") String username) NEGATIVE TEST
	@Test
	void shouldNotFindVetByUsername() {
		//1. Arrange
		String username = "FALSEUSERNAME";
		//2. Act
		Vet vet = this.vetRepository.findByUsername(username);
		//3. Assert
		assertThat(vet).isNull();
	}

}
