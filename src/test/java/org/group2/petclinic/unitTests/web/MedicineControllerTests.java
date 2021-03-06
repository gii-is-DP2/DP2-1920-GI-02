package org.group2.petclinic.unitTests.web;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = MedicineController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class)
class MedicineControllerTests {
	
	private static final int TEST_MEDICINE_ID = 1;

	@Autowired
	private MedicineController medicineController;

	@MockBean
	private MedicineService stubMedicineService;

	@Autowired
	private MockMvc mockMvc;

	@BeforeEach
	void setup() {

		Medicine medicine1 = new Medicine();
		medicine1.setName("Betadine");
		medicine1.setBrand("Bayer");
		medicine1.setId(1);
		medicine1.setUsed(false);
		Medicine medicine2 = new Medicine();
		medicine1.setName("Medicine X");
		medicine1.setBrand("Pfizer");
		medicine1.setId(2);
		medicine1.setUsed(true);
		given(this.stubMedicineService.findMedicines()).willReturn(Lists.newArrayList(medicine1, medicine2));
	}

	@WithMockUser(value = "spring")
	@Test
	void testShowMedicineListHtml() throws Exception {
		mockMvc.perform(get("/admin/medicines")).andExpect(status().isOk())
				.andExpect(model().attributeExists("medicines")).andExpect(view().name("/admin/medicinesList"));
	}

	@Test
	void testNotShowMedicineListHtml() throws Exception {
		mockMvc.perform(get("/admin/medicines").with(csrf())).andExpect(status().is4xxClientError());
	}
	
	@WithMockUser(value = "spring")
	@Test
	void testInitCreationForm() throws Exception {
		mockMvc.perform(get("/admin/medicines/new")).andExpect(status().isOk())
				.andExpect(view().name("admin/createMedicineForm"))
				.andExpect(model().attributeExists("medicine"));
	}
	
	@Test
	void testNotInitCreationForm() throws Exception {
		mockMvc.perform(get("/admin/medicines/new")).andExpect(status().is4xxClientError());
	}

	@WithMockUser(value = "spring")
	@Test
	void testUpdateMedicineForm() throws Exception {
		mockMvc.perform(get("/admin/medicines/{medicineId}/edit", TEST_MEDICINE_ID)).andExpect(status().isOk())
				.andExpect(view().name("admin/updateMedicineForm"));
	}

	@Test
	void testNotUpdateMedicineForm() throws Exception {
		mockMvc.perform(get("/admin/medicines/{medicineId}/edit", TEST_MEDICINE_ID)).andExpect(status().is4xxClientError());
	}

	@WithMockUser(value = "spring")
	@Test
	void testDeleteMedicine() throws Exception {
		mockMvc.perform(get("/admin/medicines/{medicineId}/delete", 5)).andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/admin/medicines"));
	}

	@Test
	void testNotDeleteMedicine() throws Exception {
		mockMvc.perform(get("/admin/medicines/{medicineId}/delete", TEST_MEDICINE_ID)).andExpect(status().is4xxClientError());
	}

	@WithMockUser("spring")
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
