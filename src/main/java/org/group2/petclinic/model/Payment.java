
package org.group2.petclinic.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "payments")
public class Payment extends BaseEntity {

	// ATTRIBUTES -------------------------------------------------------------

	@NotEmpty
	@Pattern(regexp = "^creditcard|cash")
	@Column(name = "method")
	private String			method;

	@Column(name = "moment")
	@DateTimeFormat(pattern = "yyyy/MM/dd HH:mm")
	private LocalDateTime	moment;

	@NotNull
	@Min(1)
	@Column(name = "final_price")
	private Double			finalPrice;

	// RELATIONSHIPS ----------------------------------------------------------

	@ManyToOne(optional = false)
	@JoinColumn(name = "secretary_id")
	private Secretary		secretary;

	@OneToOne(optional = true)
	@JoinColumn(name = "creditcard_id")
	private Creditcard		creditcard;


	// GETTERS / SETTERS ------------------------------------------------------

	public String getMethod() {
		return this.method;
	}

	public void setMethod(final String method) {
		this.method = method;
	}

	public LocalDateTime getMoment() {
		return this.moment;
	}

	public void setMoment(final LocalDateTime moment) {
		this.moment = moment;
	}

	public Double getFinalPrice() {
		return this.finalPrice;
	}

	public void setFinalPrice(final Double finalPrice) {
		this.finalPrice = finalPrice;
	}

	public Creditcard getCreditcard() {
		return this.creditcard;
	}

	public void setCreditcard(final Creditcard creditcard) {
		this.creditcard = creditcard;
	}

	public Secretary getSecretary() {
		return this.secretary;
	}

	public void setSecretary(final Secretary secretary) {
		this.secretary = secretary;
	}

}
