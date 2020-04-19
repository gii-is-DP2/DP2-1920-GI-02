
package org.group2.petclinic.unitTests.customasserts;

import java.time.LocalDateTime;
import java.util.Objects;

import org.assertj.core.api.AbstractAssert;
import org.group2.petclinic.model.Creditcard;
import org.group2.petclinic.model.Payment;
import org.group2.petclinic.model.Secretary;

public class PaymentAssert extends AbstractAssert<PaymentAssert, Payment> {

	public PaymentAssert(Payment actual) {
		super(actual, PaymentAssert.class);
	}

	public static PaymentAssert assertThat(Payment actual) {
		return new PaymentAssert(actual);
	}

	// Custom asserts --------------------------------------------------------

	public PaymentAssert hasMethod(String method) {

		isNotNull();

		if (!Objects.equals(actual.getMethod(), method)) {
			failWithMessage("Expected payment's method to be " + method + " but was " + actual.getMethod());
		}

		return this;
	}

	public PaymentAssert hasMoment(LocalDateTime moment) {

		isNotNull();

		if (!Objects.equals(actual.getMoment(), moment)) {
			failWithMessage("Expected payment's moment to be " + moment + " but was " + actual.getMoment());
		}

		return this;
	}

	public PaymentAssert hasFinalPrice(Double finalPrice) {

		isNotNull();

		if (!Objects.equals(actual.getFinalPrice(), finalPrice)) {
			failWithMessage(
				"Expected payment's finalPrice to be " + finalPrice + " but was " + actual.getFinalPrice());
		}

		return this;
	}

	public PaymentAssert hasSecretary(Secretary secretary) {

		isNotNull();

		if (!Objects.equals(actual.getSecretary(), secretary)) {
			failWithMessage(
				"Expected payment's secretary to be " + secretary + " but was " + actual.getSecretary());
		}

		return this;
	}

	public PaymentAssert hasCreditcard(Creditcard creditcard) {

		isNotNull();

		if (!Objects.equals(actual.getCreditcard(), creditcard)) {
			failWithMessage(
				"Expected payment's creditcard to be " + creditcard + " but was " + actual.getCreditcard());
		}

		return this;
	}

}
