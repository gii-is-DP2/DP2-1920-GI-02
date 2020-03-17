
package org.group2.petclinic.service;

import org.group2.petclinic.repository.CreditcardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreditcardService {

	@Autowired
	private CreditcardRepository creditcardRepository;

	//@Transactional
	//public tipoDevuelve nombreMetodo(lo que recibe) {
	//	return loQueQuiero;
	//}

}
