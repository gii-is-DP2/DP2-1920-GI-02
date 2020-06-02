
package org.group2.petclinic.web;

import java.time.LocalDateTime;
import java.util.List;

import org.group2.petclinic.model.Visit;
import org.group2.petclinic.service.VisitService;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class VisitValidator implements Validator {

	// SERVICES ---------------------------------------------------------------

	private VisitService visitService;


	// CONSTRUCTOR ------------------------------------------------------------

	public VisitValidator(final VisitService visitService) {
		this.visitService = visitService;
	}

	// SUPPORTS ---------------------------------------------------------------

	@Override
	public boolean supports(final Class<?> clazz) {
		return Visit.class.isAssignableFrom(clazz);
	}

	// VALIDATE ---------------------------------------------------------------

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

		//Validate that visit isnt in the past
		if (!errors.hasFieldErrors("moment") && visit.getMoment().isBefore(LocalDateTime.now())) {
			errors.rejectValue("moment", "Visit cannot be in the past.", "Visit cannot be in the past.");
		}

		//Validate that the visit is during opening hours (Mon-Fri: 8am-8pm)
		if (!errors.hasFieldErrors("moment")) {
			LocalDateTime beginning = visit.getMoment();
			LocalDateTime end = beginning.plusMinutes(visit.getVisitType().getDuration());

			if (beginning.getHour() < 8) {
				errors.rejectValue("moment", "Visit cannot begin before 8 a.m.", "Visit cannot begin before 8 a.m.");
			}
			if (end.getHour() > 20 || end.getHour() == 20 && end.getMinute() > 0) {
				errors.rejectValue("moment", "Visit cannot end after 8 p.m.", "Visit cannot end after 8 p.m.");
			}
			if (beginning.getDayOfWeek().getValue() > 5) {
				errors.rejectValue("moment", "Visit cannot be during the weekend.", "Visit cannot be during the weekend.");
			}
		}

		//Validate that the visit's vet doesn't already have a visit scheduled during the same time
		if (!errors.hasFieldErrors("vet") && !errors.hasFieldErrors("moment")) {
			LocalDateTime visitBeginning = visit.getMoment();
			LocalDateTime visitEnd = visitBeginning.plusMinutes(visit.getVisitType().getDuration());

			List<Visit> visitsByVetOnSameDay = this.visitService.findVisitsByVetOnDate(visit.getVet(), visit.getMoment().toLocalDate());
			for (Visit v : visitsByVetOnSameDay) {
				LocalDateTime vBeginning = v.getMoment();
				LocalDateTime vEnd = vBeginning.plusMinutes(v.getVisitType().getDuration());
				if (!visitBeginning.isBefore(vBeginning) && !visitBeginning.isAfter(vEnd) ||//
					!visitEnd.isBefore(vBeginning) && !visitEnd.isAfter(vEnd)) {
					errors.rejectValue("moment", "Vet is occupied during the given slot.", "Vet is occupied during the given slot.");
				}

			}
		}

	}

}
