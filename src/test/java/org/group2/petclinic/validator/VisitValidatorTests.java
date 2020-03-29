
package org.group2.petclinic.validator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;

import org.group2.petclinic.model.Pet;
import org.group2.petclinic.model.Vet;
import org.group2.petclinic.model.Visit;
import org.group2.petclinic.model.VisitType;
import org.group2.petclinic.service.VisitService;
import org.group2.petclinic.web.VisitValidator;
import org.junit.jupiter.api.Test;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

public class VisitValidatorTests {

	private VisitValidator visitValidator;


	// validate POSITIVE TEST
	@Test
	void shouldAcceptVisit() {
		//1. Arrange
		Visit visit = new Visit();
		visit.setId(1);
		visit.setDescription("Description");
		visit.setMoment(LocalDateTime.parse("2020-02-03T12:00:00.00"));
		visit.setPet(mock(Pet.class));
		visit.setVet(mock(Vet.class));
		visit.setVisitType(mock(VisitType.class));

		Errors errors = new BeanPropertyBindingResult(visit, "pet");

		VisitService stubVisitService = mock(VisitService.class);
		when(stubVisitService.findVisitsByVetOnDate(any(), any())).thenReturn(new ArrayList<Visit>());

		this.visitValidator = new VisitValidator(stubVisitService);
		//2. Act
		visitValidator.validate(visit, errors);
		//3. Assert
		assertThat(errors.getErrorCount()).isEqualTo(0);
	}

	// validate NEGATIVE TEST
	// Visit on sunday
	@Test
	void shouldRejectVisit1() {
		//1. Arrange
		Visit visit = new Visit();
		visit.setId(1);
		visit.setDescription("Description");
		visit.setMoment(LocalDateTime.parse("2020-02-02T12:00:00.00"));
		visit.setPet(mock(Pet.class));
		visit.setVet(mock(Vet.class));
		visit.setVisitType(mock(VisitType.class));

		Errors errors = new BeanPropertyBindingResult(visit, "pet");

		VisitService stubVisitService = mock(VisitService.class);
		when(stubVisitService.findVisitsByVetOnDate(any(), any())).thenReturn(new ArrayList<Visit>());

		this.visitValidator = new VisitValidator(stubVisitService);
		//2. Act
		visitValidator.validate(visit, errors);
		//3. Assert
		assertThat(errors.getErrorCount()).isEqualTo(1);
	}

	// validate NEGATIVE TEST
	// Visit at 5 am
	@Test
	void shouldRejectVisit2() {
		//1. Arrange
		Visit visit = new Visit();
		visit.setId(1);
		visit.setDescription("Description");
		visit.setMoment(LocalDateTime.parse("2020-02-03T05:00:00.00"));
		visit.setPet(mock(Pet.class));
		visit.setVet(mock(Vet.class));
		visit.setVisitType(mock(VisitType.class));

		Errors errors = new BeanPropertyBindingResult(visit, "pet");

		VisitService stubVisitService = mock(VisitService.class);
		when(stubVisitService.findVisitsByVetOnDate(any(), any())).thenReturn(new ArrayList<Visit>());

		this.visitValidator = new VisitValidator(stubVisitService);
		//2. Act
		visitValidator.validate(visit, errors);
		//3. Assert
		assertThat(errors.getErrorCount()).isEqualTo(1);
	}

}
