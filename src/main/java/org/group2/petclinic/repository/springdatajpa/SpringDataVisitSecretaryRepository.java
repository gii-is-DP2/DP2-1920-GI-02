
package org.group2.petclinic.repository.springdatajpa;

import java.util.List;

import org.group2.petclinic.model.Visit;
import org.group2.petclinic.repository.VisitSecretaryRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

public interface SpringDataVisitSecretaryRepository extends VisitSecretaryRepository, Repository<Visit, Integer> {

	@Override
	@Query("SELECT v FROM Visit v WHERE v.payment = null ORDER BY moment")
	Iterable<Visit> findVisitsNoPayment();

	@Override
	@Query("SELECT v FROM Visit v WHERE v.id =:id")
	Visit findVisitById(@Param("id") int id) throws DataAccessException;

	@Override
	@Query("SELECT v FROM Visit v")
	List<Visit> findAllVisits() throws DataAccessException;

}
