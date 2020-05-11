
package org.group2.petclinic.e2eControllerTests;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.time.LocalDateTime;

import javax.transaction.Transactional;

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
public class VisitSecretarye2eControllerTests {

	@Autowired
	private VisitSecretaryController	visitSecretaryController;

	@Autowired
	private MockMvc						mockMvc;


	@BeforeEach
	void setup() {
	}

	// -------------------------- listPayments ---------------------------

	// POSITIVE TEST
	@Transactional
	@WithMockUser(username = "secretary1", authorities = {
		"secretary"
	})
	@Test
	void testListPaymentsGet() throws Exception {
		mockMvc.perform(get("/secretary/visits"))//
			.andExpect(status().isOk())//
			.andExpect(model().attributeExists("visits"))//
			.andExpect(view().name("secretary/visits/noPay"));
	}

	// NEGATIVE TEST
	// Acces with a user not authenticated
	@Transactional
	@WithMockUser(username = "owner1", authorities = {
		"owner"
	})
	@Test
	void testNotListPaymentsGet() throws Exception {
		mockMvc.perform(get("/secretary/visits")//
			.with(csrf()))//
			.andExpect(status().is4xxClientError());
	}

}
