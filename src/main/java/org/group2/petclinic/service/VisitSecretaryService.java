
package org.group2.petclinic.service;

import java.util.List;

import javax.transaction.Transactional;

import org.group2.petclinic.model.Visit;
import org.group2.petclinic.repository.springdatajpa.SpringDataVisitSecretaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

@Service
public class VisitSecretaryService {

	private SpringDataVisitSecretaryRepository visitSecretaryRepository;


	@Autowired
	public VisitSecretaryService(final SpringDataVisitSecretaryRepository visitSecretaryRepository) {
		this.visitSecretaryRepository = visitSecretaryRepository;
	}

	@Transactional
	public Iterable<Visit> findVisitsNoPayment() {
		return this.visitSecretaryRepository.findVisitsNoPayment();
	}

	@Transactional
	public Visit findVisitById(final int id) throws DataAccessException {
		return this.visitSecretaryRepository.findVisitById(id);
	}

	@Transactional
	public List<Visit> findAllVisits() throws DataAccessException {
		return this.visitSecretaryRepository.findAllVisits();
	}

}
