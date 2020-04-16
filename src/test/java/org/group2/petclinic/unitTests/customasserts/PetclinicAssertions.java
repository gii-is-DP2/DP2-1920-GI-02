
package org.group2.petclinic.unitTests.customasserts;

import org.group2.petclinic.model.Visit;

public class PetclinicAssertions {

	public static VisitAssert assertThat(Visit actual) {
		return new VisitAssert(actual);
	}
}
