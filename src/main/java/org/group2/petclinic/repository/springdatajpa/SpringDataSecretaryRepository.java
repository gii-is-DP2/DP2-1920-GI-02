
package org.group2.petclinic.repository.springdatajpa;

import org.group2.petclinic.model.Secretary;
import org.group2.petclinic.repository.SecretaryRepository;
import org.springframework.data.repository.Repository;

public interface SpringDataSecretaryRepository extends SecretaryRepository, Repository<Secretary, Integer> {

	//@Override
	//@Query("SELECT DISTINCT owner FROM Owner owner left join fetch owner.pets WHERE owner.lastName LIKE :lastName%")
	//public Collection<Owner> findByLastName(@Param("lastName") String lastName);

}
