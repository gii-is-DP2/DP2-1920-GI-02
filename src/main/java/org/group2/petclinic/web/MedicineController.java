package org.group2.petclinic.web;

import java.util.Collection;

import org.group2.petclinic.model.Medicine;
import org.group2.petclinic.service.MedicineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class MedicineController {

	private final MedicineService medicineService;

	@Autowired
	public MedicineController(MedicineService medicineService) {
		this.medicineService = medicineService;
	}

	@ModelAttribute("medicines")
	public Collection<Medicine> populateMedicines() {
		return this.medicineService.findMedicines();
	}
	
}
