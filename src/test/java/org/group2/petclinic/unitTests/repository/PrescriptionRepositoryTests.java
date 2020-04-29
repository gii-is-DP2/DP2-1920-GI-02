
package org.group2.petclinic.unitTests.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.group2.petclinic.unitTests.customasserts.PetclinicAssertions.assertThat;

import java.util.List;

import org.group2.petclinic.model.Medicine;
import org.group2.petclinic.model.Prescription;
import org.group2.petclinic.repository.MedicineRepository;
import org.group2.petclinic.repository.PrescriptionRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class PrescriptionRepositoryTests {

	@Autowired
	private PrescriptionRepository prescriptionRepository;
	
	@Autowired
	private MedicineRepository medicineRepository;
	

	// findAllPrescriptions() POSITIVE TEST
	@Test
	void shouldFindAllPrescriptions() {
		//1. Arrange
		//2. Act
		List<Prescription> prescriptions = this.prescriptionRepository.findAll();
		//3. Assert
		assertThat(prescriptions).hasSize(5);
	}
	
	// findPrescriptionsWithMedicine(Medicine medicine) POSITIVE TEST
		@Test
		void findPrescriptionsWithMedicine() {
			//1. Arrange
			int id = 1;
			Medicine medicine = this.medicineRepository.findMedicineById(id);
			//2. Act
			List<Prescription> prescriptions = this.prescriptionRepository.findPrescriptionsWithMedicine(medicine);
			//3. Assert
			assertThat(prescriptions).hasSize(1);
		}
	
	// findById(Integer prescriptionId) POSITIVE TEST
		@Test
		void shouldFindPrescriptionById() {
			//1. Arrange
			int id = 1;
			Medicine medicine = this.medicineRepository.findMedicineById(id);
			//2. Act
			Prescription prescription = this.prescriptionRepository.findById(id);
			//3. Assert
			assertThat(prescription).isNotNull();
			assertThat(prescription).hasFrequency("2 times per day");
			assertThat(prescription).hasDuration("1 week");
			assertThat(prescription).hasMedicine(medicine);
		}

		// findById(Integer prescriptionId) NEGATIVE TEST
		@Test
		void shouldNotFindPrescriptionById() {
			//1. Arrange
			int id = 999;
			//2. Act
			Prescription prescription = this.prescriptionRepository.findById(id);
			//3. Assert
			assertThat(prescription).isNull();
		}
		
		// save(Prescription prescription) POSITIVE TEST
		@Test
		void shouldSavePrescription() {
			// 1. Arrange
			Medicine m = new Medicine();
			m.setId(3);
			m.setBrand("EEE");
			m.setName("EEE");
			m.setUsed(true);
			Prescription p = new Prescription();
			p.setDuration("EEE");
			p.setFrequency("EEE");
			p.setId(12);
			p.setMedicine(m);

			int int1 = this.prescriptionRepository.findAll().size();
			// 2. Act
			prescriptionRepository.save(p);
			int int2 = this.prescriptionRepository.findAll().size();
			// 3. Assert
			assertThat(int2 > int1);
		}


}
