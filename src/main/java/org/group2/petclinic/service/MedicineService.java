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
import java.util.List;

import org.group2.petclinic.model.Medicine;
import org.group2.petclinic.model.Prescription;
import org.group2.petclinic.repository.MedicineRepository;
import org.group2.petclinic.repository.PrescriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MedicineService {

	private final MedicineRepository medicineRepository;
	private final PrescriptionRepository prescriptionRepository;

	@Autowired
	public MedicineService(MedicineRepository medicineRepository, PrescriptionRepository prescriptionRepository) {
		this.medicineRepository = medicineRepository;
		this.prescriptionRepository = prescriptionRepository;
	}

	@Transactional
	public void saveMedicine(Medicine medicine) throws DataAccessException {
		medicineRepository.save(medicine);
	}
	
	@Transactional
	public void deleteMedicine(Medicine medicine) throws DataAccessException {
		medicineRepository.delete(medicine);
	}
	
	@Transactional(readOnly = true)
	public Medicine findMedicineById(Integer medicineId) throws DataAccessException {
		return medicineRepository.findMedicineById(medicineId);
	}

	@Transactional(readOnly = true)
	public Collection<Medicine> findMedicines() throws DataAccessException {
		return medicineRepository.findAll();
	}
	
	public Boolean isInUse(Medicine medicine) throws DataAccessException {
		List<Prescription> prescriptions = this.prescriptionRepository.findPrescriptionsWithMedicine(medicine);
		return !prescriptions.isEmpty();
	}

}
