
package org.group2.petclinic.unitTests.customasserts;

import org.group2.petclinic.model.Creditcard;
import org.group2.petclinic.model.Diagnosis;
import org.group2.petclinic.model.Medicine;
import org.group2.petclinic.model.Owner;
import org.group2.petclinic.model.Payment;
import org.group2.petclinic.model.Person;
import org.group2.petclinic.model.Pet;
import org.group2.petclinic.model.PetType;
import org.group2.petclinic.model.Prescription;
import org.group2.petclinic.model.Specialty;
import org.group2.petclinic.model.User;
import org.group2.petclinic.model.Vet;
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

	public static PetTypeAssert assertThat(PetType actual) {
		return new PetTypeAssert(actual);
	}

	public static PrescriptionAssert assertThat(Prescription actual) {
		return new PrescriptionAssert(actual);
	}

	public static SpecialtyAssert assertThat(Specialty actual) {
		return new SpecialtyAssert(actual);
	}

	public static UserAssert assertThat(User actual) {
		return new UserAssert(actual);
	}

	public static VetAssert assertThat(Vet actual) {
		return new VetAssert(actual);
	}

	public static VisitAssert assertThat(Visit actual) {
		return new VisitAssert(actual);
	}

	public static VisitTypeAssert assertThat(VisitType actual) {
		return new VisitTypeAssert(actual);
	}

}
