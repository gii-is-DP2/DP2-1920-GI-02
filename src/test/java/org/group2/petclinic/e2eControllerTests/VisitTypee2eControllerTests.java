
package org.group2.petclinic.e2eControllerTests;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.group2.petclinic.web.VisitTypeController;
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
class VisitTypee2eControllerTests {

	@Autowired
	private VisitTypeController	visitTypeController;

	@Autowired
	private MockMvc				mockMvc;


	@WithMockUser(username = "admin1", authorities = {
		"admin"
	})
	@Test
	void testShowVisitTypeListHtml() throws Exception {
		mockMvc.perform(get("/admin/visitTypes")).andExpect(status().isOk())
			.andExpect(model().attributeExists("visitTypes")).andExpect(view().name("/admin/visitTypesList"));
	}

	@WithMockUser(username = "vet1", authorities = {
		"veterinarian"
	})
	@Test
	void testNotShowVisitTypeListHtml() throws Exception {
		mockMvc.perform(get("/admin/visitTypes").with(csrf())).andExpect(status().is4xxClientError());
	}

	@WithMockUser(username = "admin1", authorities = {
		"admin"
	})
	@Test
	void testUpdateVisitTypeForm() throws Exception {
		mockMvc.perform(get("/admin/visitTypes/{visitTypeId}/edit", 1))
			.andExpect(status().isOk())
			.andExpect(view().name("admin/updateVisitTypeForm"));
	}

	@WithMockUser(username = "vet1", authorities = {
		"veterinarian"
	})
	@Test
	void testNotUpdateVisitTypeForm() throws Exception {
		mockMvc.perform(get("/admin/visitTypes/{visitTypeId}/edit", 1))
			.andExpect(status().is4xxClientError());
	}

}
