
package org.group2.petclinic.unitTests.customasserts;

import java.util.Objects;

import org.assertj.core.api.AbstractAssert;
import org.group2.petclinic.model.Person;
import org.group2.petclinic.model.User;

public class PersonAssert extends AbstractAssert<PersonAssert, Person> {

	public PersonAssert(Person actual) {
		super(actual, PersonAssert.class);
	}

	public static PersonAssert assertThat(Person actual) {
		return new PersonAssert(actual);
	}

	// Custom asserts --------------------------------------------------------

	public PersonAssert hasFirstName(String firstName) {

		isNotNull();

		if (!Objects.equals(actual.getFirstName(), firstName)) {
			failWithMessage(
				"Expected person's firstName to be " + firstName + " but was " + actual.getFirstName());
		}

		return this;
	}

	public PersonAssert hasLastName(String lastName) {

		isNotNull();

		if (!Objects.equals(actual.getLastName(), lastName)) {
			failWithMessage(
				"Expected person's lastName to be " + lastName + " but was " + actual.getLastName());
		}

		return this;
	}

	public PersonAssert hasUser(User user) {

		isNotNull();

		if (!Objects.equals(actual.getUser(), user)) {
			failWithMessage("Expected person's user to be " + user + " but was " + actual.getUser());
		}

		return this;
	}

}
