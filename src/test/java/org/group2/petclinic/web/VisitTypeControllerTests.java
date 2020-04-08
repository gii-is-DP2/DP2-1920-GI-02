package org.group2.petclinic.web;

import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.assertj.core.util.Lists;
import org.group2.petclinic.configuration.SecurityConfiguration;
import org.group2.petclinic.model.VisitType;
import org.group2.petclinic.service.VisitTypeService;
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

@WebMvcTest(controllers = VisitTypeController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class)
class VisitTypeControllerTests {

	@Autowired
	private VisitTypeController visitTypeController;

	@MockBean
	private VisitTypeService visitTypeService;

	@Autowired
	private MockMvc mockMvc;

	@BeforeEach
	void setup() {

		VisitType visitType1 = new VisitType();
		visitType1.setDuration(10);
		visitType1.setPrice(20.0);
		visitType1.setId(1);
		VisitType visitType2 = new VisitType();
		visitType1.setDuration(20);
		visitType1.setPrice(20.0);
		visitType1.setId(2);
		given(this.visitTypeService.findVisitTypes()).willReturn(Lists.newArrayList(visitType1, visitType2));
	}

	@WithMockUser(value = "spring")
	@Test
	void testShowVisitTypeListHtml() throws Exception {
		mockMvc.perform(get("/admin/visitTypes")).andExpect(status().isOk())
				.andExpect(model().attributeExists("visitTypes")).andExpect(view().name("/admin/visitTypesList"));
	}

	@Test
	void testNotShowVisitTypeListHtml() throws Exception {
		mockMvc.perform(get("/admin/visitTypes").with(csrf())).andExpect(status().is4xxClientError());
	}
	
	@WithMockUser(value = "spring")
	@Test
	void testUpdateVisitTypeForm() throws Exception
	{
		mockMvc.perform(get("/admin/visitTypes/{visitTypeId}/edit", 1))
		.andExpect(status().isOk())
		.andExpect(view().name("admin/updateVisitTypeForm"));
	}
	
	@Test
	void testNotUpdateVisitTypeForm() throws Exception
	{
		mockMvc.perform(get("/admin/visitTypes/{visitTypeId}/edit", 1))
		.andExpect(status().is4xxClientError());
	}

}
