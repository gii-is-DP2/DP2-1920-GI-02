package org.group2.petclinic.web;

import java.time.LocalDateTime;
import java.util.Map;

import javax.validation.Valid;

import org.group2.petclinic.model.Diagnosis;
import org.group2.petclinic.model.Visit;
import org.group2.petclinic.service.DiagnosisService;
import org.group2.petclinic.service.VisitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class DiagnosisController {

	private final DiagnosisService diagnosisService;
	private final VisitService visitService;

	@Autowired
	public DiagnosisController(DiagnosisService diagnosisService, VisitService visitService) {
		this.diagnosisService = diagnosisService;
		this.visitService = visitService;
	}

	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}

	@GetMapping(value = "/vet/visits/{visitId}/diagnosis/new")
	public String initNewDiagnosisForm(@PathVariable("visitId") int visitId, Map<String, Object> model) {
		Visit visit = this.visitService.findVisitById(visitId);
		LocalDateTime today = LocalDateTime.now();
		
		if(visit.getMoment().isAfter(today)) {
			return "/exception";
		}
		
		Diagnosis diagnosis = new Diagnosis();
		model.put("diagnosis", diagnosis);
		return "vet/createOrUpdateDiagnosisForm";
	}

	@PostMapping(value = "/vet/visits/{visitId}/diagnosis/new")
	public String processNewDiagnosisForm(@PathVariable("visitId") int visitId, @Valid Diagnosis diagnosis, BindingResult result) {
		
		Visit visit = this.visitService.findVisitById(visitId);
		
		if (result.hasErrors()) {
			return "vet/createOrUpdateDiagnosisForm";
		}
		else {
			visit.setDiagnosis(diagnosis);
			this.diagnosisService.saveDiagnosis(diagnosis);
			return "redirect:/vet/visits/{visitId}";
		}
	}
}
