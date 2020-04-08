package org.group2.petclinic.web;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.group2.petclinic.model.Medicine;
import org.group2.petclinic.model.Prescription;
import org.group2.petclinic.model.Vet;
import org.group2.petclinic.model.Visit;
import org.group2.petclinic.service.MedicineService;
import org.group2.petclinic.service.PrescriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties.Admin;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
	public String processUpdateMedicineForm(@Valid Medicine medicine, BindingResult result, ModelMap model) {
		
		if (result.hasErrors()) {
			model.put("medicine", medicine);
			return "admin/updateMedicineForm";
		}
		else {
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
