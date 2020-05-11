
package org.group2.petclinic.unitTests.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.group2.petclinic.unitTests.customasserts.PetclinicAssertions.assertThat;
import static org.mockito.Mockito.verify;

import org.group2.petclinic.model.Creditcard;
import org.group2.petclinic.service.CreditcardService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

@ExtendWith(MockitoExtension.class)
@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class CreditcardServiceTests {

	@Autowired
	protected CreditcardService	creditcardService;

	@Mock
	private CreditcardService	stubCreditcardService;

	// -------------------------- saveCreditcard(final Creditcard creditcard) ---------------------------


	// POSITIVE TEST
	@Test
	void shouldSaveCreditcard() {
		//1. Arrange
		Creditcard creditcard = new Creditcard();
		creditcard.setHolder("Maria");
		creditcard.setBrand("visa");
		creditcard.setNumber("4320730159983235");
		creditcard.setExpMonth(5);
		creditcard.setExpYear(30);
		creditcard.setSecurityCode("250");

		//2. Act
		this.stubCreditcardService.saveCreditcard(creditcard);

		//3. Assert
		verify(this.stubCreditcardService).saveCreditcard(creditcard);

	}

	// NEGATIVE TEST
	// Some mandatory fields are not completed
	@Test
	void shouldNotSaveCreditcard() {
		//1. Arrange
		Creditcard creditcard = new Creditcard();
		creditcard.setHolder("Maria");
		creditcard.setBrand("visa");

		//2. Act
		this.stubCreditcardService.saveCreditcard(creditcard);

		//3. Assert
		verify(this.stubCreditcardService).saveCreditcard(creditcard);
		assertThat(creditcard).hasHolder("Maria");
		assertThat(creditcard).hasBrand("visa");
		assertThat(creditcard.getNumber()).isNull();
		assertThat(creditcard.getExpMonth()).isNull();
		assertThat(creditcard.getExpYear()).isNull();
		assertThat(creditcard.getSecurityCode()).isNull();

	}

}
