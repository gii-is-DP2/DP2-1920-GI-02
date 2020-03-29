package org.group2.petclinic.web;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.assertj.core.util.Lists;
import org.group2.petclinic.configuration.SecurityConfiguration;
import org.group2.petclinic.model.Pet;
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

@WebMvcTest(controllers=VisitController.class,
		excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
		excludeAutoConfiguration= SecurityConfiguration.class)
class VisitControllerTests {

	@Autowired
	private VisitController visitController;

	@MockBean
	private VisitService visitService;
	
	@MockBean
	private PetService petService;
	
	@MockBean
	private VetService vetService;
	
	@MockBean
	private OwnerService ownerService;

	@Autowired
	private MockMvc mockMvc;

	@BeforeEach
	void setup() {

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");
		
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
		
		given(this.visitService.findVisitsByVet(vet1)).willReturn(Lists.newArrayList(visit1, visit2));
	}
        
    @WithMockUser(value = "spring")
		@Test
	void testShowVisitListHtml() throws Exception {
		mockMvc.perform(get("/vet/visits")).andExpect(status().isOk()).andExpect(model().attributeExists("visits"))
				.andExpect(view().name("/vet/visitsList"));
	}	

}
