package com.virtualwallet.budgetmanager.enumTypes;

public enum TypeOperation {
	INGRESS("INGRESO"), EXPENSES("EGRESO");

	private final String typeOperation;

	private TypeOperation(String typeOperation) {
		this.typeOperation = typeOperation;
	}

	public String getTypeOperation() {
		return typeOperation;
	}

}
