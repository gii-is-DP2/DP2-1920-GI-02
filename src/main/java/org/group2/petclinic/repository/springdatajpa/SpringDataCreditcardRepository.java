
package org.group2.petclinic.repository.springdatajpa;

import java.util.List;

import org.group2.petclinic.model.Creditcard;
import org.group2.petclinic.model.Payment;
import org.group2.petclinic.repository.CreditcardRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

public interface SpringDataCreditcardRepository extends CreditcardRepository, Repository<Creditcard, Integer> {

}
