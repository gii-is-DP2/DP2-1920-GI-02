
package org.group2.petclinic.unitTests.customasserts;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import org.assertj.core.api.AbstractAssert;
import org.group2.petclinic.model.Owner;
import org.group2.petclinic.model.Pet;
import org.group2.petclinic.model.PetType;
import org.group2.petclinic.model.Visit;

public class PetAssert extends AbstractAssert<PetAssert, Pet> {

	public PetAssert(Pet actual) {
		super(actual, PetAssert.class);
	}

	public static PetAssert assertThat(Pet actual) {
		return new PetAssert(actual);
	}

	// Custom asserts --------------------------------------------------------

	public PetAssert hasBirthDate(LocalDate birthDate) {

		isNotNull();

		if (!Objects.equals(actual.getBirthDate(), birthDate)) {
			failWithMessage(
				"Expected pet's birthDate to be " + birthDate + " but was " + actual.getBirthDate());
		}

		return this;
	}

	public PetAssert hasType(PetType type) {

		isNotNull();

		if (!Objects.equals(actual.getType(), type)) {
			failWithMessage("Expected pet's type to be " + type + " but was " + actual.getType());
		}

		return this;
	}

	public PetAssert hasOwner(Owner owner) {

		isNotNull();

		if (!Objects.equals(actual.getOwner(), owner)) {
			failWithMessage("Expected pet's owner to be " + owner + " but was " + actual.getOwner());
		}

		return this;
	}

	public PetAssert hasVisits(List<Visit> visits) {

		isNotNull();

		if (!Objects.equals(actual.getVisits(), visits)) {
			failWithMessage("Expected pet's visits to be " + visits + " but was " + actual.getVisits());
		}

		return this;
	}

}
