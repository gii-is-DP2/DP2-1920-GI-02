
package org.group2.petclinic.unitTests.validator;

import static org.assertj.core.api.Assertions.assertThat;

import org.group2.petclinic.model.Medicine;
import org.group2.petclinic.model.Prescription;
import org.group2.petclinic.web.PrescriptionValidator;
import org.junit.jupiter.api.Test;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

public class PrescriptionValidatorTests {

	private PrescriptionValidator prescriptionValidator;


	// validate POSITIVE TEST
	@Test
	void shouldAcceptPrescription() {
		//1. Arrange
		Medicine medicine = new Medicine();
		medicine.setId(1);
		medicine.setName("Betadine");
		medicine.setBrand("Bayer");

		Prescription prescription = new Prescription();
		prescription.setId(1);
		prescription.setFrequency("2 times per day");
		prescription.setDuration("1 week");
		prescription.setMedicine(medicine);

		Errors errors = new BeanPropertyBindingResult(prescription, "prescription");

		this.prescriptionValidator = new PrescriptionValidator();
		//2. Act
		prescriptionValidator.validate(prescription, errors);
		//3. Assert
		assertThat(errors.getErrorCount()).isEqualTo(0);
	}

	// validate NEGATIVE TEST
	// medicine is null
	@Test
	void shouldRejectPrescription() {
		//1. Arrange
		Prescription prescription = new Prescription();
		prescription.setId(1);
		prescription.setFrequency("2 times per day");
		prescription.setDuration("1 week");
		prescription.setMedicine(null);

		Errors errors = new BeanPropertyBindingResult(prescription, "prescription");

		this.prescriptionValidator = new PrescriptionValidator();
		//2. Act
		prescriptionValidator.validate(prescription, errors);
		//3. Assert
		assertThat(errors.getErrorCount()).isEqualTo(1);
	}

}
