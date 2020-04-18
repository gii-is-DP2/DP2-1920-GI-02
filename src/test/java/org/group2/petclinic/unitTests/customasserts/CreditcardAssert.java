
package org.group2.petclinic.unitTests.customasserts;

import java.util.Objects;

import org.assertj.core.api.AbstractAssert;
import org.group2.petclinic.model.Creditcard;

public class CreditcardAssert extends AbstractAssert<CreditcardAssert, Creditcard> {

	public CreditcardAssert(Creditcard actual) {
		super(actual, CreditcardAssert.class);
	}

	public static CreditcardAssert assertThat(Creditcard actual) {
		return new CreditcardAssert(actual);
	}

	// Custom asserts --------------------------------------------------------

	public CreditcardAssert hasHolder(String holder) {

		isNotNull();

		if (!Objects.equals(actual.getHolder(), holder)) {
			failWithMessage(
				"Expected creditcard's holder to be " + holder + " but was " + actual.getHolder());
		}

		return this;
	}

	public CreditcardAssert hasBrand(String brand) {

		isNotNull();

		if (!Objects.equals(actual.getBrand(), brand)) {
			failWithMessage("Expected creditcard's brand to be " + brand + " but was " + actual.getBrand());
		}

		return this;
	}

	public CreditcardAssert hasNumber(String number) {

		isNotNull();

		if (!Objects.equals(actual.getNumber(), number)) {
			failWithMessage(
				"Expected creditcard's number to be " + number + " but was " + actual.getNumber());
		}

		return this;
	}

	public CreditcardAssert hasExpMonth(Integer expMonth) {

		isNotNull();

		if (!Objects.equals(actual.getExpMonth(), expMonth)) {
			failWithMessage(
				"Expected creditcard's expMonth to be " + expMonth + " but was " + actual.getExpMonth());
		}

		return this;
	}

	public CreditcardAssert hasExpYear(Integer expYear) {

		isNotNull();

		if (!Objects.equals(actual.getExpYear(), expYear)) {
			failWithMessage(
				"Expected creditcard's expYear to be " + expYear + " but was " + actual.getExpYear());
		}

		return this;
	}

	public CreditcardAssert hasSecurityCode(String securityCode) {

		isNotNull();

		if (!Objects.equals(actual.getSecurityCode(), securityCode)) {
			failWithMessage("Expected creditcard's securityCode to be " + securityCode + " but was "
				+ actual.getSecurityCode());
		}

		return this;
	}

}
