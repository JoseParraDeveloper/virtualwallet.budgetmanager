package com.virtualwallet.budgetmanager.serviceImpl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

	@Override
	public List<Operation> findAllOperations(Long idPerson, Pageable pageable) {
		return null;
	}

	@Override
	public BigDecimal totalBalance(Long idPerson, String typeCoin) {
		BigDecimal balance = operationRepository.totalBalance(idPerson, typeCoin);
		if (balance == null) {
			balance = new BigDecimal(0);
		}

		return balance;
	}

	@Override
	public Page<Operation> findAllOperationByCoin(Long idPerson, String typeCoin, Pageable pageable) {

		return operationRepository.findAllOperationByCoin(idPerson, typeCoin, pageable);
	}

	@Override
	public Page<Operation> findAllOperationByTypeOperationAndCoin(Long id, String typeCoin, String typeOperation,
			Pageable pageable) {

		return operationRepository.findAllOperationByTypeOperationAndCoin(id, typeCoin, typeOperation, pageable);
	}

}
