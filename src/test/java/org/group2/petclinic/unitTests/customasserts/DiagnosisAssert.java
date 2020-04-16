
package org.group2.petclinic.unitTests.customasserts;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import org.assertj.core.api.AbstractAssert;
import org.group2.petclinic.model.Diagnosis;
import org.group2.petclinic.model.Prescription;

public class DiagnosisAssert extends AbstractAssert<DiagnosisAssert, Diagnosis> {

	public DiagnosisAssert(Diagnosis actual) {
		super(actual, DiagnosisAssert.class);
	}

	public static DiagnosisAssert assertThat(Diagnosis actual) {
		return new DiagnosisAssert(actual);
	}

	// Custom asserts --------------------------------------------------------

	public DiagnosisAssert hasDate(LocalDate date) {

		isNotNull();

		if (!Objects.equals(actual.getDate(), date)) {
			failWithMessage("Expected diagnosis's date to be " + date + " but was " + actual.getDate());
		}

		return this;
	}

	public DiagnosisAssert hasDescription(String description) {

		isNotNull();

		if (!Objects.equals(actual.getDescription(), description)) {
			failWithMessage("Expected diagnosis's description to be " + description + " but was "
				+ actual.getDescription());
		}

		return this;
	}

	public DiagnosisAssert hasPrescriptions(List<Prescription> prescriptions) {

		isNotNull();

		if (!Objects.equals(actual.getPrescriptions(), prescriptions)) {
			failWithMessage("Expected diagnosis's prescriptions to be " + prescriptions + " but was "
				+ actual.getPrescriptions());
		}

		return this;
	}

}
