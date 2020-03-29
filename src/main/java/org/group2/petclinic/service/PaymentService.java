
package org.group2.petclinic.service;

import javax.transaction.Transactional;

import org.group2.petclinic.model.Payment;
import org.group2.petclinic.repository.PaymentRepository;
import org.group2.petclinic.repository.springdatajpa.SpringDataPaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

	private PaymentRepository paymentRepository;


	@Autowired
	public PaymentService(final SpringDataPaymentRepository paymentRepository) {
		this.paymentRepository = paymentRepository;
	}

	@Transactional
	public void savePayment(final Payment payment) throws DataAccessException {
		this.paymentRepository.save(payment);
	}

	@Transactional
	public Payment findPaymentById(final int id) throws DataAccessException {
		return this.paymentRepository.findPaymentById(id);
	}

}
