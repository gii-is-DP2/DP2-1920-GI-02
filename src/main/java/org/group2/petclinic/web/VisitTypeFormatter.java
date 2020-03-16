
package org.group2.petclinic.web;

import java.text.ParseException;
import java.util.Collection;
import java.util.Locale;

import org.group2.petclinic.model.VisitType;
import org.group2.petclinic.service.VisitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

@Component
public class VisitTypeFormatter implements Formatter<VisitType> {

	private final VisitService visitService;


	// CONSTRUCTOR ------------------------------------------------------------

	@Autowired
	public VisitTypeFormatter(final VisitService visitService) {
		this.visitService = visitService;
	}

	// PRINT ------------------------------------------------------------------

	@Override
	public String print(final VisitType visitType, final Locale locale) {
		return visitType.getName();
	}

	// PARSE ------------------------------------------------------------------

	@Override
	public VisitType parse(final String text, final Locale locale) throws ParseException {
		Collection<VisitType> allVisitTypes = this.visitService.findVisitTypes();
		for (VisitType visitType : allVisitTypes) {
			if (visitType.getName().equals(text)) {
				return visitType;
			}
		}
		throw new ParseException("visit type not found: " + text, 0);
	}

}
