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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.group2.petclinic.model.Diagnosis;
import org.group2.petclinic.model.Prescription;
import org.group2.petclinic.model.Visit;
import org.group2.petclinic.repository.DiagnosisRepository;
import org.group2.petclinic.repository.PrescriptionRepository;
import org.group2.petclinic.repository.VisitRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DiagnosisService {

	private final DiagnosisRepository diagnosisRepository;
	private final PrescriptionRepository prescriptionRepository;

	@Autowired
	public DiagnosisService(DiagnosisRepository diagnosisRepository, PrescriptionRepository prescriptionRepository) {
		this.diagnosisRepository = diagnosisRepository;
		this.prescriptionRepository = prescriptionRepository;
	}
	
	@Transactional public void saveDiagnosis(Diagnosis diagnosis) throws
	DataAccessException { diagnosisRepository.save(diagnosis); }
	
	@Transactional(readOnly = true) public Diagnosis findDiagnosisById(int id)
	throws DataAccessException { return diagnosisRepository.findById(id); }
	
	@Transactional(readOnly = true) public Collection<Diagnosis> findDiagnostics()
	throws DataAccessException { return diagnosisRepository.findAll(); }
	

}
