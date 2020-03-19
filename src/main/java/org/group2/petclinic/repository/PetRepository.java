
package org.group2.petclinic.repository;

import java.util.List;

import org.group2.petclinic.model.BaseEntity;
import org.group2.petclinic.model.Pet;
import org.group2.petclinic.model.PetType;
import org.springframework.dao.DataAccessException;

/**
 * Repository class for <code>Pet</code> domain objects All method names are compliant
 * with Spring Data naming conventions so this interface can easily be extended for Spring
 * Data See here:
 * http://static.springsource.org/spring-data/jpa/docs/current/reference/html/jpa.repositories.html#jpa.query-methods.query-creation
 *
 * @author Ken Krebs
 * @author Juergen Hoeller
 * @author Sam Brannen
 * @author Michael Isvy
 */
public interface PetRepository {

	/**
	 * Retrieve all <code>PetType</code>s from the data store.
	 *
	 * @return a <code>Collection</code> of <code>PetType</code>s
	 */
	List<PetType> findPetTypes() throws DataAccessException;

	/**
	 * Retrieve a <code>Pet</code> from the data store by id.
	 *
	 * @param id
	 *            the id to search for
	 * @return the <code>Pet</code> if found
	 * @throws org.springframework.dao.DataRetrievalFailureException
	 *             if not found
	 */
	Pet findById(int id) throws DataAccessException;

	/**
	 * Save a <code>Pet</code> to the data store, either inserting or updating it.
	 *
	 * @param pet
	 *            the <code>Pet</code> to save
	 * @see BaseEntity#isNew
	 */
	void save(Pet pet) throws DataAccessException;

	List<Pet> findPetsByOwnerId(final int ownerId) throws DataAccessException;

	List<Pet> findAllPets() throws DataAccessException;

}
