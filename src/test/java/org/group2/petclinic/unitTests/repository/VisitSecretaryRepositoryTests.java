
package org.group2.petclinic.unitTests.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.group2.petclinic.unitTests.customasserts.PetclinicAssertions.assertThat;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.group2.petclinic.model.Visit;
import org.group2.petclinic.repository.VisitSecretaryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@ExtendWith(MockitoExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
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
		assertThat(((ArrayList<Visit>) visits).get(0)).hasDescription("Descrip without");
		assertThat(((ArrayList<Visit>) visits).get(3)).hasDescription("Sample visit");

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
		assertThat(visit).hasDescription("rabies shot");

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

	// -------------------------- findAllVisits() ---------------------------

	// POSITIVE TEST
	@Test
	void shouldFindAllVisits() {
		//1. Arrange

		//2. Act
		List<Visit> visits = this.visitsecretaryRepository.findAllVisits();

		//3. Assert
		assertThat(visits).isNotNull();
		assertThat(visits).hasSize(9);
		assertThat(visits.get(0)).hasDescription("rabies shot");
		assertThat(visits.get(8)).hasDescription("Sample visit");

	}

	// NEGATIVE TEST
	// Use an empty list
	@Test
	void shouldNotFindAllVisits() {
		//1. Arrange
		List<Visit> visit = new ArrayList<Visit>();
		when(this.stubVisitSecretaryRepository.findAllVisits()).thenReturn(visit);

		//2. Act
		List<Visit> visits = this.stubVisitSecretaryRepository.findAllVisits();

		//3. Assert
		assertThat(visits).hasSize(0);
	}

}
