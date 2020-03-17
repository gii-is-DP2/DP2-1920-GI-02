
package org.group2.petclinic.service;

import org.group2.petclinic.repository.SecretaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SecretaryService {

	@Autowired
	private SecretaryRepository secretaryRepository;

	//@Transactional
	//public tipoDevuelve nombreMetodo(lo que recibe) {
	//	return loQueQuiero;
	//}

}
