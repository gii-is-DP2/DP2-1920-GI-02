
package org.group2.petclinic.validator;

import static org.assertj.core.api.Assertions.assertThat;

import org.group2.petclinic.model.Creditcard;
import org.group2.petclinic.web.CreditcardValidator;
import org.junit.jupiter.api.Test;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

public class CreditcardValidatorTests {

	private CreditcardValidator creditcardValidator;

	// -------------------------- validate(final Object obj, final Errors errors) ---------------------------


	// POSITIVE TEST
	@Test
	void shoulAcceptCreditcard() {
		//1. Arrange
		Creditcard creditcard = new Creditcard();
		creditcard.setHolder("Maria Zambrano");
		creditcard.setBrand("visa");
		creditcard.setNumber("4785377804843758");
		creditcard.setExpMonth(5);
		creditcard.setExpYear(25);
		creditcard.setSecurityCode("025");

		Errors errors = new BeanPropertyBindingResult(creditcard, "creditcard");

		this.creditcardValidator = new CreditcardValidator();

		//2. Act
		creditcardValidator.validate(creditcard, errors);

		//3. Assert
		assertThat(errors.getErrorCount()).isEqualTo(0);
	}

	// NEGATIVE TEST
	// Holder, brand and number is not included
	@Test
	void shoulRequireHolderBrandNumber() {
		//1. Arrange
		Creditcard creditcard = new Creditcard();
		creditcard.setHolder("Maria Zambrano");
		creditcard.setBrand("visa");
		creditcard.setNumber("4785377804843758");
		creditcard.setExpMonth(5);

		Errors errors = new BeanPropertyBindingResult(creditcard, "creditcard");

		this.creditcardValidator = new CreditcardValidator();

		//2. Act
		creditcardValidator.validate(creditcard, errors);

		//3. Assert
		assertThat(errors.getErrorCount()).isEqualTo(2);
		assertThat(errors.getFieldError("expYear")).isNotNull();
		assertThat(errors.getFieldError("securityCode")).isNotNull();
	}

	// NEGATIVE TEST
	// Expiration year and security code is not included
	@Test
	void shoulRequireExpYearSecurityCode() {
		//1. Arrange
		Creditcard creditcard = new Creditcard();
		creditcard.setExpMonth(5);
		creditcard.setExpYear(25);
		creditcard.setSecurityCode("025");

		Errors errors = new BeanPropertyBindingResult(creditcard, "creditcard");

		this.creditcardValidator = new CreditcardValidator();

		//2. Act
		creditcardValidator.validate(creditcard, errors);

		//3. Assert
		assertThat(errors.getErrorCount()).isEqualTo(3);
		assertThat(errors.getFieldError("holder")).isNotNull();
		assertThat(errors.getFieldError("brand")).isNotNull();
		assertThat(errors.getFieldError("number")).isNotNull();
	}

	// NEGATIVE TEST
	// Date is expired
	@Test
	void shoulExpirationDate() {
		//1. Arrange
		Creditcard creditcard = new Creditcard();
		creditcard.setHolder("Maria Zambrano");
		creditcard.setBrand("visa");
		creditcard.setNumber("4785377804843758");
		creditcard.setExpMonth(5);
		creditcard.setExpYear(15);
		creditcard.setSecurityCode("025");

		Errors errors = new BeanPropertyBindingResult(creditcard, "creditcard");

		this.creditcardValidator = new CreditcardValidator();

		//2. Act
		creditcardValidator.validate(creditcard, errors);

		//3. Assert
		assertThat(errors.getErrorCount()).isEqualTo(2);
		assertThat(errors.getFieldError("expMonth")).isNotNull();
		assertThat(errors.getFieldError("expYear")).isNotNull();
	}

