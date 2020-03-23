
package org.group2.petclinic.web;

import org.group2.petclinic.model.Pet;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class PetValidator implements Validator {

	// SERVICES ---------------------------------------------------------------

	// CONSTRUCTOR ------------------------------------------------------------

	// SUPPORTS ---------------------------------------------------------------

	@Override
	public boolean supports(final Class<?> clazz) {
		return Pet.class.isAssignableFrom(clazz);
	}

	// VALIDATE ---------------------------------------------------------------

	@Override
	public void validate(final Object obj, final Errors errors) {
		Pet pet = (Pet) obj;
		String name = pet.getName();
		// name validation
		if (!StringUtils.hasLength(name) || name.length() > 50 || name.length() < 3) {
			errors.rejectValue("name", "required and between 3 and 50 characters", "required and between 3 and 50 character");
		}

		// type validation
		if (pet.isNew() && pet.getType() == null) {
			errors.rejectValue("type", "required", "required");
		}

		// birth date validation
		if (pet.getBirthDate() == null) {
			errors.rejectValue("birthDate", "required", "required");
		}
	}
}
