package com.virtualwallet.budgetmanager.DTOs;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.virtualwallet.budgetmanager.enumTypes.TypeCoin;

public class AdvancedSearchDTO {
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date dateFrom;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date dateTo;
	private BigDecimal amountFrom;
	private BigDecimal amountTo;
	private String typeOperation;
	private TypeCoin coin;

	public Date getDateFrom() {
		return dateFrom;
	}

	public void setDateFrom(Date dateFrom) {
		this.dateFrom = dateFrom;
	}

	public Date getDateTo() {
		return dateTo;
	}

	public void setDateTo(Date dateTo) {
		this.dateTo = dateTo;
	}

	public BigDecimal getAmountFrom() {
		return amountFrom;
	}

	public void setAmountFrom(BigDecimal amountFrom) {
		this.amountFrom = amountFrom;
	}

	public BigDecimal getAmountTo() {
		return amountTo;
	}

	public void setAmountTo(BigDecimal amountTo) {
		this.amountTo = amountTo;
	}

	public String getTypeOperation() {
		return typeOperation;
	}

	public void setTypeOperation(String typeOperation) {
		this.typeOperation = typeOperation;
	}

	public TypeCoin getCoin() {
		return coin;
	}

	public void setCoin(TypeCoin coin) {
		this.coin = coin;
	}

}
