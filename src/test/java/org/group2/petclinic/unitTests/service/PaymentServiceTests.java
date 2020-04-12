
package org.group2.petclinic.unitTests.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

import org.group2.petclinic.model.Payment;
import org.group2.petclinic.model.Secretary;
import org.group2.petclinic.model.User;
import org.group2.petclinic.model.Visit;
import org.group2.petclinic.repository.springdatajpa.SpringDataPaymentRepository;
import org.group2.petclinic.repository.springdatajpa.SpringDataVisitSecretaryRepository;
import org.group2.petclinic.service.PaymentService;
import org.group2.petclinic.service.VisitSecretaryService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

@ExtendWith(MockitoExtension.class)
@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class PaymentServiceTests {

	@Autowired
	protected PaymentService			paymentService;

	@Mock
	private SpringDataPaymentRepository	stubPaymentRepository;

	@Mock
	private PaymentService				stubPaymentService;

	// -------------------------- savePayment(final Payment payment) ---------------------------


	// POSITIVE TEST
	@Test
	void shouldSavePayment() {

		//1. Arrange
		Payment payment = new Payment();
		payment.setMethod("cash");
		payment.setCreditcard(null);
		LocalDateTime date = LocalDateTime.now();
		date.minusMinutes(2);
		payment.setMoment(date);
		payment.setFinalPrice(30.00);

		Secretary secretary = new Secretary();
		secretary.setFirstName("Maria");
		secretary.setLastName("Zamba");
		secretary.setId(1);

		User user = new User();
		user.setUsername("secretary1");
		user.setPassword("s3cr3tary");
		secretary.setUser(user);
		payment.setSecretary(secretary);

		//2. Act
		this.stubPaymentService.savePayment(payment);

		//3. Assert
		verify(this.stubPaymentService).savePayment(payment);

	}

	// NEGATIVE TEST
	// Some mandatory fields are not completed
	@Test
	void shouldNotSavePayment() {
		//1. Arrange
		Payment payment = new Payment();
		payment.setMethod("cash");
		payment.setCreditcard(null);

		//2. Act
		this.stubPaymentService.savePayment(payment);

		//3. Assert
		verify(this.stubPaymentService).savePayment(payment);
		assertThat(payment.getMoment()).isNull();
		assertThat(payment.getFinalPrice()).isNull();
	}

	// -------------------------- deletePayment(final Payment payment) ---------------------------

	// POSITIVE TEST
	@Test
	void shouldDeletePayment() {

		//1. Arrange
		int id = 1;

		//2. Act
		this.stubPaymentService.deletePayment(id);

		//3. Assert
		verify(this.stubPaymentService).deletePayment(id);

	}

	// NEGATIVE TEST
	// Introduce a id not valid (don't exits a payment with this id)
	@Test
	void shouldNotDeletePayment() {
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

		//2. Act
		this.stubPaymentService.deletePayment(id);

		//3. Assert
		verify(this.stubPaymentService).deletePayment(id);
		assertThat(this.stubPaymentRepository.findRevenuesByMonth().size()).isEqualTo(2);

	}

	// -------------------------- findPaymentById(final int id) ---------------------------

	//POSITIVE TEST
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

	// NEGATIVE TEST
	// Introduce a id not valid (don't exits a payment with this id)
	@Test
	void shouldNotFindPaymentById() {
		//1. Arrange

		//2. Act
		Payment payment = this.paymentService.findPaymentById(10);

		//3. Assert
		assertNull(payment);
	}

	// -------------------------- findRevenuesByMonth() ---------------------------

	// POSITIVE TEST
	@Test
	void shouldFindRevenuesByMonth() {
		//1. Arrange

		//2. Act
		List<Payment> payments = this.paymentService.findRevenuesByMonth();

		//3. Assert
		assertThat(payments).isNotNull();
		assertThat(payments).hasSize(5);

	}

	// NEGATIVE TEST
	// There aren't any payment
	@Test
	void shouldNotFindRevenuesByMonth() {
		//1. Arrange
		paymentService = new PaymentService(stubPaymentRepository);

		//2. Act
		List<Payment> payment = this.stubPaymentService.findRevenuesByMonth();

		//3. Assert
		assertThat(payment).hasSize(0);
	}
}
