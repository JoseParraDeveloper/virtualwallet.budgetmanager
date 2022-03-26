package com.virtualwallet.budgetmanager.entities;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import org.springframework.format.annotation.DateTimeFormat;

import com.virtualwallet.budgetmanager.enumTypes.TypeCoin;

@Entity
@Table(name = "pending_payments")
public class PendingPayment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private Long id;
	@Column
	@NotBlank
	private String concept;
	@Column
	@Min(0)
	private BigDecimal amount;
	@Column
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Temporal(TemporalType.DATE)
	private Date dateExpiration;
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "person_id", nullable = false)
	private Person person;
	@Column
	@Enumerated(EnumType.STRING)
	private TypeCoin typeCoin;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getConcept() {
		return concept;
	}

	public void setConcept(String concept) {
		this.concept = concept;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public Date getDateExpiration() {
		return dateExpiration;
	}

	public void setDateExpiration(Date dateExpiration) {
		this.dateExpiration = dateExpiration;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public TypeCoin getTypeCoin() {
		return typeCoin;
	}

	public void setTypeCoin(TypeCoin typeCoin) {
		this.typeCoin = typeCoin;
	}

}
