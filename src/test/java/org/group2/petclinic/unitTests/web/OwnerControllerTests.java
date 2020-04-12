
package org.group2.petclinic.unitTests.web;

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

import org.group2.petclinic.configuration.SecurityConfiguration;
import org.group2.petclinic.model.Owner;
import org.group2.petclinic.model.Pet;
import org.group2.petclinic.model.User;
import org.group2.petclinic.service.AuthoritiesService;
import org.group2.petclinic.service.OwnerService;
import org.group2.petclinic.service.PetService;
import org.group2.petclinic.service.UserService;
import org.group2.petclinic.web.OwnerController;
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

@WebMvcTest(controllers = OwnerController.class,
	excludeFilters = @ComponentScan.Filter(
		type = FilterType.ASSIGNABLE_TYPE,
		classes = WebSecurityConfigurer.class),
	excludeAutoConfiguration = SecurityConfiguration.class)
class OwnerControllerTests {

	@MockBean
	private OwnerService		stubOwnerService;

	@MockBean
	private PetService			stubPetService;

	@MockBean
	private UserService			stubUserService;

	@MockBean
	private AuthoritiesService	stubAuthoritiesService;

	@Autowired
	private MockMvc				mockMvc;


	@BeforeEach
	void setup() {
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

		Pet pet = new Pet();

		given(this.stubOwnerService.findOwnerByUsername("jgarcia"))
			.willReturn(owner);
		given(this.stubPetService.findPetById(1)).willReturn(pet);
	}

	// showOwnerInfo -----------------------------------------------------------

	// showOwnerInfo() POSITIVE TEST
	@WithMockUser(value = "jgarcia")
	@Test
	void testShowOwnerInfoHtml() throws Exception {
		mockMvc.perform(get("/owner/profile").with(csrf()))
			.andExpect(status().isOk())
			.andExpect(model().attributeExists("owner"))
			.andExpect(view().name("owner/profileView"));
		verify(stubOwnerService).findOwnerByUsername(any());
	}

	// showOwnerInfo() NEGATIVE TEST
	// no mock user
	@Test
	void testShouldNotShowOwnerInfoHtml() throws Exception {
		mockMvc.perform(get("/owner/profile").with(csrf()))
			.andExpect(status().is4xxClientError());
	}

	// initUpdateOwnerForm -----------------------------------------------------

	// initUpdateOwnerForm(final Model model) POSITIVE TEST
	@WithMockUser(value = "jgarcia")
	@Test
	void testInitUpdateOwnerFormHtml() throws Exception {
		mockMvc.perform(get("/owner/profile/edit").with(csrf()))
			.andExpect(status().isOk())
			.andExpect(model().attributeExists("owner"))
			.andExpect(view().name("owner/profileForm"));
		verify(stubOwnerService).findOwnerByUsername(any());
	}

	// initUpdateOwnerForm(final Model model) NEGATICE TEST
	// no mock user
	@Test
	void testShouldNotInitUpdateOwnerFormHtml() throws Exception {
		mockMvc.perform(get("/owner/profile/edit").with(csrf()))
			.andExpect(status().is4xxClientError());
	}

	// processUpdateOwnerForm --------------------------------------------------

	// processUpdateOwnerForm(@Valid final Owner owner, final BindingResult result) POSITIVE TEST
	@WithMockUser(value = "jgarcia")
	@Test
	void testProcessOwnerFormHtml() throws Exception {
		mockMvc.perform(
			post("/owner/profile/edit").with(csrf())
				.param("firstName", "José")
				.param("lastName", "García")
				.param("address", "Calle 1")
				.param("city", "Sevila")
				.param("telephone", "123")
				.param("user.username", "jgarcia")
				.param("user.password", "jgarcia"))
			.andExpect(status().is3xxRedirection())
			.andExpect(view().name("redirect:/owner/profile"));
		verify(stubOwnerService).findOwnerByUsername("jgarcia");
		verify(stubOwnerService).saveOwner(any());
	}

	// processUpdateOwnerForm(@Valid final Owner owner, final BindingResult result) NEGATIVE TEST
	// no mock user
	@Test
	void testShouldNotProcessOwnerFormHtml() throws Exception {
		mockMvc.perform(post("/owner/profile/edit").with(csrf()))
			.andExpect(status().is4xxClientError());
	}

	// processUpdateOwnerForm(@Valid final Owner owner, final BindingResult result) NEGATIVE TEST
	@WithMockUser(value = "jgarcia")
	@Test
	void testShouldNotProcessOwnerFormHtml2() throws Exception {
		mockMvc.perform(
			post("/owner/profile/edit").with(csrf())
				.param("firstName", "")
				.param("lastName", "García")
				.param("address", "Calle 1")
				.param("city", "Sevila")
				.param("telephone", "123")
				.param("user.username", "jgarcia")
				.param("user.password", "jgarcia"))
			.andExpect(model().attributeHasErrors("owner"))
			.andExpect(model().attributeHasFieldErrors("owner", "firstName"))
			.andExpect(status().isOk())
			.andExpect(view().name("owner/profileForm"));
	}

	// showOwnerPets -----------------------------------------------------------

	// showOwnerPets() POSITIVE TEST
	@WithMockUser(value = "jgarcia")
	@Test
	void testShowOwnerPetsHtml() throws Exception {
		mockMvc.perform(get("/owner/pets").with(csrf()))
			.andExpect(status().isOk())
			.andExpect(model().attributeExists("owner"))
			.andExpect(view().name("owner/petsList"));
		verify(stubOwnerService).findOwnerByUsername(any());
	}

