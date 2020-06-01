
package org.group2.petclinic.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

import org.group2.petclinic.model.Owner;
import org.group2.petclinic.model.Vet;
import org.group2.petclinic.model.Visit;
import org.group2.petclinic.model.VisitType;
import org.group2.petclinic.repository.VisitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class VisitService {

	private VisitRepository visitRepository;


	// CONSTRUCTOR ------------------------------------------------------------

	@Autowired
	public VisitService(final VisitRepository visitRepository) {
		this.visitRepository = visitRepository;
	}

	// SAVE VISITS ------------------------------------------------------------

	@Transactional
	@CacheEvict(cacheNames="visitById", allEntries=true)
	public void saveVisit(final Visit visit) throws DataAccessException {
		this.visitRepository.save(visit);
	}

	// FIND VISITS ------------------------------------------------------------

	@Transactional(readOnly = true)
	public Collection<Visit> findVisits() throws DataAccessException {
		return visitRepository.findAll();
	}

	@Transactional(readOnly = true)
	public List<Visit> findVisitsByVet(final Vet vet) throws DataAccessException {
		return this.visitRepository.findVisitsByVet(vet);
	}

	@Transactional(readOnly = true)
	@Cacheable("futureVisitsByVet")
	public List<Visit> findFutureVisitsByVet(final Vet vet) throws DataAccessException {
		return this.visitRepository.findFutureVisitsByVet(vet, LocalDateTime.now());
	}

	@Transactional(readOnly = true)
	@Cacheable("pastVisitsByVet")
	public List<Visit> findPastVisitsByVet(final Vet vet) throws DataAccessException {
		return this.visitRepository.findPastVisitsByVet(vet, LocalDateTime.now());
	}

	@Transactional(readOnly = true)
	public List<Visit> findVisitsByVetOnDate(final Vet vet, final LocalDate date) throws DataAccessException {
		LocalDateTime beginning = date.atStartOfDay();
		LocalDateTime end = beginning.plusDays(1);
		return this.visitRepository.findVisitsByVetBetween(vet, beginning, end);
	}

	@Transactional(readOnly = true)
	public List<Visit> findFutureVisitsByOwner(final Owner owner) throws DataAccessException {
		return this.visitRepository.findFutureVisitsByOwner(owner, LocalDateTime.now());
	}

	@Transactional(readOnly = true)
	public List<Visit> findPastVisitsByOwner(final Owner owner) throws DataAccessException {
		return this.visitRepository.findPastVisitsByOwner(owner, LocalDateTime.now());
	}

	// FIND VISIT -------------------------------------------------------------

	@Transactional(readOnly = true)
	@Cacheable("visitById")
	public Visit findVisitById(int id) throws DataAccessException {
		return visitRepository.findById(id);
	}

	// OTHER METHODS ----------------------------------------------------------

	@Transactional(readOnly = true)
	public Collection<VisitType> findVisitTypes() throws DataAccessException {
		return this.visitRepository.findVisitTypes();
	}

}
