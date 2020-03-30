
package org.group2.petclinic.repository;

import static org.assertj.core.api.Assertions.assertThat;

import org.group2.petclinic.model.Payment;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class PaymentRepositoryTests {

	@Autowired
	private PaymentRepository paymentRepository;

	// -------------------------- findPaymentById(@Param("id") int id) ---------------------------


	// POSITIVE TEST
	@Test
	void shouldFindPaymentById() {
		//1. Arrange
		int id = 1;

		//2. Act
		Payment payment = this.paymentRepository.findPaymentById(id);

		//3. Assert
		assertThat(payment).isNotNull();

	}

	// NEGATIVE TEST
	// Doesn't exist a payment with this id
	@Test
	void shouldNotFindPaymentById() {
		//1. Arrange
		int id = 50;

		//2. Act
		Payment payment = this.paymentRepository.findPaymentById(id);

		//3. Assert
		assertThat(payment).isNull();
	}

}
