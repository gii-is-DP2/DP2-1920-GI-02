
package org.group2.petclinic.service;

import java.util.Collection;

import org.group2.petclinic.model.Pet;
import org.group2.petclinic.model.PetType;
import org.group2.petclinic.repository.PetRepository;
import org.group2.petclinic.service.exceptions.DuplicatedPetNameException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
public class PetService {

	private PetRepository petRepository;


	// CONSTRUCTOR ------------------------------------------------------------

	@Autowired
	public PetService(final PetRepository petRepository) {
		this.petRepository = petRepository;
	}

	// SAVE PET ---------------------------------------------------------------

	@Transactional(rollbackFor = DuplicatedPetNameException.class)
	public void savePet(final Pet pet) throws DataAccessException, DuplicatedPetNameException {
		Pet otherPet = pet.getOwner().getPetwithIdDifferent(pet.getName(), pet.getId());
		if (StringUtils.hasLength(pet.getName()) && otherPet != null && otherPet.getId() != pet.getId()) {
			throw new DuplicatedPetNameException();
		} else {
			this.petRepository.save(pet);
		}
	}

	// FIND PET ---------------------------------------------------------------

	@Transactional(readOnly = true)
	public Pet findPetById(final int id) throws DataAccessException {
		return this.petRepository.findById(id);
	}

	// FIND PETS --------------------------------------------------------------

	@Transactional(readOnly = true)
	public Collection<Pet> findAllPets() throws DataAccessException {
		return this.petRepository.findAllPets();
	}

	@Transactional(readOnly = true)
	public Collection<Pet> findPetsByOwnerId(final int ownerId) throws DataAccessException {
		return this.petRepository.findPetsByOwnerId(ownerId);
	}

	// FIND PET TYPES ---------------------------------------------------------

	@Transactional(readOnly = true)
	public Collection<PetType> findPetTypes() throws DataAccessException {
		return this.petRepository.findPetTypes();
	}
}
