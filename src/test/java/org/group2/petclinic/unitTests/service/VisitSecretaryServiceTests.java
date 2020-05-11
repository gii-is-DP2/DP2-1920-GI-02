
package org.group2.petclinic.unitTests.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.group2.petclinic.unitTests.customasserts.PetclinicAssertions.assertThat;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.group2.petclinic.model.Visit;
import org.group2.petclinic.repository.springdatajpa.SpringDataVisitSecretaryRepository;
import org.group2.petclinic.service.VisitSecretaryService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
@ExtendWith(MockitoExtension.class)
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class VisitSecretaryServiceTests {

	@Mock
	private SpringDataVisitSecretaryRepository	stubVisitSecretaryRepository;

	@Autowired
	protected VisitSecretaryService				visitSecretaryService;

	// -------------------------- findVisitsNoPayment() ---------------------------


	// POSITIVE TEST
	@Test
	void shouldFindVisitsNoPayment() {
		//1. Arrange

		//2. Act
		Iterable<Visit> visits = this.visitSecretaryService.findVisitsNoPayment();

		//3. Assert
		assertThat(visits).hasSize(4);
		assertThat(((ArrayList<Visit>) visits).get(0)).hasDescription("Descrip without");
		assertThat(((ArrayList<Visit>) visits).get(0)).hasMoment(LocalDateTime.parse("2015-05-06T14:00"));
		assertThat(((ArrayList<Visit>) visits).get(3)).hasDescription("Sample visit");
		assertThat(((ArrayList<Visit>) visits).get(3)).hasMoment(LocalDateTime.parse("2025-04-01T10:30"));

	}

	// NEGATIVE TEST
	// There aren't any visit without payment
	@Test
	void shouldNotFindVisitsNoPayment() {
		//1. Arrange
		Iterable<Visit> visits = new ArrayList<Visit>();
		when(stubVisitSecretaryRepository.findVisitsNoPayment()).thenReturn(visits);
		visitSecretaryService = new VisitSecretaryService(stubVisitSecretaryRepository);

		//2. Act
		Iterable<Visit> visits1 = this.visitSecretaryService.findVisitsNoPayment();

		//3. Assert
		assertThat(visits1).hasSize(0);
	}

	// -------------------------- findVisitById(int id) ---------------------------

	// POSITIVE TEST
	@Test
	void shouldFindVisitById() {
		//1. Arrange

		//2. Act
		Visit visit = this.visitSecretaryService.findVisitById(1);

		//3. Assert
		assertThat(visit).hasDescription("rabies shot");
		assertThat(visit).hasMoment(LocalDateTime.parse("2013-01-01T10:00"));
		assertThat(visit.getVisitType().getId()).isEqualTo(1);
		assertThat(visit.getVet().getId()).isEqualTo(2);
		assertThat(visit.getPet().getId()).isEqualTo(7);
		assertThat(visit.getPayment().getId()).isEqualTo(1);

	}

	// NEGATIVE TEST
	// Introduce a id not valid (don't exits a secretary with this id)
	@Test
	void shouldNotFindVisitById() {
		//1. Arrange

		//2. Act
		Visit visit = this.visitSecretaryService.findVisitById(15);

		//3. Assert
		assertNull(visit);
	}

	// -------------------------- findAllVisits() ---------------------------

	// POSITIVE TEST
	@Test
	void shouldFindAllVisits() {
		//1. Arrange

		//2. Act
		List<Visit> visits = this.visitSecretaryService.findAllVisits();

		//3. Assert
		assertThat(visits).isNotNull();
		assertThat(visits).hasSize(9);
		assertThat(visits.get(0)).hasDescription("rabies shot");
		assertThat(visits.get(0)).hasMoment(LocalDateTime.parse("2013-01-01T10:00"));
		assertThat(visits.get(8)).hasDescription("Sample visit");
		assertThat(visits.get(8)).hasMoment(LocalDateTime.parse("2025-04-01T10:30"));

	}

	// NEGATIVE TEST
	// There aren't any visit
	@Test
	void shouldNotFindAllVisits() {
		//1. Arrange
		visitSecretaryService = new VisitSecretaryService(stubVisitSecretaryRepository);

		//2. Act
		Iterable<Visit> visits = this.visitSecretaryService.findAllVisits();

		//3. Assert
		assertThat(visits).hasSize(0);
	}
}
