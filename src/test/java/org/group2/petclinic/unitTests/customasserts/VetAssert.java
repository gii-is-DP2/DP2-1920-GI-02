
package org.group2.petclinic.unitTests.customasserts;

import java.util.List;
import java.util.Objects;

import org.assertj.core.api.AbstractAssert;
import org.group2.petclinic.model.Specialty;
import org.group2.petclinic.model.Vet;

public class VetAssert extends AbstractAssert<VetAssert, Vet> {

	public VetAssert(Vet actual) {
		super(actual, VetAssert.class);
	}

	public static VetAssert assertThat(Vet actual) {
		return new VetAssert(actual);
	}

	// Custom asserts --------------------------------------------------------

	public VetAssert hasSpecialties(List<Specialty> specialties) {

		isNotNull();

		if (!Objects.equals(actual.getSpecialties(), specialties)) {
			failWithMessage(
				"Expected vet's specialties to be " + specialties + " but was " + actual.getSpecialties());
		}

		return this;
	}

}
