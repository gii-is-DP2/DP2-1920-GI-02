
package org.group2.petclinic.repository.springdatajpa;

import org.group2.petclinic.model.Creditcard;
import org.group2.petclinic.repository.CreditcardRepository;
import org.springframework.data.repository.Repository;

public interface SpringDataCreditcardRepository extends CreditcardRepository, Repository<Creditcard, Integer> {

}
