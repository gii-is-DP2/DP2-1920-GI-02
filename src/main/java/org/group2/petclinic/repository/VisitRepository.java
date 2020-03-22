
package org.group2.petclinic.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.group2.petclinic.model.BaseEntity;
import org.group2.petclinic.model.Vet;
import org.group2.petclinic.model.Visit;
import org.group2.petclinic.model.VisitType;
import org.springframework.dao.DataAccessException;

public interface VisitRepository {

	/**
	 * Save a <code>Visit</code> to the data store, either inserting or updating it.
	 *
	 * @param visit
	 *            the <code>Visit</code> to save
	 * @see BaseEntity#isNew
	 */
	void save(Visit visit) throws DataAccessException;

	List<Visit> findByPetId(Integer petId);

	List<VisitType> findVisitTypes() throws DataAccessException;

	List<Visit> findVisitsByVetOnBetween(Vet vet, LocalDateTime beginning, LocalDateTime end) throws DataAccessException;
}
