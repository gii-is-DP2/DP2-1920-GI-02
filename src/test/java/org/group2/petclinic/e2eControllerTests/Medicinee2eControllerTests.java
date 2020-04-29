package org.group2.petclinic.e2eControllerTests;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.assertj.core.util.Lists;
import org.group2.petclinic.configuration.SecurityConfiguration;
import org.group2.petclinic.model.Medicine;
import org.group2.petclinic.service.MedicineService;
import org.group2.petclinic.web.MedicineController;
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
class Medicinee2eControllerTests {
	
	private static final int TEST_MEDICINE_ID = 1;

	@Autowired
	private MedicineController medicineController;

	@Autowired
	private MockMvc mockMvc;

	@WithMockUser(username = "admin1", authorities = { "admin" })
	@Test
	void testShowMedicineListHtml() throws Exception {
		mockMvc.perform(get("/admin/medicines")).andExpect(status().isOk())
				.andExpect(model().attributeExists("medicines")).andExpect(view().name("/admin/medicinesList"));
	}

	@WithMockUser(username = "admin1", authorities = { "aaaa" })
	@Test
	void testNotShowMedicineListHtml() throws Exception {
		mockMvc.perform(get("/admin/medicines").with(csrf())).andExpect(status().is4xxClientError());
	}
	
	@WithMockUser(username = "admin1", authorities = { "admin" })
	@Test
	void testInitCreationForm() throws Exception {
		mockMvc.perform(get("/admin/medicines/new")).andExpect(status().isOk())
				.andExpect(view().name("admin/createMedicineForm"))
				.andExpect(model().attributeExists("medicine"));
	}
	
	@WithMockUser(username = "admin1", authorities = { "aaaa" })
	@Test
	void testNotInitCreationForm() throws Exception {
		mockMvc.perform(get("/admin/medicines/new")).andExpect(status().is4xxClientError());
	}

	@WithMockUser(username = "admin1", authorities = { "admin" })
	@Test
	void testUpdateMedicineForm() throws Exception {
		mockMvc.perform(get("/admin/medicines/{medicineId}/edit", TEST_MEDICINE_ID)).andExpect(status().isOk())
				.andExpect(view().name("admin/updateMedicineForm"));
	}

	@WithMockUser(username = "admin1", authorities = { "aaaa" })
	@Test
	void testNotUpdateMedicineForm() throws Exception {
		mockMvc.perform(get("/admin/medicines/{medicineId}/edit", TEST_MEDICINE_ID)).andExpect(status().is4xxClientError());
	}

	@WithMockUser(username = "admin1", authorities = { "admin" })
	@Test
	void testDeleteMedicine() throws Exception {
		mockMvc.perform(get("/admin/medicines/{medicineId}/delete", 5)).andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/admin/medicines"));
	}

	@WithMockUser(username = "admin1", authorities = { "aaaa" })
	@Test
	void testNotDeleteMedicine() throws Exception {
		mockMvc.perform(get("/admin/medicines/{medicineId}/delete", TEST_MEDICINE_ID)).andExpect(status().is4xxClientError());
	}

	@WithMockUser(username = "admin1", authorities = { "admin" })
	@Test
	void testProcessCreationFormPostRedirectHasErrors() throws Exception {
		mockMvc.perform(post("/admin/medicines/new")//
				.with(csrf())//
				.param("name", "Medicine C")//
				.param("brand", ""))//
				.andExpect(model().attributeHasErrors("medicine"))//
				.andExpect(model().attributeHasFieldErrors("medicine", "brand"))//
				.andExpect(status().isOk()).andExpect(view().name("admin/createMedicineForm"));//
	}

}
