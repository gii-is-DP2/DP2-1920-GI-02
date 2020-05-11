
package org.group2.petclinic.unitTests.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.group2.petclinic.model.Medicine;
import org.group2.petclinic.model.Visit;
import org.group2.petclinic.model.VisitType;
import org.group2.petclinic.repository.VisitTypeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class VisitTypeRepositoryTests {

	@Autowired
	private VisitTypeRepository visitTypeRepository;


	// findAllVisitTypes() POSITIVE TEST
	@Test
	void shouldFindAllVisitTypes() {
		//1. Arrange
		//2. Act
		List<VisitType> visitTypes = this.visitTypeRepository.findAll();
		//3. Assert
		assertThat(visitTypes).hasSize(3);
	}

	// findById(Integer visitTypeId) POSITIVE TEST
	@Test
	void shouldFindVisitTypeById() {
		//1. Arrange
		int id = 1;
		//2. Act
		VisitType visitType = this.visitTypeRepository.findVisitTypeById(id);
		//3. Assert
		assertThat(visitType).isNotNull();
	}

	// findById(Integer visitTypeId) NEGATIVE TEST
	@Test
	void shouldNotFindVisitTypeById() {
		//1. Arrange
		int id = 999;
		//2. Act
		VisitType visitType = this.visitTypeRepository.findVisitTypeById(id);
		//3. Assert
		assertThat(visitType).isNull();
	}

}
