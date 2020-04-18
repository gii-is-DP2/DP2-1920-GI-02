
package org.group2.petclinic.unitTests.customasserts;

import java.util.List;
import java.util.Objects;

import org.assertj.core.api.AbstractAssert;
import org.group2.petclinic.model.Owner;
import org.group2.petclinic.model.Pet;

public class OwnerAssert extends AbstractAssert<OwnerAssert, Owner> {

	public OwnerAssert(Owner actual) {
		super(actual, OwnerAssert.class);
	}

	public static OwnerAssert assertThat(Owner actual) {
		return new OwnerAssert(actual);
	}

	// Custom asserts --------------------------------------------------------

	public OwnerAssert hasAddress(String address) {

		isNotNull();

		if (!Objects.equals(actual.getAddress(), address)) {
			failWithMessage("Expected owner's address to be " + address + " but was " + actual.getAddress());
		}

		return this;
	}

	public OwnerAssert hasCity(String city) {

		isNotNull();

		if (!Objects.equals(actual.getCity(), city)) {
			failWithMessage("Expected owner's city to be " + city + " but was " + actual.getCity());
		}

		return this;
	}

	public OwnerAssert hasTelephone(String telephone) {

		isNotNull();

		if (!Objects.equals(actual.getTelephone(), telephone)) {
			failWithMessage(
				"Expected owner's telephone to be " + telephone + " but was " + actual.getTelephone());
		}

		return this;
	}

	public OwnerAssert hasPets(List<Pet> pets) {

		isNotNull();

		if (!Objects.equals(actual.getPets(), pets)) {
			failWithMessage("Expected owner's pets to be " + pets + " but was " + actual.getPets());
		}

		return this;
	}

}
