
package org.group2.petclinic.repository.springdatajpa;

import org.group2.petclinic.model.Medicine;
import org.group2.petclinic.repository.MedicineRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

public interface SpringDataMedicineRepository extends MedicineRepository, Repository<Medicine, Integer> {

	@Override
	@Query("SELECT m FROM Medicine m WHERE m.id = ?1")
	Medicine findMedicineById(Integer medicineId) throws DataAccessException;

}
