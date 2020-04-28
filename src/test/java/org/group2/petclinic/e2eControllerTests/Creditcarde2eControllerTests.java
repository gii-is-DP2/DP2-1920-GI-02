
package org.group2.petclinic.e2eControllerTests;

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
import org.group2.petclinic.web.CreditcardController;
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
public class Creditcarde2eControllerTests {

	private static final int		TEST_PAYMENT_ID		= 1;

	private static final int		TEST_CREDITCARD_ID	= 1;

	private static final int		TEST_VISIT_ID		= 1;

	@Autowired
	private CreditcardController	creditcardController;

	@Autowired
	private MockMvc					mockMvc;


	@BeforeEach
	void setUp() {
	}

	// -------------------------- initCreationForm ---------------------------

	// POSITIVE TEST
	@WithMockUser(username = "secretary1", authorities = {
		"secretary"
	})
	@Test
	void testInitCreationFormGet() throws Exception {
		mockMvc.perform(get("/secretary/visits/{visitId}/payments/{paymentId}/creditcards/new", TEST_VISIT_ID, TEST_PAYMENT_ID)).andExpect(status().isOk())//
			.andExpect(model().attributeExists("creditcard"))//
			.andExpect(model().attributeExists("expMonth"))//
			.andExpect(view().name("secretary/visits/createCreditcardForm"));
	}

	// NEGATIVE TEST
	// Acces with a user not authenticated
	@WithMockUser(username = "owner1", authorities = {
		"owner"
	})
	@Test
	void testNotInitCreationFormGet() throws Exception {
		mockMvc.perform(get("/secretary/visits/{visitId}/payments/{paymentId}/creditcards/new", TEST_VISIT_ID, TEST_PAYMENT_ID)//
			.with(csrf()))//
			.andExpect(status().is4xxClientError());
	}

	// -------------------------- processCreationForm ---------------------------

	// POSITIVE TEST
	@WithMockUser(username = "secretary1", authorities = {
		"secretary"
	})
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
	}

	// NEGATIVE TEST
	// Form with errors
	@WithMockUser(username = "secretary1", authorities = {
		"secretary"
	})
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
	}

}
