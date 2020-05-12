
package org.group2.petclinic.unitTests.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.group2.petclinic.unitTests.customasserts.PetclinicAssertions.assertThat;

import java.time.LocalDateTime;
import java.util.List;

import org.group2.petclinic.model.Owner;
import org.group2.petclinic.model.Vet;
import org.group2.petclinic.model.Visit;
import org.group2.petclinic.model.VisitType;
import org.group2.petclinic.repository.VisitRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class VisitRepositoryTests {

	@Autowired
	private VisitRepository visitRepository;


	// findVisitsByVet(Vet vet) POSITIVE TEST
	@Test
	void shouldFindVisitByVet() {
		//1. Arrange
		Vet vet = new Vet();
		vet.setId(1);
		//2. Act
		List<Visit> visits = this.visitRepository.findVisitsByVet(vet);
		//3. Assert
		assertThat(visits).hasSize(5);
	}

	// findVisitsByVet(Vet vet) NEGATIVE TEST
	@Test
	void shouldNotFindVisitByVet() {
		//1. Arrange
		Vet vet = new Vet();
		vet.setId(99);
		//2. Act
		List<Visit> visits = this.visitRepository.findVisitsByVet(vet);
		//3. Assert
		assertThat(visits).hasSize(0);
	}

	// findFutureVisitsByVet(Vet vet) POSITIVE TEST
	@Test
	void shouldFindFutureVisitByVet() {
		//1. Arrange
		Vet vet = new Vet();
		vet.setId(1);
		//2. Act
		List<Visit> visits = this.visitRepository.findFutureVisitsByVet(vet, LocalDateTime.now());
		//3. Assert
		assertThat(visits).hasSize(1);
	}

	// findFutureVisitsByVet(Vet vet) NEGATIVE TEST
	@Test
	void shouldNotFindFutureVisitByVet() {
		//1. Arrange
		Vet vet = new Vet();
		vet.setId(2);
		//2. Act
		List<Visit> visits = this.visitRepository.findFutureVisitsByVet(vet, LocalDateTime.now());
		//3. Assert
		assertThat(visits).hasSize(0);
	}

	// findPastVisitsByVet(Vet vet) POSITIVE TEST
	@Test
	void shouldFindPastVisitByVet() {
		//1. Arrange
		Vet vet = new Vet();
		vet.setId(1);
		//2. Act
		List<Visit> visits = this.visitRepository.findPastVisitsByVet(vet, LocalDateTime.now());
		//3. Assert
		assertThat(visits).hasSize(4);
	}

	// findPastVisitsByVet(Vet vet) NEGATIVE TEST
	@Test
	void shouldNotFindPastVisitByVet() {
		//1. Arrange
		Vet vet = new Vet();
		vet.setId(3);
		//2. Act
		List<Visit> visits = this.visitRepository.findPastVisitsByVet(vet, LocalDateTime.now());
		//3. Assert
		assertThat(visits).hasSize(0);
	}

	// findVisitsByVetBetween(Vet vet, LocalDateTime beginning, LocalDateTime end) POSITIVE TEST
	@Test
	void shouldFindVisitByVetBetween() {
		//1. Arrange
		Vet vet = new Vet();
		vet.setId(1);
		LocalDateTime beginning = LocalDateTime.parse("2015-03-01T00:00:00.00");
		LocalDateTime end = LocalDateTime.parse("2019-04-01T00:00:00.00");
		//2. Act
		List<Visit> visits = this.visitRepository.findVisitsByVetBetween(vet, beginning, end);
		//3. Assert
		assertThat(visits).hasSize(2);
	}

	// findVisitsByVetBetween(Vet vet, LocalDateTime beginning, LocalDateTime end) NEGATIVE TEST
	@Test
	void shouldNotFindVisitByVetBetween() {
		//1. Arrange
		Vet vet = new Vet();
		vet.setId(1);
		LocalDateTime beginning = LocalDateTime.parse("2010-03-01T00:00:00.00");
		LocalDateTime end = LocalDateTime.parse("2011-04-01T00:00:00.00");
		//2. Act
		List<Visit> visits = this.visitRepository.findVisitsByVetBetween(vet, beginning, end);
		//3. Assert
		assertThat(visits).hasSize(0);
	}

	// findFutureVisitsByOwner(Owner owner) POSITIVE TEST
	@Test
	void shouldFindFutureVisitByOwner() {
		//1. Arrange
		Owner owner = new Owner();
		owner.setId(1);
		//2. Act
		List<Visit> visits = this.visitRepository.findFutureVisitsByOwner(owner, LocalDateTime.now());
		//3. Assert
		assertThat(visits).hasSize(1);
	}

	// findFutureVisitsByOwner(Owner owner) NEGATIVE TEST
	@Test
	void shouldNotFindFutureVisitByOwner() {
		//1. Arrange
		Owner owner = new Owner();
		owner.setId(3);
		//2. Act
		List<Visit> visits = this.visitRepository.findFutureVisitsByOwner(owner, LocalDateTime.now());
		//3. Assert
		assertThat(visits).hasSize(0);
	}

	// findPastVisitsByOwner(Owner owner) POSITIVE TEST
	@Test
	void shouldFindPastVisitByOwner() {
		//1. Arrange
		Owner owner = new Owner();
		owner.setId(1);
		//2. Act
		List<Visit> visits = this.visitRepository.findPastVisitsByOwner(owner, LocalDateTime.now());
		//3. Assert
		assertThat(visits).hasSize(1);
	}

	// findFutureVisitsByOwner(Owner owner) NEGATIVE TEST
	@Test
	void shouldNotFindPastVisitByOwner() {
		//1. Arrange
		Owner owner = new Owner();
		owner.setId(3);
		//2. Act
		List<Visit> visits = this.visitRepository.findPastVisitsByOwner(owner, LocalDateTime.now());
		//3. Assert
		assertThat(visits).hasSize(0);
	}

	// findVisitTypes() POSITIVE TEST
	@Test
	void shouldFindVisitTypes() {
		//1. Arrange
		//2. Act
		List<VisitType> visitTypes = this.visitRepository.findVisitTypes();
		//3. Assert
		assertThat(visitTypes).hasSize(3);
	}

	// findById(Integer visitId) POSITIVE TEST
	@Test
	void shouldFindVisitById() {
		//1. Arrange
		int id = 1;
		//2. Act
		Visit visit = this.visitRepository.findById(id);
		//3. Assert
		assertThat(visit).isNotNull();
		assertThat(visit).hasMoment(LocalDateTime.parse("2013-01-01T11:00"));
		assertThat(visit).hasDescription("rabies shot");
	}

	// findById(Integer visitId) NEGATIVE TEST
	@Test
	void shouldNotFindVisitById() {
		//1. Arrange
		int id = 999;
		//2. Act
		Visit visit = this.visitRepository.findById(id);
		//3. Assert
		assertThat(visit).isNull();
	}

}
