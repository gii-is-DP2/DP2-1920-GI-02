
package org.group2.petclinic.unitTests.customasserts;

import java.util.Objects;

import org.assertj.core.api.AbstractAssert;
import org.group2.petclinic.model.PetType;

public class PetTypeAssert extends AbstractAssert<PetTypeAssert, PetType> {

	public PetTypeAssert(PetType actual) {
		super(actual, PetTypeAssert.class);
	}

	public static PetTypeAssert assertThat(PetType actual) {
		return new PetTypeAssert(actual);
	}

	// Custom asserts --------------------------------------------------------

	public PetTypeAssert hasName(String name) {

		isNotNull();

		if (!Objects.equals(actual.getName(), name)) {
			failWithMessage("Expected petType's name to be " + name + " but was " + actual.getName());
		}

		return this;
	}

}
