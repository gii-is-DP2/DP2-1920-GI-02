
package org.group2.petclinic.unitTests.web;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.*;
import static org.mockito.BDDMockito.verify;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.time.LocalDateTime;

import org.group2.petclinic.configuration.SecurityConfiguration;
import org.group2.petclinic.model.Authorities;
import org.group2.petclinic.model.Payment;
import org.group2.petclinic.model.Pet;
import org.group2.petclinic.model.Secretary;
import org.group2.petclinic.model.User;
import org.group2.petclinic.model.Vet;
import org.group2.petclinic.model.Visit;
import org.group2.petclinic.model.VisitType;
import org.group2.petclinic.service.PaymentService;
import org.group2.petclinic.service.SecretaryService;
import org.group2.petclinic.service.VisitSecretaryService;
import org.group2.petclinic.web.PaymentController;
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

@WebMvcTest(value = PaymentController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class)
public class PaymentControllerTests {

	private static final int		TEST_VISIT_ID		= 1;

	private static final int		TEST_PAYMENT_ID		= 1;

	private static final int		TEST_SECRETARY_ID	= 1;

	@Autowired
	private PaymentController		paymentController;

	@MockBean
	private PaymentService			stubPaymentService;

	@MockBean
	private SecretaryService		stubSecretaryService;

	@MockBean
	private VisitSecretaryService	stubVisitSecretaryService;

	@Autowired
	private MockMvc					mockMvc;


	@BeforeEach
	void setup() {

		Visit visit1 = new Visit();
		visit1.setId(TEST_VISIT_ID);

		visit1.setDescription("Hello 1");
		LocalDateTime date1 = LocalDateTime.now();
		date1.minusHours(1);
		visit1.setMoment(date1);
		visit1.setPayment(null);
		visit1.setDiagnosis(null);
		visit1.setPet(mock(Pet.class));
		visit1.setVet(mock(Vet.class));
		visit1.setVisitType(mock(VisitType.class));

		when(this.stubVisitSecretaryService.findVisitById(TEST_VISIT_ID)).thenReturn(visit1);

		Secretary secretary = new Secretary();
		User user = new User();
		user.setUsername("secretary1");
		user.setPassword("s3cr3tary");
		Authorities authority = new Authorities();
		authority.setUsername("secretary1");
		authority.setAuthority("secretary");
		secretary.setUser(user);
		secretary.setFirstName("Maria");
		secretary.setLastName("Lugo");
		secretary.setId(TEST_SECRETARY_ID);

		when(this.stubSecretaryService.findSecretaryByName("secretary1")).thenReturn(secretary);

		when(this.stubPaymentService.findPaymentById(TEST_PAYMENT_ID)).thenReturn(new Payment());

	}

	// -------------------------- initCreationForm ---------------------------

	// POSITIVE TEST
	@WithMockUser(value = "spring")
	@Test
	void testInitCreationFormGet() throws Exception {
		mockMvc.perform(get("/secretary/visits/{visitId}/payments/new", TEST_VISIT_ID)).andExpect(status().isOk())//
			.andExpect(model().attributeExists("payment"))//
			.andExpect(model().attributeExists("method"))//
			.andExpect(view().name("/secretary/visits/createPaymentForm"));
		verify(stubVisitSecretaryService).findVisitById(TEST_VISIT_ID);
	}

	// NEGATIVE TEST
	// Acces with a user not authenticated
	@Test
	void testNotInitCreationFormGet() throws Exception {
		mockMvc.perform(get("/secretary/visits/{visitId}/payments/new", TEST_VISIT_ID)//
			.with(csrf()))//
			.andExpect(status().is4xxClientError());
	}

	// -------------------------- processCreationForm ---------------------------

	// POSITIVE TEST
	// Redirect 1
	@WithMockUser("spring")
	@Test
	void testProcessCreationFormPostRedirect1() throws Exception {
		mockMvc.perform(post("/secretary/visits/{visitId}/payments/new", TEST_VISIT_ID)//
			.with(csrf())//
			.param("method", "cash")//
			.param("finalPrice", "30.00"))//
			.andExpect(status().is3xxRedirection()).andExpect(view().name("redirect:/secretary/visits"));//
		verify(stubVisitSecretaryService, times(2)).findVisitById(TEST_VISIT_ID);
		verify(stubSecretaryService).findSecretaryByName("spring");
	}

	// POSITIVE TEST
	// Redirect 2
	@WithMockUser("spring")
	@Test
	void testProcessCreationFormPostRedirect2() throws Exception {

		mockMvc.perform(post("/secretary/visits/{visitId}/payments/new", TEST_VISIT_ID)//
			.with(csrf())//
			.param("method", "creditcard")//
			.param("finalPrice", "30.00")//
			.param("id", TEST_PAYMENT_ID + ""))//
			.andExpect(status().is3xxRedirection()).andExpect(view().name("redirect:/secretary/visits/{visitId}/payments/{paymentId}/creditcards/new"));
		verify(stubVisitSecretaryService, times(2)).findVisitById(TEST_VISIT_ID);
		verify(stubSecretaryService).findSecretaryByName("spring");
	}

	// NEGATIVE TEST
	// Form with errors
	@WithMockUser("spring")
	@Test
	void testProcessCreationFormPostRedirectHasErrors() throws Exception {
		mockMvc.perform(post("/secretary/visits/{visitId}/payments/new", TEST_VISIT_ID)//
			.with(csrf())//
			.param("method", "cash")//
			.param("finalPrice", "-10.50"))//
			.andExpect(model().attributeHasErrors("payment"))//
			.andExpect(model().attributeHasFieldErrors("payment", "finalPrice"))//
			.andExpect(status().isOk()).andExpect(view().name("/secretary/visits/createPaymentForm"));//
		verify(stubVisitSecretaryService).findVisitById(TEST_VISIT_ID);
	}

}
