package org.group2.petclinic.unitTests.web;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.group2.petclinic.configuration.SecurityConfiguration;
import org.group2.petclinic.model.Diagnosis;
import org.group2.petclinic.model.Pet;
import org.group2.petclinic.model.PetType;
import org.group2.petclinic.model.Vet;
import org.group2.petclinic.model.Visit;
import org.group2.petclinic.model.VisitType;
import org.group2.petclinic.service.DiagnosisService;
import org.group2.petclinic.service.VisitService;
import org.group2.petclinic.web.DiagnosisController;
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

@WebMvcTest(value = DiagnosisController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class)
class DiagnosisControllerTests {

	private static final int TEST_VISIT_ID = 1;

	@Autowired
	private DiagnosisController diagnosisController;

	@MockBean
	private DiagnosisService diagnosisService;

	@MockBean
	private VisitService visitService;

	@Autowired
	private MockMvc mockMvc;

	@BeforeEach
	void setup() {
		DateTimeFormatter formatter = DateTimeFormatter
				.ofPattern("yyyy/MM/dd HH:mm");
		
		PetType cat = new PetType();
		cat.setName("cat");
		
		Visit visit1 = new Visit();
		visit1.setId(1);
		visit1.setDescription("rabies shot");
		visit1.setMoment(LocalDateTime.parse("2013/01/01 10:00", formatter));
		visit1.setDiagnosis(null);
		visit1.setPayment(null);
		Pet pet1 = new Pet();
		pet1.setType(cat);
		visit1.setPet(pet1);
		visit1.setVet(null);
		visit1.setVisitType(mock(VisitType.class));
		given(this.visitService.findVisitById(visit1.getId())).willReturn(visit1);
	}

	@WithMockUser(value = "spring")
	@Test
	void testInitCreationForm() throws Exception {
		mockMvc.perform(get("/vet/visits/{visitId}/diagnosis/new", TEST_VISIT_ID)).andExpect(status().isOk())
				.andExpect(view().name("vet/createOrUpdateDiagnosisForm"))
				.andExpect(model().attributeExists("diagnosis"));
	}
	
	@Test
	void testNotInitCreationForm() throws Exception {
		mockMvc.perform(get("/vet/visits/{visitId}/diagnosis/new", TEST_VISIT_ID)).andExpect(status().is4xxClientError());
	}

	@WithMockUser(value = "spring")
	@Test
	void testProcessCreationFormSuccess() throws Exception {
		mockMvc.perform(post("/vet/visits/{visitId}/diagnosis/new", TEST_VISIT_ID).with(csrf())
				.param("date", "2012/03/12").param("description", "Description 1"))
				.andExpect(status().is3xxRedirection()).andExpect(view().name("redirect:/vet/visits/{visitId}"));
	}
	

		@WithMockUser(value = "spring")
		@Test
		void testNotProcessCreationFormSuccess() throws Exception {
			mockMvc.perform(
				post("/vet/visits/{visitId}/diagnosis/new", TEST_VISIT_ID).with(csrf())
					.param("date", "Pepe")
					.param("description", "texto"))
				.andExpect(model().attributeHasErrors("diagnosis"))
				.andExpect(model().attributeHasFieldErrors("diagnosis", "date"))
				.andExpect(status().isOk())
				.andExpect(view().name("vet/createOrUpdateDiagnosisForm"));
		}

}
