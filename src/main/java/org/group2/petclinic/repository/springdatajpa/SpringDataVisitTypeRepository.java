
package org.group2.petclinic.repository.springdatajpa;

import org.group2.petclinic.model.VisitType;
import org.group2.petclinic.repository.VisitTypeRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

public interface SpringDataVisitTypeRepository extends VisitTypeRepository, Repository<VisitType, Integer> {

	@Override
	@Query("SELECT v FROM VisitType v WHERE v.id = ?1")
	VisitType findVisitTypeById(Integer visitTypeId) throws DataAccessException;

}
