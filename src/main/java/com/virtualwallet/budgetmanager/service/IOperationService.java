package com.virtualwallet.budgetmanager.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import com.virtualwallet.budgetmanager.entities.Operation;

public interface IOperationService {
	public void saveOperation(Operation operation);

	public Operation getOperationById(Long id);

	public List<Operation> findAllOperations(Long idPerson, Pageable pageable);

	public BigDecimal totalBalance(@Param("idPerson") Long id, @Param("typeCoin") String typeCoin);

	public Page<Operation> findAllOperationByCoin(@Param("idPerson") Long id, @Param("typeCoin") String typeCoin,
			Pageable pageable);

	public Page<Operation> findAllOperationByTypeOperationAndCoin(@Param("idPerson") Long id,
			@Param("typeCoin") String typeCoin, @Param("typeOperation") String typeOperation, Pageable pageable);

}
