
package org.group2.petclinic.repository.springdatajpa;

import org.group2.petclinic.model.Payment;
import org.group2.petclinic.repository.PaymentRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

public interface SpringDataPaymentRepository extends PaymentRepository, Repository<Payment, Integer> {

	@Override
	@Query("SELECT p FROM Payment p")
	Iterable<Payment> findAllPayments();

}
