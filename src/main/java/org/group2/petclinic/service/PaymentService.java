
package org.group2.petclinic.service;

import javax.transaction.Transactional;

import org.group2.petclinic.model.Payment;
import org.group2.petclinic.repository.springdatajpa.SpringDataPaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

	@Autowired
	private SpringDataPaymentRepository paymentRepository;


	@Transactional
	public Iterable<Payment> findAllPayments() {
		return this.paymentRepository.findAllPayments();
	}

}
