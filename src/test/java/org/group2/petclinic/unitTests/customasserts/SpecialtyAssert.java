
package org.group2.petclinic.unitTests.customasserts;

import java.util.Objects;

import org.assertj.core.api.AbstractAssert;
import org.group2.petclinic.model.Specialty;

public class SpecialtyAssert extends AbstractAssert<SpecialtyAssert, Specialty> {

	public SpecialtyAssert(Specialty actual) {
		super(actual, SpecialtyAssert.class);
	}

	public static SpecialtyAssert assertThat(Specialty actual) {
		return new SpecialtyAssert(actual);
	}

	// Custom asserts --------------------------------------------------------

	public SpecialtyAssert hasProperty(String name) {

		isNotNull();

		if (!Objects.equals(actual.getName(), name)) {
			failWithMessage("Expected specialty's name to be " + name + " but was " + actual.getName());
		}

		return this;
	}

}
