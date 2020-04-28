package org.group2.petclinic.unitTests.web;

import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.Collection;

import org.group2.petclinic.configuration.SecurityConfiguration;
import org.group2.petclinic.model.Medicine;
import org.group2.petclinic.model.Visit;
import org.group2.petclinic.service.MedicineService;
import org.group2.petclinic.service.PrescriptionService;
import org.group2.petclinic.service.VisitService;
import org.group2.petclinic.web.PrescriptionController;
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

@WebMvcTest(value = PrescriptionController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class)
class PrescriptionControllerTests {

	private static final int TEST_VISIT_ID = 1;

	@Autowired
	private PrescriptionController prescripionController;

	@MockBean
	private PrescriptionService prescriptionService;

	@MockBean
	private VisitService visitService;

	@MockBean
	private MedicineService medicineService;

	@Autowired
	private MockMvc mockMvc;

	@BeforeEach
	void setup() {
		Visit visit = new Visit();
		visit.setId(1);
		given(this.visitService.findVisitById(visit.getId())).willReturn(new Visit());
		
		Medicine medicine = new Medicine();
		medicine.setId(1);
	}

	@WithMockUser(value = "spring")
	@Test
	void testInitCreationForm() throws Exception {
		mockMvc.perform(get("/vet/visits/{visitId}/prescriptions/new", TEST_VISIT_ID)).andExpect(status().isOk());
	}
	
	@Test
	void testNotInitCreationForm() throws Exception {
		mockMvc.perform(get("/vet/visits/{visitId}/prescriptions/new", TEST_VISIT_ID)).andExpect(status().is4xxClientError());
	}

	
	
	@WithMockUser(value = "spring")
	@Test
	void testProcessCreationFormSuccess() throws Exception {
		mockMvc.perform(post("/vet/visits/{visitId}/prescriptions/new", TEST_VISIT_ID).with(csrf())
				.param("frequency", "2 times per week").param("duration", "1 week"))
				.andExpect(status().isOk());
	}
	
	@WithMockUser(value = "vet1")
	@Test
	void testNotProcessCreationFormSuccess() throws Exception {
		mockMvc.perform(
			post("/vet/visits/{visitId}/prescriptions/new", TEST_VISIT_ID).with(csrf())
				.param("frequency", "2 times per week")
				.param("duration", "1 week")
				.param("medicine", "Medicine H"))
			.andExpect(model().attributeHasErrors("prescription"))
			.andExpect(model().attributeHasFieldErrors("prescription", "medicine"))
			.andExpect(status().isOk())
			.andExpect(view().name("vet/createOrUpdatePrescriptionForm"));
	}

}
