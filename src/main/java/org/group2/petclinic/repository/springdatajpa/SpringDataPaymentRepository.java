
package org.group2.petclinic.repository.springdatajpa;

import java.util.List;

import org.group2.petclinic.model.Payment;
import org.group2.petclinic.repository.PaymentRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

public interface SpringDataPaymentRepository extends PaymentRepository, Repository<Payment, Integer> {

	@Override
	@Query("SELECT p FROM Payment p WHERE p.id =:id")
	Payment findPaymentById(@Param("id") int id) throws DataAccessException;

	@Override
	@Query("SELECT p FROM Payment p")
	List<Payment> findRevenuesByMonth() throws DataAccessException;

	@Override
	@Modifying
	@Query("DELETE Payment p WHERE p.id =:id")
	void delete(@Param("id") int id) throws DataAccessException;

}
