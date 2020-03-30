
package org.group2.petclinic.web;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.time.LocalDateTime;

import org.group2.petclinic.configuration.SecurityConfiguration;
import org.group2.petclinic.model.Authorities;
import org.group2.petclinic.model.Creditcard;
import org.group2.petclinic.model.Payment;
import org.group2.petclinic.model.Pet;
import org.group2.petclinic.model.Secretary;
import org.group2.petclinic.model.User;
import org.group2.petclinic.model.Vet;
import org.group2.petclinic.model.Visit;
import org.group2.petclinic.model.VisitType;
import org.group2.petclinic.service.CreditcardService;
import org.group2.petclinic.service.PaymentService;
import org.group2.petclinic.service.SecretaryService;
import org.group2.petclinic.service.VisitSecretaryService;
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

@WebMvcTest(value = CreditcardController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class)
public class CreditcardControllerTests {

	private static final int		TEST_PAYMENT_ID		= 1;

	private static final int		TEST_CREDITCARD_ID	= 1;

	private static final int		TEST_VISIT_ID		= 1;

	@Autowired
	private CreditcardController	creditcardController;

	@MockBean
	private PaymentService			stubPaymentService;

	@MockBean
	private CreditcardService		stubCreditcardService;

	@MockBean
	private VisitSecretaryService	stubVisitSecretaryService;

	@Autowired
	private MockMvc					mockMvc;


	@BeforeEach
	void setUp() {
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

		Payment payment = new Payment();
		payment.setCreditcard(null);
		payment.setMethod("creditcard");
		payment.setFinalPrice(30.00);
		date1.minusMinutes(1);
		payment.setMoment(date1);
		payment.setId(TEST_PAYMENT_ID);
		payment.setSecretary(mock(Secretary.class));
		when(this.stubPaymentService.findPaymentById(TEST_PAYMENT_ID)).thenReturn(payment);

		Creditcard creditcard = new Creditcard();

	}

	// -------------------------- initCreationForm ---------------------------

	// POSITIVE TEST
	@WithMockUser(value = "spring")
	@Test
	void testInitCreationFormGet() throws Exception {
		mockMvc.perform(get("/secretary/visits/{visitId}/payments/{paymentId}/creditcards/new", TEST_VISIT_ID, TEST_PAYMENT_ID)).andExpect(status().isOk())//
			.andExpect(model().attributeExists("creditcard"))//
			.andExpect(model().attributeExists("expMonth"))//
			.andExpect(view().name("secretary/visits/createCreditcardForm"));
		verify(stubVisitSecretaryService).findVisitById(TEST_VISIT_ID);
		verify(stubPaymentService).findPaymentById(TEST_PAYMENT_ID);
	}

	// NEGATIVE TEST
	// Acces with a user not authenticated
	@Test
	void testNotInitCreationFormGet() throws Exception {
		mockMvc.perform(post("/secretary/visits/{visitId}/payments/{paymentId}/creditcards/new", TEST_VISIT_ID, TEST_PAYMENT_ID)//
			.with(csrf()))//
			.andExpect(status().is4xxClientError());
	}

	// -------------------------- processCreationForm ---------------------------

	// POSITIVE TEST
	@WithMockUser("spring")
	@Test
	void testProcessCreationFormPost() throws Exception {

		mockMvc.perform(post("/secretary/visits/{visitId}/payments/{paymentId}/creditcards/new", TEST_VISIT_ID, TEST_PAYMENT_ID)//
			.with(csrf())//
			.param("holder", "Maria")//
			.param("brand", "visa")//
			.param("number", "4137921689337454")//
			.param("expMonth", "5")//
			.param("expYear", "30")//
			.param("securityCode", "250")//
			.param("id", TEST_CREDITCARD_ID + ""))//
			.andExpect(status().is3xxRedirection()).andExpect(view().name("redirect:/secretary/visits"));
		verify(stubVisitSecretaryService).findVisitById(TEST_VISIT_ID);
		verify(stubPaymentService, times(2)).findPaymentById(TEST_PAYMENT_ID);
	}

	// NEGATIVE TEST
	// Form with errors
	@WithMockUser("spring")
	@Test
	void testProcessCreationFormPostHasErrors() throws Exception {
		mockMvc.perform(post("/secretary/visits/{visitId}/payments/{paymentId}/creditcards/new", TEST_VISIT_ID, TEST_PAYMENT_ID)//
			.with(csrf())//
			.param("holder", "Maria")//
			.param("brand", "visa")//
			.param("number", "1122334455667788")//
			.param("expMonth", "5")//
			.param("expYear", "10")//
			.param("securityCode", ""))//
			.andExpect(model().attributeHasErrors("creditcard"))//
			.andExpect(model().attributeHasFieldErrors("creditcard", "number"))//
			.andExpect(model().attributeHasFieldErrors("creditcard", "expMonth"))//
			.andExpect(model().attributeHasFieldErrors("creditcard", "expYear"))//
			.andExpect(model().attributeHasFieldErrors("creditcard", "securityCode"))//
			.andExpect(status().isOk()).andExpect(view().name("secretary/visits/createCreditcardForm"));//
		verify(stubVisitSecretaryService).findVisitById(TEST_VISIT_ID);
		verify(stubPaymentService).findPaymentById(TEST_PAYMENT_ID);
	}

}
