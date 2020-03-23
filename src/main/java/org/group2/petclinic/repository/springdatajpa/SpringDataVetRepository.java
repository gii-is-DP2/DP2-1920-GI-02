
package org.group2.petclinic.repository.springdatajpa;

import org.group2.petclinic.model.Vet;
import org.group2.petclinic.repository.VetRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

public interface SpringDataVetRepository extends VetRepository, Repository<Vet, Integer> {

	@Override
	@Query("SELECT vet FROM Vet vet WHERE vet.user.username =:username")
	Vet findByUsername(@Param("username") String username);
}
