
package org.group2.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

import org.group2.petclinic.model.Vet;
import org.group2.petclinic.model.Visit;
import org.group2.petclinic.model.VisitType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class VisitServiceTests {

	@Autowired
	protected VisitService	visitService;

	@Autowired
	protected VetService	vetService;


	@Test
	void shouldFindVisitsForVet() {
		//1. Arrange
		Vet vet = vetService.findVetByUsername("vet1");
		//2. Act
		List<Visit> visits = visitService.findVisitsByVet(vet);
		//3. Assert
		assertThat(visits).hasSize(3);
	}

	@Test
	void shouldFindVisitsForVetAndDate() {
		//1. Arrange
		Vet vet = vetService.findVetByUsername("vet1");
		LocalDate date = LocalDate.parse("2019-03-10");
		//2. Act
		List<Visit> visits = visitService.findVisitsByVetOnDate(vet, date);
		//3. Assert
		assertThat(visits).hasSize(1);
	}

	@Test
	void shouldFindVisitTypes() {
		//1. Arrange
		//2. Act
		Collection<VisitType> visitTypes = visitService.findVisitTypes();
		//3. Assert
		assertThat(visitTypes).hasSize(3);
	}

}
