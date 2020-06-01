
package org.group2.petclinic.service;

import java.util.Collection;

import org.group2.petclinic.model.Owner;
import org.group2.petclinic.repository.OwnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OwnerService {

	private OwnerRepository		ownerRepository;

	@Autowired
	private UserService			userService;

	@Autowired
	private AuthoritiesService	authoritiesService;


	// CONSTRUCTOR ------------------------------------------------------------

	@Autowired
	public OwnerService(final OwnerRepository ownerRepository) {
		this.ownerRepository = ownerRepository;
	}

	// SAVE OWNER -------------------------------------------------------------

	@Transactional
	@CacheEvict(cacheNames="ownerById", allEntries=true)
	public void saveOwner(final Owner owner) throws DataAccessException {
		this.ownerRepository.save(owner);
		this.userService.saveUser(owner.getUser());
		this.authoritiesService.saveAuthorities(owner.getUser().getUsername(), "owner");
	}

	//FIND OWNER --------------------------------------------------------------

	@Transactional(readOnly = true)
	@Cacheable("ownerById")
	public Owner findOwnerById(final int id) throws DataAccessException {
		return this.ownerRepository.findById(id);
	}

	@Transactional(readOnly = true)
	public Owner findOwnerByUsername(final String username) throws DataAccessException {
		return this.ownerRepository.findByUsername(username);
	}

	// FIND OWNERS ------------------------------------------------------------

	@Transactional(readOnly = true)
	public Collection<Owner> findOwnerByLastName(final String lastName) throws DataAccessException {
		return this.ownerRepository.findByLastName(lastName);
	}

}
