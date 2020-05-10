
package org.group2.petclinic.unitTests.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.group2.petclinic.unitTests.customasserts.PetclinicAssertions.assertThat;

import java.util.List;

import org.group2.petclinic.model.Medicine;
import org.group2.petclinic.repository.MedicineRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class MedicineRepositoryTests {

	@Autowired
	private MedicineRepository medicineRepository;


	// findAllMedicines() POSITIVE TEST
	@Test
	void shouldFindAllMedicines() {
		// 1. Arrange
		// 2. Act
		List<Medicine> medicines = this.medicineRepository.findAll();
		// 3. Assert
		assertThat(medicines).hasSize(5);
	}

	// findById(Integer medicineId) POSITIVE TEST
	@Test
	void shouldFindMedicineById() {
		// 1. Arrange
		int id = 1;
		// 2. Act
		Medicine medicine = this.medicineRepository.findMedicineById(id);
		// 3. Assert
		assertThat(medicine).isNotNull();
		assertThat(medicine).hasName("Betadine");
		assertThat(medicine).hasBrand("Bayer");
		assertThat(medicine).hasUsed(true);
	}

	// findById(Integer medicineId) NEGATIVE TEST
	@Test
	void shouldNotFindMedicineById() {
		// 1. Arrange
		int id = 999;
		// 2. Act
		Medicine medicine = this.medicineRepository.findMedicineById(id);
		// 3. Assert
		assertThat(medicine).isNull();
	}

	// save(Medicine medicine) POSITIVE TEST
	@Test
	void shouldSaveMedicine() {
		// 1. Arrange
		Medicine m = new Medicine();
		m.setId(3);
		m.setBrand("EEE");
		m.setName("EEE");
		m.setUsed(true);

		int int1 = this.medicineRepository.findAll().size();
		// 2. Act
		medicineRepository.save(m);
		int int2 = this.medicineRepository.findAll().size();
		// 3. Assert
		assertThat(int2 > int1);
	}

	// delete(Medicine medicine) POSITIVE TEST
	@Test
	void shouldDeleteMedicine() {
		// 1. Arrange
		int id = 5;

		int int1 = this.medicineRepository.findAll().size();
		// 2. Act
		medicineRepository.delete(this.medicineRepository.findMedicineById(id));
		int int2 = this.medicineRepository.findAll().size();
		// 3. Assert
		assertThat(int2 < int1);
	}

}
