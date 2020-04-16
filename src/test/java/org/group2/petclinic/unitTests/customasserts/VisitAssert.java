
package org.group2.petclinic.unitTests.customasserts;

import java.time.LocalDateTime;
import java.util.Objects;

import org.assertj.core.api.AbstractAssert;
import org.group2.petclinic.model.Diagnosis;
import org.group2.petclinic.model.Payment;
import org.group2.petclinic.model.Pet;
import org.group2.petclinic.model.Vet;
import org.group2.petclinic.model.Visit;
import org.group2.petclinic.model.VisitType;

public class VisitAssert extends AbstractAssert<VisitAssert, Visit> {

	public VisitAssert(Visit actual) {
		super(actual, VisitAssert.class);
	}

	public static VisitAssert assertThat(Visit actual) {
		return new VisitAssert(actual);
	}

	// Custom asserts --------------------------------------------------------

	public VisitAssert hasMoment(LocalDateTime moment) {

		isNotNull();

		if (!Objects.equals(actual.getMoment(), moment)) {
			failWithMessage("Expected visit's moment to be " + moment + " but was " + actual.getMoment());
		}

		return this;
	}

	public VisitAssert hasDescription(String description) {

		isNotNull();

		if (!Objects.equals(actual.getDescription(), description)) {
			failWithMessage(
				"Expected visit's description to be " + description + " but was " + actual.getDescription());
		}

		return this;
	}

	public VisitAssert hasPet(Pet pet) {

		isNotNull();

		if (!Objects.equals(actual.getPet(), pet)) {
			failWithMessage(
				"Expected visit's pet to be " + pet + " but was " + actual.getPet());
		}

		return this;
	}

	public VisitAssert hasVet(Vet vet) {

		isNotNull();

		if (!Objects.equals(actual.getVet(), vet)) {
			failWithMessage(
				"Expected visit's vet to be " + vet + " but was " + actual.getVet());
		}

		return this;
	}

	public VisitAssert hasVisitType(VisitType visitType) {

		isNotNull();

		if (!Objects.equals(actual.getVisitType(), visitType)) {
			failWithMessage(
				"Expected visit's visitType to be " + visitType + " but was " + actual.getVisitType());
		}

		return this;
	}

	public VisitAssert hasPayment(Payment payment) {

		isNotNull();

		if (!Objects.equals(actual.getPayment(), payment)) {
			failWithMessage(
				"Expected visit's payment to be " + payment + " but was " + actual.getPayment());
		}

		return this;
	}

	public VisitAssert hasDiagnosis(Diagnosis diagnosis) {

		isNotNull();

		if (!Objects.equals(actual.getDiagnosis(), diagnosis)) {
			failWithMessage(
				"Expected visit's diagnosis to be " + diagnosis + " but was " + actual.getDiagnosis());
		}

		return this;
	}
}
