
package org.group2.petclinic.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class PrescriptionRepositoryTests {

	@Autowired
	private PetRepository petRepository;


}
