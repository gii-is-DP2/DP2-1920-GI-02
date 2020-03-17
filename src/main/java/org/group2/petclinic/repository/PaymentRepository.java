
package org.group2.petclinic.repository;

import org.group2.petclinic.model.Payment;
import org.springframework.dao.DataAccessException;

public interface PaymentRepository {

	Iterable<Payment> findAllPayments() throws DataAccessException;

}
