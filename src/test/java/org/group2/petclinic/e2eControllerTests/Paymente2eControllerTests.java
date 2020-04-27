
package org.group2.petclinic.e2eControllerTests;

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
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class Paymente2eControllerTests {

	private static final int		TEST_VISIT_ID		= 1;

	private static final int		TEST_PAYMENT_ID		= 1;

	private static final int		TEST_SECRETARY_ID	= 1;

	@Autowired
	private PaymentController		paymentController;

	@Autowired
	private PaymentService			paymentService;

	@Autowired
	private SecretaryService		secretaryService;

	@Autowired
	private VisitSecretaryService	visitSecretaryService;

	@Autowired
	private MockMvc					mockMvc;


	@BeforeEach
	void setup() {
	}

	// -------------------------- initCreationForm ---------------------------

	// POSITIVE TEST
	@WithMockUser(username = "secretary1", authorities = {
		"secretary"
	})
	@Test
	void testInitCreationFormGet() throws Exception {
		mockMvc.perform(get("/secretary/visits/{visitId}/payments/new", TEST_VISIT_ID)).andExpect(status().isOk())//
			.andExpect(model().attributeExists("payment"))//
			.andExpect(model().attributeExists("method"))//
			.andExpect(view().name("/secretary/visits/createPaymentForm"));
	}

	// NEGATIVE TEST
	// Acces with a user not authenticated
	@WithMockUser(username = "owner1", authorities = {
		"owner"
	})
	@Test
	void testNotInitCreationFormGet() throws Exception {
		mockMvc.perform(get("/secretary/visits/{visitId}/payments/new", TEST_VISIT_ID)//
			.with(csrf()))//
			.andExpect(status().is4xxClientError());
	}

	// -------------------------- processCreationForm ---------------------------

	// POSITIVE TEST
	// Redirect 1
	@WithMockUser(username = "secretary1", authorities = {
		"secretary"
	})
	@Test
	void testProcessCreationFormPostRedirect1() throws Exception {
		mockMvc.perform(post("/secretary/visits/{visitId}/payments/new", TEST_VISIT_ID)//
			.with(csrf())//
			.param("method", "cash")//
			.param("finalPrice", "30.00"))//
			.andExpect(status().is3xxRedirection()).andExpect(view().name("redirect:/secretary/visits"));//
	}

	// POSITIVE TEST
	// Redirect 2
	@WithMockUser(username = "secretary1", authorities = {
		"secretary"
	})
	@Test
	void testProcessCreationFormPostRedirect2() throws Exception {

		mockMvc.perform(post("/secretary/visits/{visitId}/payments/new", TEST_VISIT_ID)//
			.with(csrf())//
			.param("method", "creditcard")//
			.param("finalPrice", "30.00")//
			.param("id", TEST_PAYMENT_ID + ""))//
			.andExpect(status().is3xxRedirection()).andExpect(view().name("redirect:/secretary/visits/{visitId}/payments/{paymentId}/creditcards/new"));
	}

	// NEGATIVE TEST
	// Form with errors
	@WithMockUser(username = "secretary1", authorities = {
		"secretary"
	})
	@Test
	void testProcessCreationFormPostRedirectHasErrors() throws Exception {
		mockMvc.perform(post("/secretary/visits/{visitId}/payments/new", TEST_VISIT_ID)//
			.with(csrf())//
			.param("method", "cash")//
			.param("finalPrice", "-10.50"))//
			.andExpect(model().attributeHasErrors("payment"))//
			.andExpect(model().attributeHasFieldErrors("payment", "finalPrice"))//
			.andExpect(status().isOk()).andExpect(view().name("/secretary/visits/createPaymentForm"));//
	}

}
