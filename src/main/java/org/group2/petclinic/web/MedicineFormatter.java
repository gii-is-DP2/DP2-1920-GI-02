
package org.group2.petclinic.web;

import java.text.ParseException;
import java.util.Collection;
import java.util.Locale;

import org.group2.petclinic.model.Medicine;
import org.group2.petclinic.service.MedicineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

@Component
public class MedicineFormatter implements Formatter<Medicine> {

	private final MedicineService medicineService;


	// CONSTRUCTOR ------------------------------------------------------------

	@Autowired
	public MedicineFormatter(final MedicineService medicineService) {
		this.medicineService = medicineService;
	}

	// PRINT ------------------------------------------------------------------

	@Override
	public String print(final Medicine medicine, final Locale locale) {
		return medicine.getName();
	}

	// PARSE ------------------------------------------------------------------

	@Override
	public Medicine parse(final String text, final Locale locale) throws ParseException {
		Collection<Medicine> allMedicines = this.medicineService.findMedicines();
		for (Medicine medicine : allMedicines) {
			if (medicine.getName().equals(text)) {
				return medicine;
			}
		}
		throw new ParseException("medicine not found: " + text, 0);
	}

}
