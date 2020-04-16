
package org.group2.petclinic.unitTests.customasserts;

import java.util.Objects;

import org.assertj.core.api.AbstractAssert;
import org.group2.petclinic.model.VisitType;

public class VisitTypeAssert extends AbstractAssert<VisitTypeAssert, VisitType> {

	public VisitTypeAssert(VisitType actual) {
		super(actual, VisitTypeAssert.class);
	}

	public static VisitTypeAssert assertThat(VisitType actual) {
		return new VisitTypeAssert(actual);
	}

	// Custom asserts --------------------------------------------------------

	public VisitTypeAssert hasName(String name) {

		isNotNull();

		if (!Objects.equals(actual.getName(), name)) {
			failWithMessage("Expected visitType's name to be " + name + " but was " + actual.getName());
		}

		return this;
	}

	public VisitTypeAssert hasDuration(Integer duration) {

		isNotNull();

		if (!Objects.equals(actual.getDuration(), duration)) {
			failWithMessage(
				"Expected visitType's duration to be " + duration + " but was " + actual.getDuration());
		}

		return this;
	}

	public VisitTypeAssert hasPrice(Double price) {

		isNotNull();

		if (!Objects.equals(actual.getPrice(), price)) {
			failWithMessage("Expected visitType's price to be " + price + " but was " + actual.getPrice());
		}

		return this;
	}

}
