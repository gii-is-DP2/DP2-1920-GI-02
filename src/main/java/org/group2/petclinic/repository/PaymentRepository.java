
package org.group2.petclinic.repository;

import java.util.List;

import org.group2.petclinic.model.Payment;
import org.springframework.dao.DataAccessException;
import org.springframework.data.repository.query.Param;

public interface PaymentRepository {

	void save(Payment payment) throws DataAccessException;

	Payment findPaymentById(@Param("id") int id) throws DataAccessException;

	List<Payment> findRevenuesByMonth() throws DataAccessException;

}
