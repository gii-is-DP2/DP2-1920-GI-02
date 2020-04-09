
package org.group2.petclinic.web;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import org.assertj.core.util.Lists;
import org.group2.petclinic.configuration.SecurityConfiguration;
import org.group2.petclinic.model.Owner;
import org.group2.petclinic.model.Pet;
import org.group2.petclinic.model.User;
import org.group2.petclinic.model.Vet;
import org.group2.petclinic.model.Visit;
import org.group2.petclinic.service.OwnerService;
import org.group2.petclinic.service.PetService;
import org.group2.petclinic.service.VetService;
import org.group2.petclinic.service.VisitService;
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

@WebMvcTest(controllers = VisitController.class,
	excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,
		classes = WebSecurityConfigurer.class),
	excludeAutoConfiguration = SecurityConfiguration.class)
class VisitControllerTests {

	private static final int	TEST_VISIT_ID	= 1;

	@MockBean
	private VisitService		visitService;

	@MockBean
	private PetService			petService;

	@MockBean
	private VetService			vetService;

	@MockBean
	private OwnerService		ownerService;

	@Autowired
	private MockMvc				mockMvc;


	@BeforeEach
	void setup() {

		DateTimeFormatter formatter = DateTimeFormatter
			.ofPattern("yyyy/MM/dd HH:mm");

		Vet vet1 = new Vet();

		Visit visit1 = new Visit();
		visit1.setId(1);
		visit1.setDescription("rabies shot");
		visit1.setMoment(LocalDateTime.parse("2013/01/01 10:00", formatter));
		visit1.setDiagnosis(null);
		visit1.setPayment(null);
		visit1.setPet(mock(Pet.class));
		visit1.setVet(null);
		visit1.setVisitType(null);

		Visit visit2 = new Visit();
		visit2.setId(2);
		visit2.setDescription("rabies shot");
		visit2.setMoment(LocalDateTime.parse("2013/01/02 10:00", formatter));
		visit2.setDiagnosis(null);
		visit2.setPayment(null);
		visit1.setPet(mock(Pet.class));
		visit2.setVet(null);
		visit2.setVisitType(null);

		User user = new User();
		user.setUsername("jgarcia");
		user.setPassword("jgarcia");
		user.setEnabled(true);

		Owner owner = new Owner();
		owner.setId(1);
		owner.setFirstName("Juan");
		owner.setLastName("García");
		owner.setUser(mock(User.class));
		owner.setAddress("Calle Larga 1");
		owner.setCity("Sevilla");
		owner.setTelephone("954123123");
		owner.setUser(user);

		given(this.ownerService.findOwnerByUsername("jgarcia"))
			.willReturn(owner);

		given(this.visitService.findVisitsByVet(vet1))
			.willReturn(Lists.newArrayList(visit1, visit2));
		given(this.visitService.findVisitById(1)).willReturn(visit1);
		given(this.petService.findPetsByOwnerId(1))
			.willReturn(new ArrayList<Pet>());
	}

	// initScheduleVisitForm ---------------------------------------------------

	// initScheduleVisitForm(final ModelMap modelMap) POSITIVE TEST
	@WithMockUser(value = "jgarcia")
	@Test
	void testShowInitScheduleVisitFormHtml() throws Exception {
		mockMvc.perform(get("/owner/schedule-visit").with(csrf()))
			.andExpect(status().isOk())
			.andExpect(model().attributeExists("petsOfOwner"))
			.andExpect(model().attributeExists("visit"))
			.andExpect(view().name("owner/scheduleVisitForm"));
		verify(ownerService).findOwnerByUsername(any());
	}

	// initScheduleVisitForm(final ModelMap modelMap) NEGATIVE TEST
	// no mock user
	@Test
	void testShouldNotShowInitScheduleVisitFormHtml() throws Exception {
		mockMvc.perform(get("/owner/schedule-visit").with(csrf()))
			.andExpect(status().is4xxClientError());
	}

	// processScheduleVisitForm ------------------------------------------------

	// processScheduleVisitForm(@Valid final Visit visit, final BindingResult result, final ModelMap modelMap) POSITIVE TEST
	@WithMockUser(value = "jgarcia")
	@Test
	void testProcessScheduleVisitFormHtml() throws Exception {
		mockMvc.perform(
			post("/owner/schedule-visit").with(csrf())
				.param("description", "Description")
				.param("moment", "2012/03/12 10:00"))
			.andExpect(status().isOk());
	}

	// processScheduleVisitForm(@Valid final Visit visit, final BindingResult result, final ModelMap modelMap) NEGATIVE TEST
	// no mock user
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
	@WithMockUser(value = "jgarcia")
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

	@WithMockUser(value = "spring")
	@Test
	void testShowVisitListHtml() throws Exception {
		mockMvc.perform(get("/vet/visits")).andExpect(status().isOk())
			.andExpect(model().attributeExists("futureVisits"))
			.andExpect(model().attributeExists("pastVisits"))
			.andExpect(view().name("/vet/visitsList"));
	}

	@Test
	void testNotShowVisitListHtml() throws Exception {
		mockMvc.perform(get("/vet/visits").with(csrf()))
			.andExpect(status().is4xxClientError());
	}

	@WithMockUser(value = "spring")
	@Test
	void testShowOwnerVisitListHtml() throws Exception {
		mockMvc.perform(get("/owner/visits")).andExpect(status().isOk())
			.andExpect(model().attributeExists("futureVisits"))
			.andExpect(model().attributeExists("pastVisits"))
			.andExpect(view().name("owner/visitsList"));
	}

	@Test
	void testNotOwnerShowVisitListHtml() throws Exception {
		mockMvc.perform(get("/owner/visits").with(csrf()))
			.andExpect(status().is4xxClientError());
	}

	@WithMockUser(value = "spring")
	@Test
	void testShowVisitHtml() throws Exception {
		mockMvc.perform(get("/vet/visits/{visitId}", TEST_VISIT_ID))
			.andExpect(status().isOk())
			.andExpect(model().attributeExists("visit"))
			.andExpect(view().name("vet/visitDetails"));
	}

	@Test
	void testNotShowVisitHtml() throws Exception {
		mockMvc
			.perform(get("/vet/visits/{visitId}", TEST_VISIT_ID).with(csrf()))
			.andExpect(status().is4xxClientError());
	}

	@WithMockUser(value = "spring")
	@Test
	void testShowOwnerVisitHtml() throws Exception {
		mockMvc.perform(get("/owner/visits/{visitId}", TEST_VISIT_ID))
			.andExpect(status().isOk())
			.andExpect(model().attributeExists("visit"))
			.andExpect(view().name("owner/visitDetails"));
	}

	@Test
	void testNotShowOwnerVisitHtml() throws Exception {
		mockMvc
			.perform(get("/owner/visits/{visitId}", TEST_VISIT_ID).with(csrf()))
			.andExpect(status().is4xxClientError());
	}

}
