
package org.group2.petclinic.service;

import java.util.Collection;

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

	// METHODS ----------------------------------------------------------------

	@Transactional(readOnly = true)
	public Collection<VisitType> findVisitTypes() throws DataAccessException {
		return this.visitRepository.findVisitTypes();
	}

}
