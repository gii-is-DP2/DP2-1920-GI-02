
package org.group2.petclinic.web;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.group2.petclinic.configuration.SecurityConfiguration;
import org.group2.petclinic.model.Owner;
import org.group2.petclinic.model.User;
import org.group2.petclinic.service.AuthoritiesService;
import org.group2.petclinic.service.OwnerService;
import org.group2.petclinic.service.PetService;
import org.group2.petclinic.service.UserService;
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

@WebMvcTest(controllers = OwnerController.class,//
	excludeFilters = @ComponentScan.Filter(//
		type = FilterType.ASSIGNABLE_TYPE,//
		classes = WebSecurityConfigurer.class),//
	excludeAutoConfiguration = SecurityConfiguration.class)
class OwnerControllerTests {

	@Autowired
	private OwnerController		ownerController;

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

		given(this.stubOwnerService.findOwnerByUsername("jgarcia")).willReturn(owner);
	}

	@WithMockUser(value = "jgarcia")
	@Test
	void testShowOwnerInfoHtml() throws Exception {
		mockMvc.perform(get("/owner/profile").with(csrf())).andExpect(status().isOk()).andExpect(model().attributeExists("owner")).andExpect(view().name("owner/profileView"));
		verify(stubOwnerService).findOwnerByUsername(any());
	}

}
