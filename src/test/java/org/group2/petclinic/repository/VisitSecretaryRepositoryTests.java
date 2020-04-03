
package org.group2.petclinic.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import org.group2.petclinic.model.Visit;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@ExtendWith(MockitoExtension.class)
@DataJpaTest
public class VisitSecretaryRepositoryTests {

	@Autowired
	private VisitSecretaryRepository	visitsecretaryRepository;

	@Mock
	private VisitSecretaryRepository	stubVisitSecretaryRepository;

	// -------------------------- findVisitsNoPayment() ---------------------------


	// POSITIVE TEST
	@Test
	void shouldFindVisitsNoPayment() {
		//1. Arrange

		//2. Act
		Iterable<Visit> visits = this.visitsecretaryRepository.findVisitsNoPayment();

		//3. Assert
		assertThat(visits).hasSize(4);

	}

	// NEGATIVE TEST
	// Use a empty list
	@Test
	void shouldNotFindVisitsNoPayment() {
		//1. Arrange
		Iterable<Visit> visits = new ArrayList<Visit>();
		when(this.stubVisitSecretaryRepository.findVisitsNoPayment()).thenReturn(visits);

		//2. Act
		Iterable<Visit> visits1 = this.stubVisitSecretaryRepository.findVisitsNoPayment();

		//3. Assert
		assertThat(visits1).hasSize(0);

	}

	// -------------------------- findVisitById(@Param("id") int id) ---------------------------

	// POSITIVE TEST
	@Test
	void shouldFindVisitById() {
		//1. Arrange
		int id = 1;

		//2. Act
		Visit visit = this.visitsecretaryRepository.findVisitById(id);

		//3. Assert
		assertThat(visit).isNotNull();

	}

	// NEGATIVE TEST
	// Doesn't exist a visit with this id
	@Test
	void shouldNotFindVisitById() {
		//1. Arrange
		int id = 50;

		//2. Act
		Visit visit = this.visitsecretaryRepository.findVisitById(id);

		//3. Assert
		assertThat(visit).isNull();

	}

}
