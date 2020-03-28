package org.group2.petclinic.web;

import java.util.Collection;
import java.util.Map;

import javax.validation.Valid;

import org.group2.petclinic.model.Medicine;
import org.group2.petclinic.model.Vet;
import org.group2.petclinic.model.Visit;
import org.group2.petclinic.service.MedicineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties.Admin;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MedicineController {

	private final MedicineService medicineService;

	@Autowired
	public MedicineController(MedicineService medicineService) {
		this.medicineService = medicineService;
	}

	@InitBinder
	public void setAllowedFields(final WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}
	
	@GetMapping(value = "/admin/medicines")
	public String showMedicines(final ModelMap modelMap) {
		String view = "/admin/medicinesList";
		Iterable<Medicine> medicines = this.medicineService.findMedicines();
		modelMap.addAttribute("medicines", medicines);
		return view;
	}
	
	@GetMapping(value = "/admin/medicines/new")
	public String initNewMedicineForm(Map<String, Object> model) {
		Medicine medicine = new Medicine();
		model.put("medicine", medicine);
		return "admin/createOrUpdateMedicineForm";
	}

	@PostMapping(value = "/admin/medicines/new")
	public String processNewMedicineForm(@Valid Medicine medicine, BindingResult result) {
		
		if (result.hasErrors()) {
			return "admin/createOrUpdateMedicineForm";
		}
		else {
			this.medicineService.saveMedicine(medicine);
			return "redirect:/admin/medicines";
		}
	}

	@ModelAttribute("medicines")
	public Collection<Medicine> populateMedicines() {
		return this.medicineService.findMedicines();
	}

}