	// showOwnerPets() NEGATIVE TEST
	// no mock user
	@Test
	void testShouldNotShowOwnerPetsHtml() throws Exception {
		mockMvc.perform(get("/owner/pets").with(csrf()))
			.andExpect(status().is4xxClientError());
	}

	// initCreationForm --------------------------------------------------------

	// initCreationForm(final ModelMap model) POSITIVE TEST
	@WithMockUser(value = "jgarcia")
	@Test
	void testInitCreationFormHtml() throws Exception {
		mockMvc.perform(get("/owner/pets/new").with(csrf()))
			.andExpect(status().isOk())
			.andExpect(model().attributeExists("pet"))
			.andExpect(view().name("owner/createOrUpdatePetForm"));
		verify(stubOwnerService).findOwnerByUsername(any());
	}

	// initCreationForm(final ModelMap model) NEGATIVE TEST
	// no mock user
	@Test
	void testShouldNotInitCreationFormHtml() throws Exception {
		mockMvc.perform(get("/owner/pets/new").with(csrf()))
			.andExpect(status().is4xxClientError());
	}

	// processCreationForm -----------------------------------------------------

	// processCreationForm(final ModelMap model) POSITIVE TEST
	@WithMockUser(value = "jgarcia")
	@Test
	void testProcessCreationFormHtml() throws Exception {
		mockMvc.perform(
			post("/owner/pets/new").with(csrf())
				.param("name", "Pepe")
				.param("birthDate", "2020/03/14"))
			.andExpect(status().is3xxRedirection())
			.andExpect(view().name("redirect:/owner/pets"));
		verify(stubOwnerService).findOwnerByUsername(any());
		verify(stubPetService).savePet(any());
	}

	// processCreationForm(final ModelMap model) NEGATIVE TEST
	// no mock user
	@Test
	void testShouldNotProcessCreationFormHtml() throws Exception {
		mockMvc.perform(post("/owner/pets/new").with(csrf()))
			.andExpect(status().is4xxClientError());
	}

	// processCreationForm(final ModelMap model) NEGATIVE TEST
	// birthDate is not a valid date
	@WithMockUser(value = "jgarcia")
	@Test
	void testShouldNotProcessCreationFormHtml2() throws Exception {
		mockMvc.perform(
			post("/owner/pets/new").with(csrf())
				.param("name", "Pepe")
				.param("birthDate", "texto"))
			.andExpect(model().attributeHasErrors("pet"))
			.andExpect(model().attributeHasFieldErrors("pet", "birthDate"))
			.andExpect(status().isOk())
			.andExpect(view().name("owner/createOrUpdatePetForm"));
	}

	// initUpdateForm ----------------------------------------------------------

	// initUpdateForm(@PathVariable("petId") final int petId, final ModelMap model) POSITIVE TEST
	@WithMockUser(value = "jgarcia")
	@Test
	void testInitUpdateFormHtml() throws Exception {
		mockMvc.perform(get("/owner/pets/{petId}/edit", 1).with(csrf()))//
			.andExpect(status().isOk())//
			.andExpect(model().attributeExists("pet"))//
			.andExpect(model().attributeExists("types"))//
			.andExpect(view().name("owner/createOrUpdatePetForm"));
		verify(stubPetService).findPetById(1);
	}

	// initUpdateForm(@PathVariable("petId") final int petId, final ModelMap model) NEGATIVE TEST
	// no mock user
	@Test
	void testShouldNotInitUpdateFormHtml() throws Exception {
		mockMvc.perform(get("/owner/pets/{petId}/edit", 1).with(csrf()))
			.andExpect(status().is4xxClientError());
	}

	// processUpdateForm -------------------------------------------------------

	// processUpdateForm(@Valid final Pet pet, final BindingResult result, @PathVariable("petId") final int petId, final ModelMap model) POSITIVE TEST
	@WithMockUser(value = "jgarcia")
	@Test
	void testProcessUpdateFormHtml() throws Exception {
		mockMvc.perform(
			post("/owner/pets/{petId}/edit", 1).with(csrf())
				.param("name", "Pepe")
				.param("birthDate", "2020/03/14"))
			.andExpect(status().is3xxRedirection())
			.andExpect(view().name("redirect:/owner/pets"));
		verify(stubPetService).findPetById(1);
		verify(stubPetService).savePet(any());
	}

	// iprocessUpdateForm(@Valid final Pet pet, final BindingResult result, @PathVariable("petId") final int petId, final ModelMap model) NEGATIVE TEST
	// no mock user
	@Test
	void testShouldNotProcessUpdateFormHtml() throws Exception {
		mockMvc.perform(post("/owner/pets/{petId}/edit", 1).with(csrf()))
			.andExpect(status().is4xxClientError());
	}

	// processUpdateForm(@Valid final Pet pet, final BindingResult result, @PathVariable("petId") final int petId, final ModelMap model) NEGATIVE TEST
	// birthDate is not a valid date
	@WithMockUser(value = "jgarcia")
	@Test
	void testShouldNotProcessUpdateFormHtml2() throws Exception {
		mockMvc.perform(
			post("/owner/pets/{petId}/edit", 1).with(csrf())
				.param("name", "Pepe")
				.param("birthDate", "texto"))
			.andExpect(model().attributeHasErrors("pet"))
			.andExpect(model().attributeHasFieldErrors("pet", "birthDate"))
			.andExpect(status().isOk())
			.andExpect(view().name("owner/createOrUpdatePetForm"));
	}

}
