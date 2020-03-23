
package org.group2.petclinic.service;

import java.util.Collection;

import org.group2.petclinic.model.Vet;
import org.group2.petclinic.repository.VetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class VetService {

	private VetRepository vetRepository;


	// CONSTRUCTOR ------------------------------------------------------------

	@Autowired
	public VetService(VetRepository vetRepository) {
		this.vetRepository = vetRepository;
	}

	// FIND VET ---------------------------------------------------------------

	@Transactional(readOnly = true)
	public Vet findVetByUsername(final String username) throws DataAccessException {
		return this.vetRepository.findByUsername(username);
	}

	// FIND VETS --------------------------------------------------------------

	@Transactional(readOnly = true)
	public Collection<Vet> findVets() throws DataAccessException {
		return vetRepository.findAll();
	}

}
