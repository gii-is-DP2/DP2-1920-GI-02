/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.group2.petclinic.web;

import java.util.Map;

import javax.validation.Valid;

import org.group2.petclinic.model.Diagnosis;
import org.group2.petclinic.model.Prescription;
import org.group2.petclinic.model.Visit;
import org.group2.petclinic.service.DiagnosisService;
import org.group2.petclinic.service.PetService;
import org.group2.petclinic.service.PrescriptionService;
import org.group2.petclinic.service.VisitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author Juergen Hoeller
 * @author Ken Krebs
 * @author Arjen Poutsma
 * @author Michael Isvy
 */
@Controller
public class PrescriptionController {

	private final PrescriptionService prescriptionService;
	private final DiagnosisService diagnosisService;
	private final VisitService visitService;

	@Autowired
	public PrescriptionController(PrescriptionService prescriptionService, DiagnosisService diagnosisService, VisitService visitService, PetService petService) {
		this.prescriptionService = prescriptionService;
		this.diagnosisService = diagnosisService;
		this.visitService = visitService;
	}

	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}

	/**
	 * Called before each and every @GetMapping or @PostMapping annotated method. 2 goals:
	 * - Make sure we always have fresh data - Since we do not use the session scope, make
	 * sure that Pet object always has an id (Even though id is not part of the form
	 * fields)
	 * @param petId
	 * @return Pet
	 */
	@ModelAttribute("prescription")
	public Prescription loadDiagnosisWithPrescription(@PathVariable("visitId") int visitId) {
		Diagnosis diagnosis = this.visitService.findVisitById(visitId).getDiagnosis();
		Prescription prescription = new Prescription();
		diagnosis.addPrescription(prescription);
		return prescription;
	}

	// Spring MVC calls method loadPetWithVisit(...) before initNewVisitForm is called
	@GetMapping(value = "/vet/visits/{visitId}/prescriptions/new")
	public String initNewPrescriptionForm(@PathVariable("visitId") int visitId, Map<String, Object> model) {
		return "vet/createOrUpdatePrescriptionForm";
	}

	// Spring MVC calls method loadPetWithVisit(...) before processNewVisitForm is called
	@PostMapping(value = "/vet/visits/{visitId}/prescriptions/new")
	public String processNewPrescriptionForm(@Valid Prescription prescription, BindingResult result) {
		if (result.hasErrors()) {
			return "vet/createOrUpdatePrescriptionForm";
		}
		else {
			this.prescriptionService.savePrescription(prescription);
			return "redirect:/vet/visits/{visitId}";
		}
	}

	
	/*@GetMapping("/vet/visits/{visitId}/diagnosis/{diagnosisId}")
	public ModelAndView showDiagnosis(@PathVariable("diagnosisId") int diagnosisId) {
		ModelAndView mav = new ModelAndView("diagnostics/diagnosisDetails");
		Diagnosis diagnosis = this.diagnosisService.findDiagnosisById(diagnosisId);
		mav.addObject(diagnosis);
		return mav;
	}*/
	
}
