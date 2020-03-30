package org.group2.petclinic.web;

import java.util.Collection;
import java.util.Map;

import javax.validation.Valid;

import org.group2.petclinic.model.Diagnosis;
import org.group2.petclinic.model.Medicine;
import org.group2.petclinic.model.Prescription;
import org.group2.petclinic.service.MedicineService;
import org.group2.petclinic.service.PetService;
import org.group2.petclinic.service.PrescriptionService;
import org.group2.petclinic.service.VisitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class PrescriptionController {

	private final PrescriptionService prescriptionService;
	private final VisitService visitService;
	private final MedicineService medicineService;

	@Autowired
	public PrescriptionController(PrescriptionService prescriptionService, VisitService visitService, MedicineService medicineService) {
		this.prescriptionService = prescriptionService;
		this.visitService = visitService;
		this.medicineService = medicineService;
	}

	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}


	@GetMapping(value = "/vet/visits/{visitId}/prescriptions/new")
	public String initNewPrescriptionForm(Map<String, Object> model) {
		Prescription prescription = new Prescription();
		model.put("prescription", prescription);
		return "vet/createOrUpdatePrescriptionForm";
	}

	@PostMapping(value = "/vet/visits/{visitId}/prescriptions/new")
	public String processNewPrescriptionForm(@PathVariable("visitId") int visitId, @Valid Prescription prescription, BindingResult result) {
		
		Diagnosis diagnosis = this.visitService.findVisitById(visitId).getDiagnosis();
		
		if (result.hasErrors()) {
			return "vet/createOrUpdatePrescriptionForm";
		}
		else {
			diagnosis.addPrescription(prescription);
			this.prescriptionService.savePrescription(prescription);
			return "redirect:/vet/visits/{visitId}";
		}
	}
	
	@ModelAttribute("medicines")
	public Collection<Medicine> populateMedicines() {
		return this.medicineService.findMedicines();
	}
	
}
