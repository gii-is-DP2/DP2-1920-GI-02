
package org.group2.petclinic.web;

import java.time.LocalDate;
import java.util.regex.Pattern;

import org.group2.petclinic.model.Creditcard;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class CreditcardValidator implements Validator {

	// SERVICES ---------------------------------------------------------------

	// CONSTRUCTOR ------------------------------------------------------------

	// SUPPORTS ---------------------------------------------------------------

	@Override
	public boolean supports(final Class<?> clazz) {
		return Creditcard.class.isAssignableFrom(clazz);
	}

	// VALIDATE ---------------------------------------------------------------

	@Override
	public void validate(final Object obj, final Errors errors) {

		Creditcard creditcard = (Creditcard) obj;

		//Validate required fields
		if (creditcard.getHolder() == null || StringUtils.isEmpty(creditcard.getHolder())) {
			errors.rejectValue("holder", "Holder is required.", "Holder is required.");
		}

		if (creditcard.getBrand() == null || StringUtils.isEmpty(creditcard.getBrand())) {
			errors.rejectValue("brand", "Brand is required.", "Brand is required.");
		}

		if (creditcard.getNumber() == null || StringUtils.isEmpty(creditcard.getNumber())) {
			errors.rejectValue("number", "Number is required.", "Number is required.");
		}

		if (creditcard.getExpYear() == null || StringUtils.isEmpty(creditcard.getExpYear())) {
			errors.rejectValue("expYear", "Expiration year is required.", "Expiration year is required.");
		}

		if (creditcard.getSecurityCode() == null || StringUtils.isEmpty(creditcard.getSecurityCode())) {
			errors.rejectValue("securityCode", "Security code is required.", "Security code is required.");
		}

		// Validate that expiration date is valid
		if (!errors.hasFieldErrors("expYear")) {

			// Year format with 2 digits
			if (!((creditcard.getExpYear() + "").length() == 2)) {
				errors.rejectValue("expYear", "Expiration year must be two digits between 00 and 99.", "Expiration year must be two digits between 00 and 99.");
			}

			String stringFecha = "20" + creditcard.getExpYear();
			Integer integerYear = Integer.parseInt(stringFecha);
			LocalDate fechaString = LocalDate.of(integerYear, creditcard.getExpMonth(), 28);
			LocalDate actual = LocalDate.now();

			// Expiration date
			if (fechaString.isBefore(actual)) {
				errors.rejectValue("expYear", "Expirated date.", "Expirated date.");
				errors.rejectValue("expMonth", "Expirated date.", "Expirated date.");
			}
		}

		// Validate that security code is a Integer
		if (!errors.hasFieldErrors("securityCode")) {
			Boolean booleano = false;
			for (int i = 0; i < creditcard.getSecurityCode().length(); i++) {
				char letra = creditcard.getSecurityCode().charAt(i);
				if (Character.isLetter(letra)) {
					booleano = true;
				}
			}

			if (booleano) {
				errors.rejectValue("securityCode", "Security code must be a int with 3 or 4 digits.", "Security code must be a int with 3 or 4 digits.");
			}

			if (creditcard.getSecurityCode().length() < 3 || creditcard.getSecurityCode().length() > 4) {
				errors.rejectValue("securityCode", "Security code must be a int with 3 or 4 digits.", "Security code must be a int with 3 or 4 digits.");
			}
		}

		// Validate the number creditcard
		// Number valid
		if (!errors.hasFieldErrors("number") && !(Pattern.matches("^4[0-9]{12}(?:[0-9]{3}){0,2}$", creditcard.getNumber()) == true) && !(Pattern.matches("^(?:5[1-5]|2(?!2([01]|20)|7(2[1-9]|3))[2-7])\\d{14}$", creditcard.getNumber()) == true)
			&& !(Pattern.matches("^3[47][0-9]{13}$", creditcard.getNumber()) == true) && !(Pattern.matches("^6(?:011|[45][0-9]{2})[0-9]{12}$", creditcard.getNumber()) == true)
			&& !(Pattern.matches("^(?:2131|1800|35\\d{3})\\d{11}$", creditcard.getNumber()) == true)) {
			errors.rejectValue("number", "Number is not valid.", "Number is not valid.");
		}
	}

}
