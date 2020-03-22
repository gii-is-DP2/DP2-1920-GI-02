
package org.group2.petclinic.service;

import javax.transaction.Transactional;

import org.group2.petclinic.model.Secretary;
import org.group2.petclinic.repository.springdatajpa.SpringDataSecretaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SecretaryService {

	@Autowired
	private SpringDataSecretaryRepository secretaryRepository;


	@Transactional
	public Secretary findSecretaryByName(final String username) {
		return this.secretaryRepository.findSecretaryByName(username);
	}

}
