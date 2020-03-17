
package org.group2.petclinic.web;

import java.text.ParseException;
import java.util.Collection;
import java.util.Locale;

import org.group2.petclinic.model.PetType;
import org.group2.petclinic.service.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

@Component
public class PetTypeFormatter implements Formatter<PetType> {

	private final PetService petService;


	// CONSTRUCTOR ------------------------------------------------------------

	@Autowired
	public PetTypeFormatter(final PetService petService) {
		this.petService = petService;
	}

	// PRINT ------------------------------------------------------------------

	@Override
	public String print(final PetType petType, final Locale locale) {
		return petType.getName();
	}

	// PARSE ------------------------------------------------------------------

	@Override
	public PetType parse(final String text, final Locale locale) throws ParseException {
		Collection<PetType> allPetTypes = this.petService.findPetTypes();
		for (PetType petType : allPetTypes) {
			if (petType.getName().equals(text)) {
				return petType;
			}
		}
		throw new ParseException("pet type not found: " + text, 0);
	}

}
