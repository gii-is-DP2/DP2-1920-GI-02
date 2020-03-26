
package org.group2.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import org.group2.petclinic.model.Visit;
import org.group2.petclinic.repository.springdatajpa.SpringDataVisitSecretaryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
@ExtendWith(MockitoExtension.class)
public class VisitSecretaryServiceTests {

	@Mock
	private SpringDataVisitSecretaryRepository	stubVisitSecretaryRepository;

	@Autowired
	protected VisitSecretaryService				visitSecretaryService;


	// findVisitsNoPayment() POSITIVE TEST
	@Test
	void shouldFindVisitsNoPayment() {
		//1. Arrange

		//2. Act
		Iterable<Visit> visits = this.visitSecretaryService.findVisitsNoPayment();

		//3. Assert
		assertThat(visits).hasSize(2);

	}

	// findVisitsNoPayment() NEGATIVE TEST
	// asd
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

	// findVisitById(int id) POSITIVE TEST
	@Test
	void shouldFindVisitById() {
		//1. Arrange

		//2. Act
		Visit visit = this.visitSecretaryService.findVisitById(1);

		//3. Assert
		assertThat(visit.getDescription()).isEqualTo("rabies shot");
		assertThat(visit.getMoment()).isEqualTo("2013-01-01T10:00");
		assertThat(visit.getVisitType().getId()).isEqualTo(1);
		assertThat(visit.getVet().getId()).isEqualTo(2);
		assertThat(visit.getPet().getId()).isEqualTo(7);
		assertThat(visit.getPayment().getId()).isEqualTo(1);

	}

	// findVisitById(int id) NEGATIVE TEST
	// Introduce a id not valid (don't exits a secretary with this id)
	@Test
	void shouldNotFindVisitById() {
		//1. Arrange

		//2. Act
		Visit visit = this.visitSecretaryService.findVisitById(15);

		//3. Assert
		assertNull(visit);
	}

}
