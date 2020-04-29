
package org.group2.petclinic.e2eControllerTests;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.group2.petclinic.web.VisitController;
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
class Visite2eControllerTests {

	private static final int	TEST_VISIT_ID	= 9;

	@Autowired
	private VisitController		visitController;

	@Autowired
	private MockMvc				mockMvc;

	// initScheduleVisitForm ---------------------------------------------------


	// initScheduleVisitForm(final ModelMap modelMap) POSITIVE TEST
	@WithMockUser(username = "gfranklin", authorities = {
		"owner"
	})
	@Test
	void testShowInitScheduleVisitFormHtml() throws Exception {
		mockMvc.perform(get("/owner/schedule-visit").with(csrf()))
			.andExpect(status().isOk())
			.andExpect(model().attributeExists("petsOfOwner"))
			.andExpect(model().attributeExists("visit"))
			.andExpect(view().name("owner/scheduleVisitForm"));
	}

	// initScheduleVisitForm(final ModelMap modelMap) NEGATIVE TEST
	// admin doesn't have access
	@WithMockUser(username = "admin1", authorities = {
		"admin"
	})
	@Test
	void testShouldNotShowInitScheduleVisitFormHtml() throws Exception {
		mockMvc.perform(get("/owner/schedule-visit").with(csrf()))
			.andExpect(status().is4xxClientError());
	}

	// processScheduleVisitForm ------------------------------------------------

	// processScheduleVisitForm(@Valid final Visit visit, final BindingResult result, final ModelMap modelMap) POSITIVE TEST
	@WithMockUser(username = "gfranklin", authorities = {
		"owner"
	})
	@Test
	void testProcessScheduleVisitFormHtml() throws Exception {
		mockMvc.perform(
			post("/owner/schedule-visit").with(csrf())
				.param("description", "Description")
				.param("moment", "2012/03/12 10:00"))
			.andExpect(status().isOk());
	}

	// processScheduleVisitForm(@Valid final Visit visit, final BindingResult result, final ModelMap modelMap) NEGATIVE TEST
	// admin doesn't have access
	@WithMockUser(username = "admin1", authorities = {
		"admin"
	})
	@Test
	void testShouldNotProcessScheduleVisitFormHtml() throws Exception {
		mockMvc.perform(
			post("/owner/schedule-visit").with(csrf())
				.param("description", "Description")
				.param("moment", "2012/03/12 10:00"))
			.andExpect(status().is4xxClientError());
	}

	// processScheduleVisitForm(@Valid final Visit visit, final BindingResult result, final ModelMap modelMap) NEGATIVE TEST
	// invalid moment
	@WithMockUser(username = "gfranklin", authorities = {
		"owner"
	})
	@Test
	void testShouldNotProcessScheduleVisitFormHtml2() throws Exception {
		mockMvc.perform(
			post("/owner/schedule-visit").with(csrf())
				.param("description", "Description")
				.param("moment", "texto"))
			.andExpect(model().attributeHasErrors("visit"))
			.andExpect(model().attributeHasFieldErrors("visit", "moment"))
			.andExpect(status().isOk())
			.andExpect(view().name("owner/scheduleVisitForm"));
	}

	@WithMockUser(username = "vet1", authorities = {
		"veterinarian"
	})
	@Test
	void testShowVisitListHtml() throws Exception {
		mockMvc.perform(get("/vet/visits").with(csrf()))
			.andExpect(status().isOk())
			.andExpect(model().attributeExists("futureVisits"))
			.andExpect(model().attributeExists("pastVisits"))
			.andExpect(view().name("/vet/visitsList"));
	}

	// admin doesn't have access
	@WithMockUser(username = "admin1", authorities = {
		"admin"
	})
	@Test
	void testNotShowVisitListHtml() throws Exception {
		mockMvc.perform(get("/vet/visits").with(csrf()))
			.andExpect(status().is4xxClientError());
	}

	@WithMockUser(username = "gfranklin", authorities = {
		"owner"
	})
	@Test
	void testShowOwnerVisitListHtml() throws Exception {
		mockMvc.perform(get("/owner/visits")).andExpect(status().isOk())
			.andExpect(model().attributeExists("futureVisits"))
			.andExpect(model().attributeExists("pastVisits"))
			.andExpect(view().name("owner/visitsList"));
	}

	// admin doesn't have access
	@WithMockUser(username = "admin1", authorities = {
		"admin"
	})
	@Test
	void testNotOwnerShowVisitListHtml() throws Exception {
		mockMvc.perform(get("/owner/visits").with(csrf()))
			.andExpect(status().is4xxClientError());
	}

	@WithMockUser(username = "vet1", authorities = {
		"veterinarian"
	})
	@Test
	void testShowVisitHtml() throws Exception {
		mockMvc.perform(get("/vet/visits/{visitId}", TEST_VISIT_ID))
			.andExpect(status().isOk())
			.andExpect(model().attributeExists("visit"))
			.andExpect(view().name("vet/visitDetails"));
	}

	// admin doesn't have access
	@WithMockUser(username = "admin1", authorities = {
		"admin"
	})
	@Test
	void testNotShowVisitHtml() throws Exception {
		mockMvc
			.perform(get("/vet/visits/{visitId}", TEST_VISIT_ID).with(csrf()))
			.andExpect(status().is4xxClientError());
	}

	@WithMockUser(username = "gfranklin", authorities = {
		"owner"
	})
	@Test
	void testShowOwnerVisitHtml() throws Exception {
		mockMvc.perform(get("/owner/visits/{visitId}", TEST_VISIT_ID))
			.andExpect(status().isOk())
			.andExpect(model().attributeExists("visit"))
			.andExpect(view().name("owner/visitDetails"));
	}

	// admin doesn't have access
	@WithMockUser(username = "admin1", authorities = {
		"admin"
	})
	@Test
	void testNotShowOwnerVisitHtml() throws Exception {
		mockMvc
			.perform(get("/owner/visits/{visitId}", TEST_VISIT_ID).with(csrf()))
			.andExpect(status().is4xxClientError());
	}

}
