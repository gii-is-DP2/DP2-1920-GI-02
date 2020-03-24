
package org.group2.petclinic.repository.springdatajpa;

import org.group2.petclinic.model.Medicine;
import org.group2.petclinic.repository.MedicineRepository;
import org.springframework.data.repository.Repository;

public interface SpringDataMedicineRepository extends MedicineRepository, Repository<Medicine, Integer> {

}
