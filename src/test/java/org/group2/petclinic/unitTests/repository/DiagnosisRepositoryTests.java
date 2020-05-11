
package org.group2.petclinic.unitTests.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.group2.petclinic.unitTests.customasserts.PetclinicAssertions.assertThat;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.assertj.core.util.Arrays;
import org.group2.petclinic.model.Diagnosis;
import org.group2.petclinic.model.Medicine;
import org.group2.petclinic.model.Prescription;
import org.group2.petclinic.repository.DiagnosisRepository;
import org.group2.petclinic.repository.PrescriptionRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class DiagnosisRepositoryTests {

	@Autowired
	private DiagnosisRepository		diagnosisRepository;

	@Autowired
	private PrescriptionRepository	prescriptionRepository;


	// findAllDiagnosis() POSITIVE TEST
	@Test
	void shouldFindAllDiagnosis() {
		// 1. Arrange
		// 2. Act
		List<Diagnosis> diagnosis = this.diagnosisRepository.findAll();
		// 3. Assert
		assertThat(diagnosis).hasSize(4);
	}

	// findById(Integer diagnosisId) POSITIVE TEST
	@Test
	void shouldFindDiagnosisById() {
		// 1. Arrange
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
		int id = 1;
		Set<Prescription> prescriptions = new HashSet<Prescription>();
		prescriptions.add(this.prescriptionRepository.findById(1));
		prescriptions.add(this.prescriptionRepository.findById(2));
		List<Prescription> ps = new ArrayList<Prescription>(prescriptions);
		// 2. Act
		Diagnosis diagnosis = this.diagnosisRepository.findById(id);
		// 3. Assert
		assertThat(diagnosis).isNotNull();
		assertThat(diagnosis).hasDate(LocalDate.parse("2013/01/01", formatter));
		assertThat(diagnosis).hasDescription("rabies shot");
		assertThat(diagnosis).hasPrescriptions(ps);
	}

	// findById(Integer diagnosisId) NEGATIVE TEST
	@Test
	void shouldNotFindMedicineById() {
		// 1. Arrange
		int id = 999;
		// 2. Act
		Diagnosis diagnosis = this.diagnosisRepository.findById(id);
		// 3. Assert
		assertThat(diagnosis).isNull();
	}

	// save(Diagnosis diagnosis) POSITIVE TEST
	@Test
	void shouldSaveDiagnosis() {
		// 1. Arrange
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
		Diagnosis diagnosis = new Diagnosis();
		diagnosis.setId(1);
		diagnosis.setDate(LocalDate.parse("2013/01/01", formatter));
		diagnosis.setDescription("Description 1");
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
		Set<Prescription> prescriptions = new HashSet<Prescription>();
		prescriptions.add(p);
		diagnosis.setPrescriptions(prescriptions);

		int int1 = this.diagnosisRepository.findAll().size();
		// 2. Act
		diagnosisRepository.save(diagnosis);
		int int2 = this.diagnosisRepository.findAll().size();
		// 3. Assert
		assertThat(int2 > int1);
	}

}
