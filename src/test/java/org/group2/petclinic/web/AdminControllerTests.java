
package org.group2.petclinic.web;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.group2.petclinic.configuration.SecurityConfiguration;
import org.group2.petclinic.model.Payment;
import org.group2.petclinic.model.Pet;
import org.group2.petclinic.model.Secretary;
import org.group2.petclinic.model.Vet;
import org.group2.petclinic.model.Visit;
import org.group2.petclinic.model.VisitType;
import org.group2.petclinic.service.PaymentService;
import org.group2.petclinic.service.VisitSecretaryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(value = AdminController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class)
public class AdminControllerTests {

	@Autowired
	private AdminController			adminController;

	@MockBean
	private PaymentService			stubPaymentService;

	@MockBean
	private VisitSecretaryService	stubVisitSecretaryService;

	@Autowired
	private MockMvc					mockMvc;

	private static final int		TEST_VISIT_ID	= 1;


	@BeforeEach
	void setUp() {
		Visit visit1 = new Visit();
		visit1.setId(TEST_VISIT_ID);
		visit1.setDescription("Hello 1");
		LocalDateTime date = LocalDateTime.now();
		date.minusHours(1);
		visit1.setMoment(date);
		visit1.setPayment(null);
		visit1.setDiagnosis(null);
		visit1.setPet(mock(Pet.class));
		visit1.setVet(mock(Vet.class));
		visit1.setVisitType(mock(VisitType.class));

		when(this.stubVisitSecretaryService.findVisitById(TEST_VISIT_ID)).thenReturn(visit1);

		List<Visit> visits = new ArrayList<Visit>();
		when(this.stubVisitSecretaryService.findAllVisits()).thenReturn(visits);

	}

	// -------------------------- showRenevuesByMonth ---------------------------

	// POSITIVE TEST
	@WithMockUser(value = "spring")
	@ParameterizedTest
	@ValueSource(ints = {
		1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12
	})
	void testShowRenevuesByMonthGet(int month) throws Exception {

		Payment payment = new Payment();
		LocalDateTime moment = LocalDateTime.of(2020, month, 01, 10, 30);
		payment.setMoment(moment);
		payment.setFinalPrice(55.00);
		payment.setCreditcard(null);
		payment.setId(1);
		payment.setMethod("cash");
		payment.setSecretary(mock(Secretary.class));

		List<Payment> listPayment = new ArrayList<Payment>();
		listPayment.add(payment);

		when(this.stubPaymentService.findRevenuesByMonth()).thenReturn(listPayment);

		mockMvc.perform(get("/admin/payment/revenues")).andExpect(status().isOk())//
			.andExpect(model().attributeExists("listMonth"))//
			.andExpect(view().name("/admin/revenuesByMonth"));
		verify(stubPaymentService, times(1)).findRevenuesByMonth();
	}

	// NEGATIVE TEST
	// Acces with a user not authenticated
	@Test
	void testNotShowRenevuesByMonthGet() throws Exception {
		mockMvc.perform(get("/admin/payment/revenues")//
			.with(csrf()))//
			.andExpect(status().is4xxClientError());
	}

	// -------------------------- showVisits ---------------------------

	// POSITIVE TEST
	@WithMockUser(value = "spring")
	@Test
	void testShowVisitsGet() throws Exception {
		mockMvc.perform(get("/admin/visits")).andExpect(status().isOk())//
			.andExpect(model().attributeExists("listVisit"))//
			.andExpect(view().name("/admin/visits"));
		verify(stubVisitSecretaryService).findAllVisits();
	}

	// NEGATIVE TEST
	// Acces with a user not authenticated
	@Test
	void testNotShowVisitsGet() throws Exception {
		mockMvc.perform(get("/admin/visits")//
			.with(csrf()))//
			.andExpect(status().is4xxClientError());
	}

	// -------------------------- showPaymentVisit ---------------------------

	// POSITIVE TEST
	@WithMockUser(value = "spring")
	@Test
	void testShowPaymentVisitGet() throws Exception {
		mockMvc.perform(get("/admin/visits/{visitId}/payment", TEST_VISIT_ID)).andExpect(status().isOk())//
			.andExpect(model().attributeExists("visit"))//
			.andExpect(view().name("/admin/payments"));
		verify(stubVisitSecretaryService).findVisitById(TEST_VISIT_ID);
	}

	// NEGATIVE TEST
	// Acces with a user not authenticated
	@Test
	void testNotShowPaymentVisitGet() throws Exception {
		mockMvc.perform(get("/admin/visits/{visitId}/payment", TEST_VISIT_ID)//
			.with(csrf()))//
			.andExpect(status().is4xxClientError());
	}

	// -------------------------- showDiagnosisVisit ---------------------------

	// POSITIVE TEST
	@WithMockUser(value = "spring")
	@Test
	void testShowDiagnosisVisitGet() throws Exception {
		mockMvc.perform(get("/admin/visits/{visitId}/diagnosis", TEST_VISIT_ID)).andExpect(status().isOk())//
			.andExpect(model().attributeExists("visit"))//
			.andExpect(view().name("/admin/diagnosis"));
		verify(stubVisitSecretaryService).findVisitById(TEST_VISIT_ID);
	}

	// NEGATIVE TEST
	// Acces with a user not authenticated
	@Test
	void testNotShowDiagnosisVisitGet() throws Exception {
		mockMvc.perform(get("/admin/visits/{visitId}/diagnosis", TEST_VISIT_ID)//
			.with(csrf()))//
			.andExpect(status().is4xxClientError());
	}

	// POSITIVE TEST
	@WithMockUser(value = "spring")
	@Test
	void testShowRenevuesByMonthGet1() throws Exception {

		Payment payment = new Payment();
		LocalDateTime moment = LocalDateTime.of(2020, 01, 01, 10, 30);
		payment.setMoment(moment);
		payment.setFinalPrice(55.00);
		payment.setCreditcard(null);
		payment.setId(1);
		payment.setMethod("cash");
		payment.setSecretary(mock(Secretary.class));

		List<Payment> listPayment = new ArrayList<Payment>();
		listPayment.add(payment);

		when(this.stubPaymentService.findRevenuesByMonth()).thenReturn(listPayment);

		mockMvc.perform(get("/admin/payment/revenues")).andExpect(status().isOk())//
			//.andExpect(model().attributeExists("listMonth"))//
			.andExpect(view().name("/admin/revenuesByMonth"));
		verify(stubPaymentService, times(1)).findRevenuesByMonth();
	}

}
