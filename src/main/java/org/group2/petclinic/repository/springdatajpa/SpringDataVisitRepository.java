
package org.group2.petclinic.repository.springdatajpa;

import java.util.List;

import org.group2.petclinic.model.Visit;
import org.group2.petclinic.model.VisitType;
import org.group2.petclinic.repository.VisitRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

public interface SpringDataVisitRepository extends VisitRepository, Repository<Visit, Integer> {

	@Override
	@Query("SELECT vt FROM VisitType vt ORDER BY vt.name")
	List<VisitType> findVisitTypes() throws DataAccessException;

}
