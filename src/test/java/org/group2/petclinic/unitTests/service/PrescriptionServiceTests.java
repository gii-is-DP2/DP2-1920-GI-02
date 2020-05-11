
package org.group2.petclinic.unitTests.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.group2.petclinic.model.Medicine;
import org.group2.petclinic.model.Prescription;
import org.group2.petclinic.repository.PrescriptionRepository;
import org.group2.petclinic.service.MedicineService;
import org.group2.petclinic.service.PrescriptionService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

@ExtendWith(MockitoExtension.class)
@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
@AutoConfigureTestDatabase(replace = Replace.NONE)
class PrescriptionServiceTests {

	@Mock
	private PrescriptionRepository	stubPrescriptionRepository;

	protected PrescriptionService	prescriptionService;


	@Test
	void shouldFindAll() {
		// 1. Arrange
		List<Prescription> toReturn = new ArrayList<Prescription>();
		toReturn.add(mock(Prescription.class));
		toReturn.add(mock(Prescription.class));
		toReturn.add(mock(Prescription.class));

		when(stubPrescriptionRepository.findAll()).thenReturn(toReturn);

		prescriptionService = new PrescriptionService(stubPrescriptionRepository);
		// 2. Act
		List<Prescription> prescriptions = (List<Prescription>) prescriptionService.findAllPrescriptions();
		// 3. Assert
		assertThat(prescriptions).isEqualTo(toReturn);
	}

	// findAll() NEGATIVE TEST
	@Test
	void shouldNotFindAll() {
		// 1. Arrange
		when(stubPrescriptionRepository.findAll()).thenReturn(null);

		prescriptionService = new PrescriptionService(stubPrescriptionRepository);
		// 2. Act
		List<Prescription> prescriptions = (List<Prescription>) prescriptionService.findAllPrescriptions();
		// 3. Assert
		assertThat(prescriptions).isNull();
	}

	// save(Prescription prescription) POSITIVE TEST
	@Test
	void shouldSavePrescription() {
		// 1. Arrange
		Medicine m = new Medicine();
		m.setId(1);
		m.setName("Winstrol");
		m.setBrand("Chinese lab");
		m.setUsed(true);

		Prescription toReturn = new Prescription();
		toReturn.setId(1);
		toReturn.setFrequency("2 times per week");
		toReturn.setDuration("4 weeks");
		toReturn.setMedicine(m);

		prescriptionService = new PrescriptionService(stubPrescriptionRepository);
		int int1 = this.stubPrescriptionRepository.findAll().size();
		// 2. Act
		prescriptionService.savePrescription(toReturn);
		int int2 = this.stubPrescriptionRepository.findAll().size();

		// 3. Assert
		assertThat(int2 > int1);
	}

}
