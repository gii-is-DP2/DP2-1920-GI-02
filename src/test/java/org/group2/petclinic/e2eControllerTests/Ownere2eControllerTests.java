
package org.group2.petclinic.e2eControllerTests;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import javax.transaction.Transactional;

import org.group2.petclinic.web.OwnerController;
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
class Ownere2eControllerTests {

	@Autowired
	private OwnerController	OwnerController;

	@Autowired
	private MockMvc			mockMvc;

	// showOwnerInfo -----------------------------------------------------------


	// showOwnerInfo() POSITIVE TEST
	@Transactional
	@WithMockUser(username = "gfranklin", authorities = {
		"owner"
	})
	@Test
	void testShowOwnerInfoHtml() throws Exception {
		mockMvc.perform(get("/owner/profile").with(csrf())).andExpect(status().isOk()).andExpect(model().attributeExists("owner")).andExpect(view().name("owner/profileView"));
	}

	// showOwnerInfo() NEGATIVE TEST
	// admin doesn't have access
	@Transactional
	@WithMockUser(username = "admin1", authorities = {
		"admin"
	})
	@Test
	void testShouldNotShowOwnerInfoHtml() throws Exception {
		mockMvc.perform(get("/owner/profile").with(csrf())).andExpect(status().is4xxClientError());
	}

	// initUpdateOwnerForm -----------------------------------------------------

	// initUpdateOwnerForm(final Model model) POSITIVE TEST
	@Transactional
	@WithMockUser(username = "gfranklin", authorities = {
		"owner"
	})
	@Test
	void testInitUpdateOwnerFormHtml() throws Exception {
		mockMvc.perform(get("/owner/profile/edit").with(csrf())).andExpect(status().isOk()).andExpect(model().attributeExists("owner")).andExpect(view().name("owner/profileForm"));
	}

	// initUpdateOwnerForm(final Model model) NEGATIVE TEST
	// admin doesn't have access
	@Transactional
	@WithMockUser(username = "admin1", authorities = {
		"admin"
	})
	@Test
	void testShouldNotInitUpdateOwnerFormHtml() throws Exception {
		mockMvc.perform(get("/owner/profile/edit").with(csrf())).andExpect(status().is4xxClientError());
	}

	// processUpdateOwnerForm --------------------------------------------------

	// processUpdateOwnerForm(@Valid final Owner owner, final BindingResult result) POSITIVE TEST
	@Transactional
	@WithMockUser(username = "gfranklin", authorities = {
		"owner"
	})
	@Test
	void testProcessOwnerFormHtml() throws Exception {
		mockMvc.perform(
			post("/owner/profile/edit").with(csrf()).param("firstName", "José").param("lastName", "García").param("address", "Calle 1").param("city", "Sevila").param("telephone", "123").param("user.username", "gfranklin").param("user.password", "jgarcia"))
			.andExpect(status().is3xxRedirection()).andExpect(view().name("redirect:/owner/profile"));
	}

	// processUpdateOwnerForm(@Valid final Owner owner, final BindingResult result) NEGATIVE TEST
	// admin doesn't have access
	@Transactional
	@WithMockUser(username = "admin1", authorities = {
		"admin"
	})
	@Test
	void testShouldNotProcessOwnerFormHtml() throws Exception {
		mockMvc.perform(post("/owner/profile/edit").with(csrf())).andExpect(status().is4xxClientError());
	}

	// processUpdateOwnerForm(@Valid final Owner owner, final BindingResult result) NEGATIVE TEST
	// firstName cannot be empty
	@Transactional
	@WithMockUser(username = "gfranklin", authorities = {
		"owner"
	})
	@Test
	void testShouldNotProcessOwnerFormHtml2() throws Exception {
		mockMvc
			.perform(
				post("/owner/profile/edit").with(csrf()).param("firstName", "").param("lastName", "García").param("address", "Calle 1").param("city", "Sevila").param("telephone", "123").param("user.username", "jgarcia").param("user.password", "jgarcia"))
			.andExpect(model().attributeHasErrors("owner")).andExpect(model().attributeHasFieldErrors("owner", "firstName")).andExpect(status().isOk()).andExpect(view().name("owner/profileForm"));
	}

	// showOwnerPets -----------------------------------------------------------

	// showOwnerPets() POSITIVE TEST
	@Transactional
	@WithMockUser(username = "gfranklin", authorities = {
		"owner"
	})
	@Test
	void testShowOwnerPetsHtml() throws Exception {
		mockMvc.perform(get("/owner/pets").with(csrf())).andExpect(status().isOk()).andExpect(model().attributeExists("owner")).andExpect(view().name("owner/petsList"));
	}

	// showOwnerPets() NEGATIVE TEST
	// admin doesn't have access
	@Transactional
	@WithMockUser(username = "admin1", authorities = {
		"admin"
	})
	@Test
	void testShouldNotShowOwnerPetsHtml() throws Exception {
		mockMvc.perform(get("/owner/pets").with(csrf())).andExpect(status().is4xxClientError());
	}

	// initCreationForm --------------------------------------------------------

