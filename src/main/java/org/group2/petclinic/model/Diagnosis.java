/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.group2.petclinic.model;
 
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlElement;

import org.springframework.beans.support.MutableSortDefinition;
import org.springframework.beans.support.PropertyComparator;
import org.springframework.core.style.ToStringCreator;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;
import lombok.EqualsAndHashCode;
 
@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "diagnostics")
public class Diagnosis extends BaseEntity {
 
	
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	@Column(name = "date")
    @NotNull
    private LocalDate date;
 
    @Column(name = "description")
    @NotEmpty
    private String description;
 
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(name = "diagnosis_prescriptions", joinColumns = @JoinColumn(name = "diagnosis_id"),
			inverseJoinColumns = @JoinColumn(name = "prescription_id"))
	private Set<Prescription> prescriptions;
    
    public LocalDate getDate() {
        return this.date;
    }
 
    public void setDate(LocalDate date) {
        this.date = date;
    }
   
    public String getDescription() {
        return this.description;
    }
 
    public void setDescription(String description) {
        this.description = description;
    }
 
	protected Set<Prescription> getPrescriptionsInternal() {
		if (this.prescriptions == null) {
			this.prescriptions = new HashSet<>();
		}
		return this.prescriptions;
	}

	protected void setPrescriptionsInternal(Set<Prescription> prescriptions) {
		this.prescriptions = prescriptions;
	}

	@XmlElement
	public List<Prescription> getPrescriptions() {
		List<Prescription> sortedPrecs = new ArrayList<>(getPrescriptionsInternal());
		PropertyComparator.sort(sortedPrecs, new MutableSortDefinition("frequency", true, true));
		return Collections.unmodifiableList(sortedPrecs);
	}

	public int getNrOfPrescriptions() {
		return getPrescriptionsInternal().size();
	}

	public void addPrescription(Prescription prescription) {
		getPrescriptionsInternal().add(prescription);
	}
 
   
    public boolean removePrescription(Prescription prescription) {
        return getPrescriptions().remove(prescription);
    }
 
    @Override
    public String toString() {
        return new ToStringCreator(this)
 
                .append("id", this.getId()).append("new", this.isNew()).append("date", this.getDate())
                .append("description", this.getDescription()).toString();
    }
 

	
}