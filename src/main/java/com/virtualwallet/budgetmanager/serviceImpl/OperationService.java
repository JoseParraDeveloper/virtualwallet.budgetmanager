package com.virtualwallet.budgetmanager.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.virtualwallet.budgetmanager.entities.Operation;
import com.virtualwallet.budgetmanager.exceptions.OperationNotFoundException;
import com.virtualwallet.budgetmanager.repository.IOperationRepository;
import com.virtualwallet.budgetmanager.service.IOperationService;

@Service
public class OperationService implements IOperationService {

	@Autowired
	private IOperationRepository operationRepository;

	@Override
	public void saveOperation(Operation operation) {
		operationRepository.save(operation);

	}

	@Override
	public Operation getOperationById(Long id) {

		return operationRepository.findById(id)
				.orElseThrow(() -> new OperationNotFoundException("No Esta Registrada la Operación en la Aplicación"));
	}

}
