
package org.group2.petclinic.service;

import java.util.List;

import javax.transaction.Transactional;

import org.group2.petclinic.model.Creditcard;
import org.group2.petclinic.repository.CreditcardRepository;
import org.group2.petclinic.repository.springdatajpa.SpringDataCreditcardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

@Service
public class CreditcardService {

	private CreditcardRepository creditcardRepository;


	@Autowired
	public CreditcardService(final SpringDataCreditcardRepository creditcardRepository) {
		this.creditcardRepository = creditcardRepository;
	}

	@Transactional
	public void saveCreditcard(final Creditcard creditcard) throws DataAccessException {
		this.creditcardRepository.save(creditcard);
	}

}
