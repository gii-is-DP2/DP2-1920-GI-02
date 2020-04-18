
package org.group2.petclinic.unitTests.customasserts;

import java.util.Objects;

import org.assertj.core.api.AbstractAssert;
import org.group2.petclinic.model.Medicine;

public class MedicineAssert extends AbstractAssert<MedicineAssert, Medicine> {

	public MedicineAssert(Medicine actual) {
		super(actual, MedicineAssert.class);
	}

	public static MedicineAssert assertThat(Medicine actual) {
		return new MedicineAssert(actual);
	}

	// Custom asserts --------------------------------------------------------

	public MedicineAssert hasName(String name) {

		isNotNull();

		if (!Objects.equals(actual.getName(), name)) {
			failWithMessage("Expected medicine's name to be " + name + " but was " + actual.getName());
		}

		return this;
	}

	public MedicineAssert hasBrand(String brand) {

		isNotNull();

		if (!Objects.equals(actual.getBrand(), brand)) {
			failWithMessage("Expected medicine's brand to be " + brand + " but was " + actual.getBrand());
		}

		return this;
	}

	public MedicineAssert hasUsed(boolean used) {

		isNotNull();

		if (actual.isUsed() != used) {
			failWithMessage("Expected medicine's used to be " + used + " but was " + actual.isUsed());
		}

		return this;
	}

}
