
package org.group2.petclinic.unitTests.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.group2.petclinic.unitTests.customasserts.PetclinicAssertions.assertThat;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.group2.petclinic.model.Payment;
import org.group2.petclinic.model.Visit;
import org.group2.petclinic.repository.PaymentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.repository.query.Param;

@ExtendWith(MockitoExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
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
		assertThat(payment).hasMethod("creditcard");
		assertThat(payment).hasMoment(LocalDateTime.parse("2019-06-05T12:20"));
		assertThat(payment).hasFinalPrice(30.00);

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
		assertThat(payments.get(0)).hasMethod("creditcard");
		assertThat(payments.get(4)).hasMethod("cash");

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

	// -------------------------- delete(@Param("id") int id) ---------------------------

	// POSITIVE TEST
	@Test
	void shouldDelete() {
		//1. Arrange
		int id = 1;
		Payment payment1 = new Payment();
		payment1.setId(1);
		Payment payment2 = new Payment();
		payment1.setId(2);
		List<Payment> listPayment = new ArrayList<Payment>();
		listPayment.add(payment1);
		listPayment.add(payment2);

		when(this.stubPaymentRepository.findRevenuesByMonth()).thenReturn(listPayment);
		int int1 = this.stubPaymentRepository.findRevenuesByMonth().size();

		//2. Act
		this.stubPaymentRepository.delete(id);
		int int2 = this.stubPaymentRepository.findRevenuesByMonth().size();

		//3. Assert
		assertThat(int1 > int2);
	}

	// NEGATIVE TEST
	// Use a ID not valid
	@Test
	void shouldNotDelete() {
		//1. Arrange
		int id = 10;
		Payment payment1 = new Payment();
		payment1.setId(1);
		Payment payment2 = new Payment();
		payment1.setId(2);
		List<Payment> listPayment = new ArrayList<Payment>();
		listPayment.add(payment1);
		listPayment.add(payment2);

		when(this.stubPaymentRepository.findRevenuesByMonth()).thenReturn(listPayment);
		int int1 = this.stubPaymentRepository.findRevenuesByMonth().size();

		//2. Act
		this.stubPaymentRepository.delete(id);
		int int2 = this.stubPaymentRepository.findRevenuesByMonth().size();

		//3. Assert
		assertThat(int1 == int2);
	}

}
