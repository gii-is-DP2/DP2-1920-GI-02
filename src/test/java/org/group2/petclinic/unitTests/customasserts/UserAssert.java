
package org.group2.petclinic.unitTests.customasserts;

import java.util.Objects;

import org.assertj.core.api.AbstractAssert;
import org.group2.petclinic.model.User;

public class UserAssert extends AbstractAssert<UserAssert, User> {

	public UserAssert(User actual) {
		super(actual, UserAssert.class);
	}

	public static UserAssert assertThat(User actual) {
		return new UserAssert(actual);
	}

	// Custom asserts --------------------------------------------------------

	public UserAssert hasUsername(String username) {

		isNotNull();

		if (!Objects.equals(actual.getUsername(), username)) {
			failWithMessage(
				"Expected user's username to be " + username + " but was " + actual.getUsername());
		}

		return this;
	}

	public UserAssert hasPassword(String password) {

		isNotNull();

		if (!Objects.equals(actual.getPassword(), password)) {
			failWithMessage(
				"Expected user's password to be " + password + " but was " + actual.getPassword());
		}

		return this;
	}

	public UserAssert hasEnabled(Boolean enabled) {

		isNotNull();

		if (!Objects.equals(actual.isEnabled(), enabled)) {
			failWithMessage("Expected user's enabled to be " + enabled + " but was " + actual.isEnabled());
		}

		return this;
	}

}
