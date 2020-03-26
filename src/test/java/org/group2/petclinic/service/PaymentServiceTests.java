
package org.group2.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.group2.petclinic.model.Payment;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class PaymentServiceTests {

	@Autowired
	protected PaymentService paymentService;


	// savePayment(final Payment payment) POSITIVE TEST
	@Test
	void shouldSavePayment() {
		//1. Arrange

		//2. Act

		//3. Assert

	}

	// savePayment(final Payment payment) NEGATIVE TEST
	// ahsbc
	@Test
	void shouldNotSavePayment() {
		//1. Arrange

		//2. Act

		//3. Assert

	}

	// findPaymentById(final int id) POSITIVE TEST
	@Test
	void shouldFindPaymentById() {
		//1. Arrange

		//2. Act
		Payment payment = this.paymentService.findPaymentById(1);

		//3. Assert
		assertThat(payment.getMethod()).isEqualTo("creditcard");
		assertThat(payment.getMoment()).isEqualTo("2019-06-05T10:20");
		assertThat(payment.getFinalPrice()).isEqualTo(30);
		assertThat(payment.getSecretary().getId()).isEqualTo(1);
		assertThat(payment.getCreditcard().getId()).isEqualTo(1);
	}

	// findPaymentById(final int id) NEGATIVE TEST
	// Introduce a id not valid (don't exits a payment with this id)
	@Test
	void shouldNotFindPaymentById() {
		//1. Arrange

		//2. Act
		Payment payment = this.paymentService.findPaymentById(10);

		//3. Assert
		assertNull(payment);
	}
}
