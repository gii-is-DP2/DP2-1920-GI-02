package org.group2.petclinic.e2eControllerTests;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.group2.petclinic.web.DiagnosisController;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
class Diagnosise2eControllerTests {

	private static final int TEST_VISIT_ID = 1;

	@Autowired
	private DiagnosisController diagnosisController;

	@Autowired
	private MockMvc mockMvc;

	@WithMockUser(username = "vet1", authorities = { "veterinarian" })
	@Test
	void testInitCreationForm() throws Exception {
		mockMvc.perform(get("/vet/visits/{visitId}/diagnosis/new", TEST_VISIT_ID)).andExpect(status().isOk())
				.andExpect(view().name("vet/createOrUpdateDiagnosisForm"))
				.andExpect(model().attributeExists("diagnosis"));
	}

	@WithMockUser(username = "vet1", authorities = { "aaaa" })
	@Test
	void testNotInitCreationForm() throws Exception {
		mockMvc.perform(get("/vet/visits/{visitId}/diagnosis/new", TEST_VISIT_ID))
				.andExpect(status().is4xxClientError());
	}

	@WithMockUser(username = "vet1", authorities = { "veterinarian" })
	@Test
	void testProcessCreationFormSuccess() throws Exception {
		mockMvc.perform(post("/vet/visits/{visitId}/diagnosis/new", TEST_VISIT_ID).with(csrf())
				.param("date", "2012/03/12").param("description", "Description 1"))
				.andExpect(status().is3xxRedirection()).andExpect(view().name("redirect:/vet/visits/{visitId}"));
	}

	@WithMockUser(username = "vet1", authorities = { "veterinarian" })
	@Test
	void testNotProcessCreationFormSuccess() throws Exception {
		mockMvc.perform(post("/vet/visits/{visitId}/diagnosis/new", TEST_VISIT_ID).with(csrf()).param("date", "Pepe")
				.param("description", "texto")).andExpect(model().attributeHasErrors("diagnosis"))
				.andExpect(model().attributeHasFieldErrors("diagnosis", "date")).andExpect(status().isOk())
				.andExpect(view().name("vet/createOrUpdateDiagnosisForm"));
	}

	@WithMockUser(username = "vet1", authorities = { "veterinarian" })
	@Test
	void testProcessCreationFormPostRedirectHasErrors() throws Exception {
		mockMvc.perform(post("/vet/visits/{visitId}/diagnosis/new", TEST_VISIT_ID)//
				.with(csrf())//
				.param("date", "")//
				.param("description", "Description"))//
				.andExpect(model().attributeHasErrors("diagnosis"))//
				.andExpect(model().attributeHasFieldErrors("diagnosis", "date"))//
				.andExpect(status().isOk()).andExpect(view().name("vet/createOrUpdateDiagnosisForm"));//
	}

}
