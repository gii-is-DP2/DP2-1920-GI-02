
package org.group2.petclinic.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.group2.petclinic.model.Medicine;
import org.group2.petclinic.model.Visit;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class MedicineRepositoryTests {

	@Autowired
	private MedicineRepository medicineRepository;

	// findAllMedicines() POSITIVE TEST
	@Test
	void shouldFindAllMedicines() {
		//1. Arrange
		//2. Act
		List<Medicine> medicines= this.medicineRepository.findAll();
		//3. Assert
		assertThat(medicines).hasSize(4);
	}
	
	// findById(Integer medicineId) POSITIVE TEST
		@Test
		void shouldFindMedicineById() {
			//1. Arrange
			int id = 1;
			//2. Act
			Medicine medicine = this.medicineRepository.findMedicineById(id);
			//3. Assert
			assertThat(medicine).isNotNull();
		}

		// findById(Integer medicineId) NEGATIVE TEST
		@Test
		void shouldNotFindMedicineById() {
			//1. Arrange
			int id = 999;
			//2. Act
			Medicine medicine = this.medicineRepository.findMedicineById(id);
			//3. Assert
			assertThat(medicine).isNull();
		}

}
