
package org.group2.petclinic.unitTests.customasserts;

import java.util.Objects;

import org.assertj.core.api.AbstractAssert;
import org.group2.petclinic.model.Medicine;
import org.group2.petclinic.model.Prescription;

public class PrescriptionAssert extends AbstractAssert<PrescriptionAssert, Prescription> {

	public PrescriptionAssert(Prescription actual) {
		super(actual, PrescriptionAssert.class);
	}

	public static PrescriptionAssert assertThat(Prescription actual) {
		return new PrescriptionAssert(actual);
	}

	// Custom asserts --------------------------------------------------------

	public PrescriptionAssert hasFrequency(String frecuency) {

		isNotNull();

		if (!Objects.equals(actual.getFrequency(), frecuency)) {
			failWithMessage(
				"Expected prescription's frecuency to be " + frecuency + " but was " + actual.getFrequency());
		}

		return this;
	}

	public PrescriptionAssert hasDuration(String duration) {

		isNotNull();

		if (!Objects.equals(actual.getDuration(), duration)) {
			failWithMessage(
				"Expected prescription's duration to be " + duration + " but was " + actual.getDuration());
		}

		return this;
	}

	public PrescriptionAssert hasMedicine(Medicine medicine) {

		isNotNull();

		if (!Objects.equals(actual.getMedicine(), medicine)) {
			failWithMessage(
				"Expected prescription's medicine to be " + medicine + " but was " + actual.getMedicine());
		}

		return this;
	}

}
