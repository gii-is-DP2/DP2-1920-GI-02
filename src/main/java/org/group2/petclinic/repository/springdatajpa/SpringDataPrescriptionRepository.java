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
package org.group2.petclinic.repository.springdatajpa;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.group2.petclinic.model.Diagnosis;
import org.group2.petclinic.model.Prescription;
import org.group2.petclinic.model.Visit;
import org.group2.petclinic.repository.DiagnosisRepository;
import org.group2.petclinic.repository.PrescriptionRepository;
import org.group2.petclinic.repository.VisitRepository;

/**
 * Spring Data JPA specialization of the {@link VisitRepository} interface
 *
 * @author Michael Isvy
 * @since 15.1.2013
 */
public interface SpringDataPrescriptionRepository extends PrescriptionRepository, Repository<Prescription, Integer> {

	@Query("select p from Prescription p where p.id = ?1")
	Prescription findById(int prescriptionId);
	
}
