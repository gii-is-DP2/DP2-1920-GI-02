
package org.group2.petclinic.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.group2.petclinic.model.Payment;
import org.group2.petclinic.model.Visit;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@ExtendWith(MockitoExtension.class)
@DataJpaTest
public class PaymentRepositoryTests {

	@Autowired
	private PaymentRepository	paymentRepository;

	@Mock
	private PaymentRepository	stubPaymentRepository;

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

	// -------------------------- findRevenuesByMonth() ---------------------------

	// POSITIVE TEST
	@Test
	void shouldFindRevenuesByMonth() {
		//1. Arrange

		//2. Act
		List<Payment> payments = this.paymentRepository.findRevenuesByMonth();

		//3. Assert
		assertThat(payments).isNotNull();
		assertThat(payments).hasSize(5);

	}

	// NEGATIVE TEST
	// Use an empty list
	@Test
	void shouldNotFindRevenuesByMonth() {
		//1. Arrange
		List<Payment> payment = new ArrayList<Payment>();
		when(this.stubPaymentRepository.findRevenuesByMonth()).thenReturn(payment);

		//2. Act
		List<Payment> payments = this.stubPaymentRepository.findRevenuesByMonth();

		//3. Assert
		assertThat(payments).hasSize(0);
	}

}
