package org.group2.petclinic.web;

import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.assertj.core.util.Lists;
import org.group2.petclinic.configuration.SecurityConfiguration;
import org.group2.petclinic.model.Medicine;
import org.group2.petclinic.service.MedicineService;
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

	@Autowired
	private MedicineController medicineController;

	@MockBean
	private MedicineService medicineService;

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
		given(this.medicineService.findMedicines()).willReturn(Lists.newArrayList(medicine1, medicine2));
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
	void testUpdateMedicineForm() throws Exception
	{
		mockMvc.perform(get("/admin/medicines/{medicineId}/edit", 1))
		.andExpect(status().isOk())
		.andExpect(view().name("admin/updateMedicineForm"));
	}
	
	@Test
	void testNotUpdateMedicineForm() throws Exception
	{
		mockMvc.perform(get("/admin/medicines/{medicineId}/edit", 1))
		.andExpect(status().is4xxClientError());
	}

	@WithMockUser(value = "spring")
	@Test
	void testDeleteMedicine() throws Exception {
		mockMvc.perform(get("/admin/medicines/{medicineId}/delete", 1)).andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/admin/medicines"));
	}
	
	@Test
	void testNotDeleteMedicine() throws Exception {
		mockMvc.perform(get("/admin/medicines/{medicineId}/delete", 2)).andExpect(status().is4xxClientError());
	}

}
