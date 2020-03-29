
package org.group2.petclinic.web;

import org.group2.petclinic.model.Medicine;
import org.group2.petclinic.model.Prescription;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class PrescriptionValidator implements Validator {

	// SERVICES ---------------------------------------------------------------

	// CONSTRUCTOR ------------------------------------------------------------

	// SUPPORTS ---------------------------------------------------------------

	@Override
	public boolean supports(final Class<?> clazz) {
		return Prescription.class.isAssignableFrom(clazz);
	}

	// VALIDATE ---------------------------------------------------------------

	@Override
	public void validate(final Object obj, final Errors errors) {
		Prescription prescription = (Prescription) obj;
		Medicine medicine = prescription.getMedicine();

		// medicine validation
		if (medicine == null) {
			errors.rejectValue("medicine", "required", "required");
		}
	}
}
