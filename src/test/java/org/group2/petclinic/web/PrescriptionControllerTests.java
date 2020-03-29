package org.group2.petclinic.web;

/*
 * Copyright 2012-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.mockito.Mockito.mock;

import java.time.LocalDate;

import org.assertj.core.util.Lists;
import org.group2.petclinic.configuration.SecurityConfiguration;
import org.group2.petclinic.model.Diagnosis;
import org.group2.petclinic.model.Medicine;
import org.group2.petclinic.model.Owner;
import org.group2.petclinic.model.Pet;
import org.group2.petclinic.model.PetType;
import org.group2.petclinic.model.Prescription;
import org.group2.petclinic.model.Visit;
import org.group2.petclinic.service.DiagnosisService;
import org.group2.petclinic.service.MedicineService;
import org.group2.petclinic.service.OwnerService;
import org.group2.petclinic.service.PetService;
import org.group2.petclinic.service.PrescriptionService;
import org.group2.petclinic.service.VetService;
import org.group2.petclinic.service.VisitService;
import org.group2.petclinic.web.PetController;
import org.group2.petclinic.web.PetTypeFormatter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(value = PrescriptionController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class)
class PrescriptionControllerTests {

	private static final int TEST_VISIT_ID = 1;

	private static final int TEST_PRESCRIPTION_ID = 1;

	@Autowired
	private PrescriptionController prescripionController;

	@MockBean
	private PrescriptionService prescriptionService;

	@MockBean
	private VisitService visitService;

	@MockBean
	private MedicineService medicineService;

	@Autowired
	private MockMvc mockMvc;

	@BeforeEach
	void setup() {
		Visit visit = new Visit();
		visit.setId(1);
		given(this.visitService.findVisitById(visit.getId())).willReturn(new Visit());
	}

	@WithMockUser(value = "spring")
	@Test
	void testInitCreationForm() throws Exception {
		mockMvc.perform(get("/vet/visits/{visitId}/prescriptions/new", TEST_PRESCRIPTION_ID)).andExpect(status().isOk())
				.andExpect(view().name("vet/createOrUpdatePrescriptionForm"))
				.andExpect(model().attributeExists("prescription"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testProcessCreationFormSuccess() throws Exception {
		mockMvc.perform(post("/vet/visits/{visitId}/prescription/new", TEST_PRESCRIPTION_ID).with(csrf())
				.param("frequency", "2 times per week").param("duration", "1 week"));
	}

}
