
package org.group2.petclinic.e2eControllerTests;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.Collection;

import javax.transaction.Transactional;

import org.group2.petclinic.configuration.SecurityConfiguration;
import org.group2.petclinic.model.Medicine;
import org.group2.petclinic.model.Visit;
import org.group2.petclinic.service.MedicineService;
import org.group2.petclinic.service.PrescriptionService;
import org.group2.petclinic.service.VisitService;
import org.group2.petclinic.web.PrescriptionController;
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
class Prescriptione2eControllerTests {

	private static final int		TEST_VISIT_ID	= 1;

	@Autowired
	private PrescriptionController	prescripionController;

	@Autowired
	private MockMvc					mockMvc;


	@Transactional
	@WithMockUser(username = "vet1", authorities = {
		"veterinarian"
	})
	@Test
	void testInitCreationForm() throws Exception {
		mockMvc.perform(get("/vet/visits/{visitId}/prescriptions/new", TEST_VISIT_ID)).andExpect(status().isOk());
	}

	@Transactional
	@WithMockUser(username = "vet1", authorities = {
		"aaaa"
	})
	@Test
	void testNotInitCreationForm() throws Exception {
		mockMvc.perform(get("/vet/visits/{visitId}/prescriptions/new", TEST_VISIT_ID)).andExpect(status().is4xxClientError());
	}

	@Transactional
	@WithMockUser(username = "vet1", authorities = {
		"veterinarian"
	})
	@Test
	void testProcessCreationFormSuccess() throws Exception {
		mockMvc.perform(post("/vet/visits/{visitId}/prescriptions/new", TEST_VISIT_ID).with(csrf()).param("frequency", "2 times per week").param("duration", "1 week")).andExpect(status().isOk());
	}

	@Transactional
	@WithMockUser(username = "vet1", authorities = {
		"veterinarian"
	})
	@Test
	void testNotProcessCreationFormSuccess() throws Exception {
		mockMvc.perform(post("/vet/visits/{visitId}/prescriptions/new", TEST_VISIT_ID).with(csrf()).param("frequency", "2 times per week").param("duration", "1 week").param("medicine", "Medicine H")).andExpect(model().attributeHasErrors("prescription"))
			.andExpect(model().attributeHasFieldErrors("prescription", "medicine")).andExpect(status().isOk()).andExpect(view().name("vet/createOrUpdatePrescriptionForm"));
	}

	@Transactional
	@WithMockUser(username = "vet1", authorities = {
		"veterinarian"
	})
	@Test
	void testProcessCreationFormPostRedirectHasErrors() throws Exception {
		mockMvc.perform(post("/vet/visits/{visitId}/prescriptions/new", TEST_VISIT_ID)//
			.with(csrf())//
			.param("frequency", "1 time per week")//
			.param("duration", ""))//
			.andExpect(model().attributeHasErrors("prescription"))//
			.andExpect(model().attributeHasFieldErrors("prescription", "duration"))//
			.andExpect(status().isOk()).andExpect(view().name("vet/createOrUpdatePrescriptionForm"));//
	}

}