	// initCreationForm(final ModelMap model) POSITIVE TEST
	@Transactional
	@WithMockUser(username = "gfranklin", authorities = {
		"owner"
	})
	@Test
	void testInitCreationFormHtml() throws Exception {
		mockMvc.perform(get("/owner/pets/new").with(csrf())).andExpect(status().isOk()).andExpect(model().attributeExists("pet")).andExpect(view().name("owner/createOrUpdatePetForm"));
	}

	// initCreationForm(final ModelMap model) NEGATIVE TEST
	// admin doesn't have access
	@Transactional
	@WithMockUser(username = "admin1", authorities = {
		"admin"
	})
	@Test
	void testShouldNotInitCreationFormHtml() throws Exception {
		mockMvc.perform(get("/owner/pets/new").with(csrf())).andExpect(status().is4xxClientError());
	}

	// processCreationForm -----------------------------------------------------

	// processCreationForm(final ModelMap model) POSITIVE TEST
	@Transactional
	@WithMockUser(username = "gfranklin", authorities = {
		"owner"
	})
	@Test
	void testProcessCreationFormHtml() throws Exception {
		mockMvc.perform(post("/owner/pets/new").with(csrf()).param("name", "Pepe").param("birthDate", "2020/03/14")).andExpect(status().is3xxRedirection()).andExpect(view().name("redirect:/owner/pets"));
	}

	// processCreationForm(final ModelMap model) NEGATIVE TEST
	// admin doesn't have access
	@Transactional
	@WithMockUser(username = "admin1", authorities = {
		"admin"
	})
	@Test
	void testShouldNotProcessCreationFormHtml() throws Exception {
		mockMvc.perform(post("/owner/pets/new").with(csrf())).andExpect(status().is4xxClientError());
	}

	// processCreationForm(final ModelMap model) NEGATIVE TEST
	// birthDate is not a valid date
	@Transactional
	@WithMockUser(username = "gfranklin", authorities = {
		"owner"
	})
	@Test
	void testShouldNotProcessCreationFormHtml2() throws Exception {
		mockMvc.perform(post("/owner/pets/new").with(csrf()).param("name", "Pepe").param("birthDate", "texto")).andExpect(model().attributeHasErrors("pet")).andExpect(model().attributeHasFieldErrors("pet", "birthDate")).andExpect(status().isOk())
			.andExpect(view().name("owner/createOrUpdatePetForm"));
	}

	// initUpdateForm ----------------------------------------------------------

	// initUpdateForm(@PathVariable("petId") final int petId, final ModelMap model) POSITIVE TEST
	@Transactional
	@WithMockUser(username = "gfranklin", authorities = {
		"owner"
	})
	@Test
	void testInitUpdateFormHtml() throws Exception {
		mockMvc.perform(get("/owner/pets/{petId}/edit", 1).with(csrf()))//
			.andExpect(status().isOk())//
			.andExpect(model().attributeExists("pet"))//
			.andExpect(model().attributeExists("types"))//
			.andExpect(view().name("owner/createOrUpdatePetForm"));
	}

	// initUpdateForm(@PathVariable("petId") final int petId, final ModelMap model) NEGATIVE TEST
	// admin doesn't have access
	@Transactional
	@WithMockUser(username = "admin1", authorities = {
		"admin"
	})
	@Test
	void testShouldNotInitUpdateFormHtml() throws Exception {
		mockMvc.perform(get("/owner/pets/{petId}/edit", 1).with(csrf())).andExpect(status().is4xxClientError());
	}

	// processUpdateForm -------------------------------------------------------

	// processUpdateForm(@Valid final Pet pet, final BindingResult result, @PathVariable("petId") final int petId, final ModelMap model) POSITIVE TEST
	@Transactional
	@WithMockUser(username = "gfranklin", authorities = {
		"owner"
	})
	@Test
	void testProcessUpdateFormHtml() throws Exception {
		mockMvc.perform(post("/owner/pets/{petId}/edit", 1).with(csrf()).param("name", "Pepito").param("birthDate", "2020/03/14").param("type", "cat")).andExpect(status().is3xxRedirection()).andExpect(view().name("redirect:/owner/pets"));
	}

	// processUpdateForm(@Valid final Pet pet, final BindingResult result, @PathVariable("petId") final int petId, final ModelMap model) NEGATIVE TEST
	// admin doesn't have access
	@Transactional
	@WithMockUser(username = "admin1", authorities = {
		"admin"
	})
	@Test
	void testShouldNotProcessUpdateFormHtml() throws Exception {
		mockMvc.perform(post("/owner/pets/{petId}/edit", 1).with(csrf())).andExpect(status().is4xxClientError());
	}

	// processUpdateForm(@Valid final Pet pet, final BindingResult result, @PathVariable("petId") final int petId, final ModelMap model) NEGATIVE TEST
	// birthDate is not a valid date
	@Transactional
	@WithMockUser(username = "gfranklin", authorities = {
		"owner"
	})
	@Test
	void testShouldNotProcessUpdateFormHtml2() throws Exception {
		mockMvc.perform(post("/owner/pets/{petId}/edit", 1).with(csrf()).param("name", "Pepe").param("birthDate", "texto")).andExpect(model().attributeHasErrors("pet")).andExpect(model().attributeHasFieldErrors("pet", "birthDate"))
			.andExpect(status().isOk()).andExpect(view().name("owner/createOrUpdatePetForm"));
	}

}
