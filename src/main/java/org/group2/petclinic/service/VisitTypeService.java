/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.group2.petclinic.service;

import java.util.Collection;

import org.group2.petclinic.model.VisitType;
import org.group2.petclinic.repository.VisitTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class VisitTypeService {

	private final VisitTypeRepository VisitTypeRepository;

	@Autowired
	public VisitTypeService(VisitTypeRepository VisitTypeRepository) {
		this.VisitTypeRepository = VisitTypeRepository;
	}

	@Transactional
	public void saveVisitType(VisitType VisitType) throws DataAccessException {
		VisitTypeRepository.save(VisitType);
	}
	
	@Transactional(readOnly = true)
	public VisitType findVisitTypeById(Integer VisitTypeId) throws DataAccessException {
		return VisitTypeRepository.findVisitTypeById(VisitTypeId);
	}

	@Transactional(readOnly = true)
	public Collection<VisitType> findVisitTypes() throws DataAccessException {
		return VisitTypeRepository.findAll();
	}

}
