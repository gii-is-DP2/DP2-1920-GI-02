
package org.group2.petclinic.repository.springdatajpa;

import java.time.LocalDateTime;
import java.util.List;

import org.group2.petclinic.model.Vet;
import org.group2.petclinic.model.Visit;
import org.group2.petclinic.model.VisitType;
import org.group2.petclinic.repository.VisitRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

public interface SpringDataVisitRepository
	extends VisitRepository, Repository<Visit, Integer> {

	@Override
	@Query("SELECT visit FROM Visit visit WHERE visit.vet = ?1 ORDER BY visit.moment DESC")
	List<Visit> findVisitsByVet(Vet vet) throws DataAccessException;

	@Override
	@Query("SELECT visit FROM Visit visit WHERE visit.vet = ?1 AND visit.moment > ?2 ORDER BY visit.moment DESC")
	List<Visit> findFutureVisitsByVet(Vet vet, LocalDateTime now) throws DataAccessException;

	@Override
	@Query("SELECT visit FROM Visit visit WHERE visit.vet = ?1 AND visit.moment <= ?2 ORDER BY visit.moment DESC")
	List<Visit> findPastVisitsByVet(Vet vet, LocalDateTime now) throws DataAccessException;

	@Override
	@Query("SELECT visit FROM Visit visit WHERE visit.vet = ?1 AND visit.moment >= ?2 AND visit.moment < ?3 ORDER BY visit.moment")
	List<Visit> findVisitsByVetBetween(Vet vet, LocalDateTime beginning,
		LocalDateTime end) throws DataAccessException;

	@Override
	@Query("SELECT vt FROM VisitType vt ORDER BY vt.name")
	List<VisitType> findVisitTypes() throws DataAccessException;

	@Override
	@Query("SELECT v FROM Visit v WHERE v.id = ?1")
	Visit findById(Integer visitId) throws DataAccessException;

}
