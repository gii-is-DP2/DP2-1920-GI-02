
package org.group2.petclinic.validator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

import java.time.LocalDateTime;

import org.group2.petclinic.model.Creditcard;
import org.group2.petclinic.model.Payment;
import org.group2.petclinic.model.Secretary;
import org.group2.petclinic.web.PaymentValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

public class PaymentValidatorTests {

	private PaymentValidator paymentValidator;

	// -------------------------- validate(final Object obj, final Errors errors) ---------------------------


	// POSITIVE TEST
	@Test
	void shoulAcceptPayment() {
		//1. Arrange
		Payment payment = new Payment();
		payment.setId(1);
		payment.setFinalPrice(30.00);
		payment.setCreditcard(mock(Creditcard.class));
		LocalDateTime date = LocalDateTime.now();
		date.minusMinutes(1);
		payment.setMoment(date);
		payment.setMethod("creditcard");
		payment.setSecretary(mock(Secretary.class));

		Errors errors = new BeanPropertyBindingResult(payment, "payment");

		this.paymentValidator = new PaymentValidator();

		//2. Act
		paymentValidator.validate(payment, errors);

		//3. Assert
		assertThat(errors.getErrorCount()).isEqualTo(0);
	}

	// NEGATIVE TEST
	// Final price is not included
	@Test
	void shouldRequireFinalPrice() {
		//1. Arrange
		Payment payment = new Payment();
		payment.setId(1);
		payment.setCreditcard(mock(Creditcard.class));
		LocalDateTime date = LocalDateTime.now();
		date.minusMinutes(1);
		payment.setMoment(date);
		payment.setMethod("creditcard");
		payment.setSecretary(mock(Secretary.class));

		Errors errors = new BeanPropertyBindingResult(payment, "payment");

		this.paymentValidator = new PaymentValidator();

		//2. Act
		paymentValidator.validate(payment, errors);

		//3. Assert
		assertThat(errors.getErrorCount()).isEqualTo(1);
		assertThat(errors.getFieldError("finalPrice")).isNotNull();
	}

	// NEGATIVE TEST
	// Final price is not bigger than 0
	@ParameterizedTest
	@ValueSource(doubles = {
		0.00, -1.00, -5.00, -10.00
	})
	void shouldFinalPriceNegative(Double finalPrice) {
		//1. Arrange
		Payment payment = new Payment();
		payment.setId(1);
		payment.setFinalPrice(finalPrice);
		payment.setCreditcard(mock(Creditcard.class));
		LocalDateTime date = LocalDateTime.now();
		date.minusMinutes(1);
		payment.setMoment(date);
		payment.setMethod("creditcard");
		payment.setSecretary(mock(Secretary.class));

		Errors errors = new BeanPropertyBindingResult(payment, "payment");

		this.paymentValidator = new PaymentValidator();

		//2. Act
		paymentValidator.validate(payment, errors);

		//3. Assert
		assertThat(errors.getErrorCount()).isEqualTo(1);
		assertThat(errors.getFieldError("finalPrice")).isNotNull();
	}

}
