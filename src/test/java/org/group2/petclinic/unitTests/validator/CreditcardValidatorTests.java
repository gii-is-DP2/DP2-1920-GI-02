
package org.group2.petclinic.unitTests.validator;

import static org.assertj.core.api.Assertions.assertThat;

import org.group2.petclinic.model.Creditcard;
import org.group2.petclinic.web.CreditcardValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
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
	@ParameterizedTest
	@CsvSource({
		"10, 15", "01, 20", "03, 20", "5, 16"
	})
	void shoulExpirationDate(int month, int year) {
		//1. Arrange
		Creditcard creditcard = new Creditcard();
		creditcard.setHolder("Maria Zambrano");
		creditcard.setBrand("visa");
		creditcard.setNumber("4785377804843758");
		creditcard.setExpMonth(month);
		creditcard.setExpYear(year);
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
	@ParameterizedTest
	@ValueSource(strings = {
		"251458", "10", "05", "08524"
	})
	void shoulSecurityCodeLengt(String securityCode) {
		//1. Arrange
		Creditcard creditcard = new Creditcard();
		creditcard.setHolder("Maria Zambrano");
		creditcard.setBrand("visa");
		creditcard.setNumber("4785377804843758");
		creditcard.setExpMonth(5);
		creditcard.setExpYear(25);
		creditcard.setSecurityCode(securityCode);

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
	@ParameterizedTest
	@ValueSource(strings = {
		"aaa5", "25a", "5a5"
	})
	void shoulSecurityCodeCharacteres(String securityCode) {
		//1. Arrange
		Creditcard creditcard = new Creditcard();
		creditcard.setHolder("Maria Zambrano");
		creditcard.setBrand("visa");
		creditcard.setNumber("4785377804843758");
		creditcard.setExpMonth(5);
		creditcard.setExpYear(25);
		creditcard.setSecurityCode(securityCode);

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
	@ParameterizedTest
	@ValueSource(strings = {
		"1122335588447755", "11223388", "11112222555588884444", "0022005544998877"
	})
	void shoulNumberNotValid(String number) {
		//1. Arrange
		Creditcard creditcard = new Creditcard();
		creditcard.setHolder("Maria Zambrano");
		creditcard.setBrand("visa");
		creditcard.setNumber(number);
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
