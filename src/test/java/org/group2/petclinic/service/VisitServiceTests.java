
package org.group2.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.group2.petclinic.model.User;
import org.group2.petclinic.model.Vet;
import org.group2.petclinic.model.Visit;
import org.group2.petclinic.model.VisitType;
import org.group2.petclinic.repository.VisitRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class VisitServiceTests {

	@Mock
	private VisitRepository	stubVisitRepository;

	protected VisitService	visitService;


	// findVisitsByVet(final Vet vet) POSITIVE TEST
	@Test
	void shouldFindVisitsForVet() {
		//1. Arrange
		Vet vet = new Vet();
		vet.setId(1);
		vet.setFirstName("James");
		vet.setFirstName("Carter");
		vet.setUser(mock(User.class));

		Visit visit1 = mock(Visit.class);
		Visit visit2 = mock(Visit.class);
		Visit visit3 = mock(Visit.class);
		List<Visit> toReturn = Arrays.asList(visit1, visit2, visit3);

		when(stubVisitRepository.findVisitsByVet(vet)).thenReturn(toReturn);

		visitService = new VisitService(stubVisitRepository);
		//2. Act
		List<Visit> visits = visitService.findVisitsByVet(vet);
		//3. Assert
		assertThat(visits).hasSize(3);
	}

	// findVisitsByVet(final Vet vet) NEGATIVE TEST
	// Vet that doesn't exist in the repository. Should return an empty list of visits.
	@Test
	void shouldNotFindVisitsForVet() {
		//1. Arrange
		Vet vet = new Vet();
		vet.setId(1);
		vet.setFirstName("James");
		vet.setFirstName("Carter");
		vet.setUser(mock(User.class));

		List<Visit> toReturn = Arrays.asList();

		when(stubVisitRepository.findVisitsByVet(vet)).thenReturn(toReturn);

		visitService = new VisitService(stubVisitRepository);
		//2. Act
		List<Visit> visits = visitService.findVisitsByVet(vet);
		//3. Assert
		assertThat(visits).hasSize(0);
	}

	// findVisitsByVetOnDate(final Vet vet, final LocalDate date) POSITIVE TEST
	@Test
	void shouldFindVisitsForVetAndDate() {
		//1. Arrange
		Vet vet = new Vet();
		vet.setId(1);
		vet.setFirstName("James");
		vet.setFirstName("Carter");
		vet.setUser(mock(User.class));

		Visit visit1 = mock(Visit.class);
		Visit visit2 = mock(Visit.class);
		Visit visit3 = mock(Visit.class);
		Visit visit4 = mock(Visit.class);
		Visit visit5 = mock(Visit.class);
		List<Visit> toReturn = Arrays.asList(visit1, visit2, visit3, visit4, visit5);

		LocalDateTime date_start = LocalDateTime.parse("2020-02-02T00:00:00.00");
		LocalDateTime date_end = LocalDateTime.parse("2020-02-03T00:00:00.00");
		LocalDate date = LocalDate.parse("2020-02-02");

		when(stubVisitRepository.findVisitsByVetOnBetween(vet, date_start, date_end)).thenReturn(toReturn);

		visitService = new VisitService(stubVisitRepository);
		//2. Act
		List<Visit> visits = visitService.findVisitsByVetOnDate(vet, date);
		//3. Assert
		assertThat(visits).hasSize(5);
	}

	// findVisitsByVetOnDate(final Vet vet, final LocalDate date) NEGATIVE TEST
	// Vet that doesn't have any visits on that day. Should return an empty list of visits.
	@Test
	void shouldNotFindVisitsForVetAndDate() {
		//1. Arrange
		Vet vet = new Vet();
		vet.setId(1);
		vet.setFirstName("James");
		vet.setFirstName("Carter");
		vet.setUser(mock(User.class));

		List<Visit> toReturn = Arrays.asList();

		LocalDateTime date_start = LocalDateTime.parse("2020-02-02T00:00:00.00");
		LocalDateTime date_end = LocalDateTime.parse("2020-02-03T00:00:00.00");
		LocalDate date = LocalDate.parse("2020-02-02");

		when(stubVisitRepository.findVisitsByVetOnBetween(vet, date_start, date_end)).thenReturn(toReturn);

		visitService = new VisitService(stubVisitRepository);
		//2. Act
		List<Visit> visits = visitService.findVisitsByVetOnDate(vet, date);
		//3. Assert
		assertThat(visits).hasSize(0);
	}

	// findVisitTypes() POSITIVE TEST
	@Test
	void shouldFindVisitTypes() {
		//1. Arrange
		VisitType visitType1 = new VisitType();
		visitType1.setId(1);
		visitType1.setName("Consultation");
		visitType1.setDuration(30);
		visitType1.setPrice(20.0);

		VisitType visitType2 = new VisitType();
		visitType2.setId(2);
		visitType2.setName("Revision");
		visitType2.setDuration(30);
		visitType2.setPrice(15.0);

		VisitType visitType3 = new VisitType();
		visitType3.setId(3);
		visitType3.setName("Operation");
		visitType3.setDuration(60);
		visitType3.setPrice(100.0);

		List<VisitType> toReturn = Arrays.asList(visitType1, visitType2, visitType3);

		when(stubVisitRepository.findVisitTypes()).thenReturn(toReturn);

		visitService = new VisitService(stubVisitRepository);
		//2. Act
		Collection<VisitType> visitTypes = visitService.findVisitTypes();
		//3. Assert
		assertThat(visitTypes).hasSize(3);
	}

	// findVisitTypes() NEGATIVE TEST
	// No visits in the repository.
	@Test
	void shouldNotFindVisitTypes() {
		//1. Arrange
		List<VisitType> toReturn = Arrays.asList();

		when(stubVisitRepository.findVisitTypes()).thenReturn(toReturn);

		visitService = new VisitService(stubVisitRepository);
		//2. Act
		Collection<VisitType> visitTypes = visitService.findVisitTypes();
		//3. Assert
		assertThat(visitTypes).hasSize(0);
	}

}
