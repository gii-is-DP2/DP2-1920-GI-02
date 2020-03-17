
package org.group2.petclinic.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "creditcards")
public class Creditcard extends BaseEntity {

	// ATTRIBUTES -------------------------------------------------------------

	@NotEmpty
	@Column(name = "holder")
	private String	holder;

	@NotEmpty
	@Column(name = "brand")
	private String	brand;

	@NotEmpty
	@Column(name = "number")
	private String	number;

	@NotEmpty
	@Min(1)
	@Max(12)
	@Column(name = "exp_month")
	private Integer	expMonth;

	@NotEmpty
	@Column(name = "exp_year")
	private Integer	expYear;

	@NotEmpty
	@Column(name = "security_code")
	private String	securityCode;

	// RELATIONSHIPS ----------------------------------------------------------


	// GETTERS / SETTERS ------------------------------------------------------

	public String getHolder() {
		return this.holder;
	}

	public void setHolder(final String holder) {
		this.holder = holder;
	}

	public String getBrand() {
		return this.brand;
	}

	public void setBrand(final String brand) {
		this.brand = brand;
	}

	public String getNumber() {
		return this.number;
	}

	public void setNumber(final String number) {
		this.number = number;
	}

	public Integer getExpMonth() {
		return this.expMonth;
	}

	public void setExpMonth(final Integer expMonth) {
		this.expMonth = expMonth;
	}

	public Integer getExpYear() {
		return this.expYear;
	}

	public void setExpYear(final Integer expYear) {
		this.expYear = expYear;
	}

	public String getSecurityCode() {
		return this.securityCode;
	}

	public void setSecurityCode(final String securityCode) {
		this.securityCode = securityCode;
	}
}
