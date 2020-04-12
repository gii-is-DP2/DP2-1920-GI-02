
package org.group2.petclinic.unitTests.web;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.time.LocalDateTime;

import org.assertj.core.util.Lists;
import org.group2.petclinic.configuration.SecurityConfiguration;
import org.group2.petclinic.model.Pet;
import org.group2.petclinic.model.Vet;
import org.group2.petclinic.model.Visit;
import org.group2.petclinic.model.VisitType;
import org.group2.petclinic.service.VisitSecretaryService;
import org.group2.petclinic.web.VisitSecretaryController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = VisitSecretaryController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class)
public class VisitSecretaryControllerTests {

	@Autowired
	private VisitSecretaryController	visitSecretaryController;

	@MockBean
	private VisitSecretaryService		stubVisitSecretaryService;

	@Autowired
	private MockMvc						mockMvc;


	@BeforeEach
	void setup() {

		Visit visit1 = new Visit();
		visit1.setId(1);
		visit1.setDescription("Hello 1");
		LocalDateTime date1 = LocalDateTime.now();
		date1.plusYears(2);
		visit1.setMoment(date1);
		visit1.setPayment(null);
		visit1.setDiagnosis(null);
		visit1.setPet(mock(Pet.class));
		visit1.setVet(mock(Vet.class));
		visit1.setVisitType(mock(VisitType.class));

		Visit visit2 = new Visit();
		visit2.setId(2);
		visit2.setDescription("Hello for 2");
		LocalDateTime date2 = LocalDateTime.now();
		date2.plusYears(2);
		date2.plusMonths(2);
		visit2.setMoment(date2);
		visit2.setPayment(null);
		visit2.setDiagnosis(null);
		visit2.setPet(mock(Pet.class));
		visit2.setVet(mock(Vet.class));
		visit2.setVisitType(mock(VisitType.class));

		given(this.stubVisitSecretaryService.findVisitsNoPayment()).willReturn(Lists.newArrayList(visit1, visit2));
	}

	// -------------------------- listPayments ---------------------------

	// POSITIVE TEST
	@WithMockUser(value = "spring")
	@Test
	void testListPaymentsGet() throws Exception {
		mockMvc.perform(get("/secretary/visits"))//
			.andExpect(status().isOk())//
			.andExpect(model().attributeExists("visits"))//
			.andExpect(view().name("secretary/visits/noPay"));
		verify(stubVisitSecretaryService).findVisitsNoPayment();
	}

	// NEGATIVE TEST
	// Acces with a user not authenticated
	@Test
	void testNotListPaymentsGet() throws Exception {
		mockMvc.perform(get("/secretary/visits")//
			.with(csrf()))//
			.andExpect(status().is4xxClientError());
	}

}
