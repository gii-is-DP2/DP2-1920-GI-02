
package org.group2.petclinic.web;

import java.text.ParseException;
import java.util.Collection;
import java.util.Locale;

import org.group2.petclinic.model.Pet;
import org.group2.petclinic.service.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

@Component
public class PetFormatter implements Formatter<Pet> {

	private final PetService petService;


	// CONSTRUCTOR ------------------------------------------------------------

	@Autowired
	public PetFormatter(final PetService petService) {
		this.petService = petService;
	}

	// PRINT ------------------------------------------------------------------

	@Override
	public String print(final Pet pet, final Locale locale) {
		return pet.getName();
	}

	// PARSE ------------------------------------------------------------------

	@Override
	public Pet parse(final String text, final Locale locale) throws ParseException {
		Collection<Pet> allPets = this.petService.findAllPets();
		for (Pet pet : allPets) {
			if (pet.getName().equals(text)) {
				return pet;
			}
		}
		throw new ParseException("pet not found: " + text, 0);
	}

}
