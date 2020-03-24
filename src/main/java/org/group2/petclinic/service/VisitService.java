
package org.group2.petclinic.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

import org.group2.petclinic.model.Vet;
import org.group2.petclinic.model.Visit;
import org.group2.petclinic.model.VisitType;
import org.group2.petclinic.repository.VisitRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
	public void saveVisit(final Visit visit) throws DataAccessException {
		this.visitRepository.save(visit);
	}

	// FIND VISITS ------------------------------------------------------------

	@Transactional(readOnly = true)
	public List<Visit> findVisitsByVet(final Vet vet) throws DataAccessException {
		return this.visitRepository.findVisitsByVet(vet);
	}

	@Transactional(readOnly = true)
	public List<Visit> findVisitsByVetOnDate(final Vet vet, final LocalDate date) throws DataAccessException {
		LocalDateTime beginning = date.atStartOfDay();
		LocalDateTime end = beginning.plusDays(1);
		return this.visitRepository.findVisitsByVetBetween(vet, beginning, end);
	}

	// OTHER METHODS ----------------------------------------------------------

	@Transactional(readOnly = true)
	public Collection<VisitType> findVisitTypes() throws DataAccessException {
		return this.visitRepository.findVisitTypes();
	}

}
