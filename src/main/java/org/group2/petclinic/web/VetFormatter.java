
package org.group2.petclinic.web;

import java.text.ParseException;
import java.util.Collection;
import java.util.Locale;

import org.group2.petclinic.model.Vet;
import org.group2.petclinic.service.VetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

@Component
public class VetFormatter implements Formatter<Vet> {

	private final VetService vetService;


	// CONSTRUCTOR ------------------------------------------------------------

	@Autowired
	public VetFormatter(final VetService vetService) {
		this.vetService = vetService;
	}

	// PRINT ------------------------------------------------------------------

	@Override
	public String print(final Vet vet, final Locale locale) {
		return vet.getFirstName() + " " + vet.getLastName();
	}

	// PARSE ------------------------------------------------------------------

	@Override
	public Vet parse(final String text, final Locale locale) throws ParseException {
		Collection<Vet> findVets = this.vetService.findVets();
		for (Vet vet : findVets) {
			if ((vet.getFirstName() + " " + vet.getLastName()).equals(text)) {
				return vet;
			}
		}
		throw new ParseException("vet not found: " + text, 0);
	}

}
