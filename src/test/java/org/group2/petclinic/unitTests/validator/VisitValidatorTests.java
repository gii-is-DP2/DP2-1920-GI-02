
package org.group2.petclinic.unitTests.validator;

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
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

public class VisitValidatorTests {

	private VisitValidator visitValidator;


	// validate POSITIVE TEST
	@ParameterizedTest
	@ValueSource(strings = {
		"2025-03-31T08:00:00.00", //Monday 8:00
		"2025-03-31T08:00:00.01", //Monday 8:00:00.01
		"2025-04-01T12:00:00.00", //Wednesday 12:00
		"2025-04-04T19:39:59.99", //Friday 19:39:59.99 - ends at 19:59:59.99
		"2025-04-04T19:40:00.00" //Friday 19:40 - ends at 20:00
	})
	void shouldAcceptVisit(String moment) {
		//1. Arrange
		VisitType visitType = new VisitType();
		visitType.setDuration(20);

		Visit visit = new Visit();
		visit.setId(1);
		visit.setDescription("Description");
		visit.setMoment(LocalDateTime.parse(moment));
		visit.setPet(mock(Pet.class));
		visit.setVet(mock(Vet.class));
		visit.setVisitType(visitType);

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
	@ParameterizedTest
	@ValueSource(strings = {
		"2025-03-31T06:00:00.00", //Monday 6:00
		"2025-03-31T07:59:59.99", //Monday 7:59:59.99
		"2025-04-01T23:00:00.00", //Wednesday 23:00
		"2025-04-04T19:40:00.01" //Friday 19:40:00.01 - ends at 20:00:00.01
	})
	void shouldRejectVisit1() {
		//1. Arrange
		VisitType visitType = new VisitType();
		visitType.setDuration(20);

		Visit visit = new Visit();
		visit.setId(1);
		visit.setDescription("Description");
		visit.setMoment(LocalDateTime.parse("2020-02-02T12:00:00.00"));
		visit.setPet(mock(Pet.class));
		visit.setVet(mock(Vet.class));
		visit.setVisitType(visitType);

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
