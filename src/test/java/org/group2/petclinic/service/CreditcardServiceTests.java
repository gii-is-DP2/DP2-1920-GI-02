
package org.group2.petclinic.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class CreditcardServiceTests {

	@Autowired
	protected CreditcardService creditcardService;

	// -------------------------- saveCreditcard(final Creditcard creditcard) ---------------------------


	// POSITIVE TEST
	@Test
	void shouldSaveCreditcard() {
		//1. Arrange

		//2. Act

		//3. Assert

	}

	// NEGATIVE TEST
	// Description why is negative
	@Test
	void shouldNotSaveCreditcard() {
		//1. Arrange

		//2. Act

		//3. Assert

	}

}
