package com.virtualwallet.budgetmanager.DTOs;

import java.math.BigDecimal;
import java.util.Date;

public class OperationDTO {

	private String concept;
	private BigDecimal amount;
	private Date date;

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

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

}
