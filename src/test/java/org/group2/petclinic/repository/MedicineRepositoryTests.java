
package org.group2.petclinic.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.group2.petclinic.model.Medicine;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class MedicineRepositoryTests {

	@Autowired
	private MedicineRepository medicineRepository;

	// findAllPets() POSITIVE TEST
	@Test
	void shouldFindAllMedicines() {
		//1. Arrange
		//2. Act
		List<Medicine> medicines= this.medicineRepository.findAll();
		//3. Assert
		assertThat(medicines).hasSize(4);
	}

}