	// NEGATIVE TEST
	// Expiration year is not introduce with two digits
	@Test
	void shoulExpYearWithTwoDigits() {
		//1. Arrange
		Creditcard creditcard = new Creditcard();
		creditcard.setHolder("Maria Zambrano");
		creditcard.setBrand("visa");
		creditcard.setNumber("4785377804843758");
		creditcard.setExpMonth(5);
		creditcard.setExpYear(2025);
		creditcard.setSecurityCode("025");

		Errors errors = new BeanPropertyBindingResult(creditcard, "creditcard");

		this.creditcardValidator = new CreditcardValidator();

		//2. Act
		creditcardValidator.validate(creditcard, errors);

		//3. Assert
		assertThat(errors.getErrorCount()).isEqualTo(1);
		assertThat(errors.getFieldError("expYear")).isNotNull();
	}

	// NEGATIVE TEST
	// Security code with lenght different of 2 or 4
	@Test
	void shoulSecurityCodeLengt() {
		//1. Arrange
		Creditcard creditcard = new Creditcard();
		creditcard.setHolder("Maria Zambrano");
		creditcard.setBrand("visa");
		creditcard.setNumber("4785377804843758");
		creditcard.setExpMonth(5);
		creditcard.setExpYear(25);
		creditcard.setSecurityCode("251458");

		Errors errors = new BeanPropertyBindingResult(creditcard, "creditcard");

		this.creditcardValidator = new CreditcardValidator();

		//2. Act
		creditcardValidator.validate(creditcard, errors);

		//3. Assert
		assertThat(errors.getErrorCount()).isEqualTo(1);
		assertThat(errors.getFieldError("securityCode")).isNotNull();
	}

	// NEGATIVE TEST
	// Security code with characteres
	@Test
	void shoulSecurityCodeCharacteres() {
		//1. Arrange
		Creditcard creditcard = new Creditcard();
		creditcard.setHolder("Maria Zambrano");
		creditcard.setBrand("visa");
		creditcard.setNumber("4785377804843758");
		creditcard.setExpMonth(5);
		creditcard.setExpYear(25);
		creditcard.setSecurityCode("aaa5");

		Errors errors = new BeanPropertyBindingResult(creditcard, "creditcard");

		this.creditcardValidator = new CreditcardValidator();

		//2. Act
		creditcardValidator.validate(creditcard, errors);

		//3. Assert
		assertThat(errors.getErrorCount()).isEqualTo(1);
		assertThat(errors.getFieldError("securityCode")).isNotNull();
	}

	// NEGATIVE TEST
	// Security code with characteres and too short
	@Test
	void shoulSecurityCodeCharacteresAndShort() {
		//1. Arrange
		Creditcard creditcard = new Creditcard();
		creditcard.setHolder("Maria Zambrano");
		creditcard.setBrand("visa");
		creditcard.setNumber("4785377804843758");
		creditcard.setExpMonth(5);
		creditcard.setExpYear(25);
		creditcard.setSecurityCode("a5");

		Errors errors = new BeanPropertyBindingResult(creditcard, "creditcard");

		this.creditcardValidator = new CreditcardValidator();

		//2. Act
		creditcardValidator.validate(creditcard, errors);

		//3. Assert
		assertThat(errors.getErrorCount()).isEqualTo(2);
		assertThat(errors.getFieldError("securityCode")).isNotNull();
	}

	// NEGATIVE TEST
	// Number is not valid
	@Test
	void shoulNumberNotValid() {
		//1. Arrange
		Creditcard creditcard = new Creditcard();
		creditcard.setHolder("Maria Zambrano");
		creditcard.setBrand("visa");
		creditcard.setNumber("1122335588447755");
		creditcard.setExpMonth(5);
		creditcard.setExpYear(25);
		creditcard.setSecurityCode("025");

		Errors errors = new BeanPropertyBindingResult(creditcard, "creditcard");

		this.creditcardValidator = new CreditcardValidator();

		//2. Act
		creditcardValidator.validate(creditcard, errors);

		//3. Assert
		assertThat(errors.getErrorCount()).isEqualTo(1);
		assertThat(errors.getFieldError("number")).isNotNull();
	}

}
