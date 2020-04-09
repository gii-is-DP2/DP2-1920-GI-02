package org.group2.petclinic.web;

import java.util.Collection;
import java.util.Map;

import javax.validation.Valid;

import org.group2.petclinic.model.Medicine;
import org.group2.petclinic.service.MedicineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
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
		return "admin/createMedicineForm";
	}

	@PostMapping(value = "/admin/medicines/new")
	public String processNewMedicineForm(@Valid Medicine medicine, BindingResult result) {
		
		if (result.hasErrors()) {
			return "admin/createMedicineForm";
		}
		else {
			this.medicineService.saveMedicine(medicine);
			return "redirect:/admin/medicines";
		}
	}
	
	@GetMapping(value = "/admin/medicines/{medicineId}/edit")
	public String initUpdateMedicineForm(@PathVariable("medicineId") int medicineId, ModelMap model) {
		Medicine medicine = this.medicineService.findMedicineById(medicineId);
		model.put("medicine", medicine);
		return "admin/updateMedicineForm";
	}

	@PostMapping(value = "/admin/medicines/{medicineId}/edit")
	public String processUpdateMedicineForm(@Valid Medicine medicine, BindingResult result, @PathVariable("medicineId") final int medicineId, ModelMap model) {
		
		if (result.hasErrors()) {
			model.put("medicine", medicine);
			return "admin/updateMedicineForm";
		}
		else {
			medicine.setId(medicineId);
			medicine.setUsed(this.medicineService.isInUse(medicine));
			this.medicineService.saveMedicine(medicine);
			return "redirect:/admin/medicines";
		}
	}
	
	@GetMapping("/admin/medicines/{medicineId}/delete")
	public String deleteMedicine(@PathVariable("medicineId") int medicineId, Model model) {
		
		Medicine medicine = this.medicineService.findMedicineById(medicineId);
		try {
			this.medicineService.deleteMedicine(medicine);
		}catch(Exception e){
			return "exception";
		}
		
		return "redirect:/admin/medicines";
	}

	
	

	@ModelAttribute("medicines")
	public Collection<Medicine> populateMedicines() {
		return this.medicineService.findMedicines();
	}

}
