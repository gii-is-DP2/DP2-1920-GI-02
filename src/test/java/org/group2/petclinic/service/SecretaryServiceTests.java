
package org.group2.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.group2.petclinic.model.Secretary;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class SecretaryServiceTests {

	@Autowired
	protected SecretaryService secretaryService;


	// findSecretaryByName(final String username) POSITIVE TEST
	@Test
	void shouldFindSecretaryByName() {
		//1. Arrange

		//2. Act
		Secretary secretary = this.secretaryService.findSecretaryByName("secretary1");

		//3. Assert
		assertThat(secretary.getId()).isEqualTo(1);
		assertThat(secretary.getUser().getUsername()).isEqualTo("secretary1");
		assertThat(secretary.getUser().getPassword()).isEqualTo("s3cr3tary");
	}

	// findSecretaryByName(final String username) NEGATIVE TEST
	// Introduce a name not valid (don't exits a secretary with this name)
	@Test
	void shouldNotFindSecretaryByName() {
		//1. Arrange

		//2. Act
		Secretary secretary = this.secretaryService.findSecretaryByName("secre1");

		//3. Assert
		assertNull(secretary);
	}

}
