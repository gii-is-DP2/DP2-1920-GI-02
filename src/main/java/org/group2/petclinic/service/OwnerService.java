/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.group2.petclinic.service;

import java.util.Collection;

import org.group2.petclinic.model.Owner;
import org.group2.petclinic.repository.OwnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Mostly used as a facade for all Petclinic controllers Also a placeholder
 * for @Transactional and @Cacheable annotations
 *
 * @author Michael Isvy
 */
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
	public void saveOwner(final Owner owner) throws DataAccessException {
		this.ownerRepository.save(owner);
		this.userService.saveUser(owner.getUser());
		this.authoritiesService.saveAuthorities(owner.getUser().getUsername(), "owner");
	}

	//FIND OWNER --------------------------------------------------------------

	@Transactional(readOnly = true)
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
