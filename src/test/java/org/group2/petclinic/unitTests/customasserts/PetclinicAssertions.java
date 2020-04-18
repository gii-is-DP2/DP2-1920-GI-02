
package org.group2.petclinic.unitTests.customasserts;

import org.group2.petclinic.model.Creditcard;
import org.group2.petclinic.model.Diagnosis;
import org.group2.petclinic.model.Medicine;
import org.group2.petclinic.model.Owner;
import org.group2.petclinic.model.Payment;
import org.group2.petclinic.model.Person;
import org.group2.petclinic.model.Pet;
import org.group2.petclinic.model.Visit;
import org.group2.petclinic.model.VisitType;

public class PetclinicAssertions {

	public static CreditcardAssert assertThat(Creditcard actual) {
		return new CreditcardAssert(actual);
	}

	public static DiagnosisAssert assertThat(Diagnosis actual) {
		return new DiagnosisAssert(actual);
	}

	public static MedicineAssert assertThat(Medicine actual) {
		return new MedicineAssert(actual);
	}

	public static OwnerAssert assertThat(Owner actual) {
		return new OwnerAssert(actual);
	}

	public static PaymentAssert assertThat(Payment actual) {
		return new PaymentAssert(actual);
	}

	public static PersonAssert assertThat(Person actual) {
		return new PersonAssert(actual);
	}

	public static PetAssert assertThat(Pet actual) {
		return new PetAssert(actual);
	}

	//TODO PetType
	//TODO Prescription
	//TODO Secretary
	//TODO Specialty
	//TODO User
	//TODO Vet

	public static VisitAssert assertThat(Visit actual) {
		return new VisitAssert(actual);
	}

	public static VisitTypeAssert assertThat(VisitType actual) {
		return new VisitTypeAssert(actual);
	}

}
