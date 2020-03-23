
package org.group2.petclinic.repository.springdatajpa;

import java.util.List;

import org.group2.petclinic.model.Pet;
import org.group2.petclinic.model.PetType;
import org.group2.petclinic.repository.PetRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

public interface SpringDataPetRepository extends PetRepository, Repository<Pet, Integer> {

	@Override
	@Query("SELECT ptype FROM PetType ptype ORDER BY ptype.name")
	List<PetType> findPetTypes() throws DataAccessException;

	@Override
	@Query("SELECT pet FROM Pet pet WHERE pet.owner.id = ?1")
	List<Pet> findPetsByOwnerId(final int ownerId);

	@Override
	@Query("SELECT pet FROM Pet pet ORDER BY pet.name")
	List<Pet> findAllPets() throws DataAccessException;
}
