
package org.group2.petclinic.web;

import java.time.LocalDateTime;

import org.group2.petclinic.model.Visit;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class VisitValidator implements Validator {

	@Override
	public void validate(final Object obj, final Errors errors) {
		Visit visit = (Visit) obj;

		//Validate required fields
		if (visit.getMoment() == null || StringUtils.isEmpty(visit.getMoment())) {
			errors.rejectValue("moment", "Moment is required.", "Moment is required.");
		}
		if (visit.getDescription() == null || StringUtils.isEmpty(visit.getDescription())) {
			errors.rejectValue("description", "Description is required.", "Description is required.");
		}
		if (visit.getPet() == null) {
			errors.rejectValue("pet", "Pet is required.", "Pet is required.");
		}
		if (visit.getVet() == null) {
			errors.rejectValue("vet", "Vet is required.", "Vet is required.");
		}

		//Validate that the visit is during opening hours (Mon-Fri: 8am-8pm)
		if (!errors.hasFieldErrors("moment")) {
			LocalDateTime beginning = visit.getMoment();
			LocalDateTime end = beginning.plusMinutes(visit.getVisitType().getDuration());

			if (!errors.hasFieldErrors("moment") && beginning.getHour() < 8) {
				errors.rejectValue("moment", "Visit cannot begin before 8 a.m.", "Visit cannot begin before 8 a.m.");
			}
			if (!errors.hasFieldErrors("moment") && (end.getHour() > 20 || end.getHour() == 20 && end.getMinute() > 0)) {
				errors.rejectValue("moment", "Visit cannot end after 8 p.m.", "Visit cannot end after 8 p.m.");
			}
			if (!errors.hasFieldErrors("moment") && beginning.getDayOfWeek().getValue() > 5) {
				errors.rejectValue("moment", "Visit cannot be during the weekend.", "Visit cannot be during the weekend.");
			}
		}

	}

	/**
	 * This Validator validates *just* Visit instances
	 */
	@Override
	public boolean supports(final Class<?> clazz) {
		return Visit.class.isAssignableFrom(clazz);
	}

}
