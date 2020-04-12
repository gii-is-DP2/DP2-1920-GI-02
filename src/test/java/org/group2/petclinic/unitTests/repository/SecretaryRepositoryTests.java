
package org.group2.petclinic.unitTests.repository;

import static org.assertj.core.api.Assertions.assertThat;

import org.group2.petclinic.model.Secretary;
import org.group2.petclinic.repository.SecretaryRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class SecretaryRepositoryTests {

	@Autowired
	private SecretaryRepository secretaryRepository;

	// -------------------------- findSecretaryByName(@Param("username") String username) ---------------------------


	// POSITIVE TEST
	@Test
	void shouldFindSecretaryByName() {
		//1. Arrange
		String username = "secretary1";

		//2. Act
		Secretary secretary = this.secretaryRepository.findSecretaryByName(username);

		//3. Assert
		assertThat(secretary).isNotNull();

	}

	// NEGATIVE TEST
	// Doesn't exist a secretary with this username
	@Test
	void shouldNotFindSecretaryByName() {
		//1. Arrange
		String username = "secre50";

		//2. Act
		Secretary secretary = this.secretaryRepository.findSecretaryByName(username);

		//3. Assert
		assertThat(secretary).isNull();

	}

}
