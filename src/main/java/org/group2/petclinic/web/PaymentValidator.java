
package org.group2.petclinic.web;

import org.group2.petclinic.model.Payment;
import org.group2.petclinic.service.PaymentService;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class PaymentValidator implements Validator {

	// SERVICES ---------------------------------------------------------------

	private PaymentService paymentService;


	// CONSTRUCTOR ------------------------------------------------------------

	public PaymentValidator(final PaymentService paymentService) {
		this.paymentService = paymentService;
	}

	// SUPPORTS ---------------------------------------------------------------

	@Override
	public boolean supports(final Class<?> clazz) {
		return Payment.class.isAssignableFrom(clazz);
	}

	// VALIDATE ---------------------------------------------------------------

	@Override
	public void validate(final Object obj, final Errors errors) {

		Payment payment = (Payment) obj;

		//Validate required fields
		if (payment.getFinalPrice() == null || StringUtils.isEmpty(payment.getFinalPrice())) {
			errors.rejectValue("finalPrice", "Final price is required.", "Final price is required.");
		}

		// Validate that finalPrice is a Positive
		if (!errors.hasFieldErrors("finalPrice")) {
			if (payment.getFinalPrice() <= 0) {
				errors.rejectValue("finalPrice", "Final price must be bigger than 0.", "Final price must be bigger than 0.");
			}
		}
	}

}
