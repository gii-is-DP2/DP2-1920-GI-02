
package org.group2.petclinic.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.group2.petclinic.model.Vet;
import org.group2.petclinic.model.Visit;
import org.group2.petclinic.model.VisitType;
import org.springframework.dao.DataAccessException;

public interface VisitRepository {

	void save(Visit visit) throws DataAccessException;

	List<Visit> findByPetId(Integer petId);

	List<Visit> findVisitsByVet(Vet vet) throws DataAccessException;

	List<Visit> findVisitsByVetBetween(Vet vet, LocalDateTime beginning, LocalDateTime end) throws DataAccessException;

	List<VisitType> findVisitTypes() throws DataAccessException;

}
