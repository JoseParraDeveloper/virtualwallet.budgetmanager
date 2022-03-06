package com.virtualwallet.budgetmanager.service;



import com.virtualwallet.budgetmanager.entities.Operation;

public interface IOperationService {
	public void saveOperation(Operation operation);

	public Operation getOperationById(Long id);

}
