package com.virtualwallet.budgetmanager.repository;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.virtualwallet.budgetmanager.entities.Operation;

@Repository
public interface IOperationRepository extends JpaRepository<Operation, Long> {
	@Query(value = "SELECT * FROM operations WHERE person_id = :idPerson ORDER BY date DESC", nativeQuery = true)
	public List<Operation> findAllOperations(@Param("idPerson") Long id, Pageable pageable);

	@Query(value = "SELECT SUM(amount) FROM operations WHERE person_id = :idPerson AND type_coin = :typeCoin", nativeQuery = true)
	public BigDecimal totalBalance(@Param("idPerson") Long id, @Param("typeCoin") String typeCoin);

	@Query(value = "SELECT * FROM operations WHERE person_id = :idPerson AND type_coin = :typeCoin ORDER BY date DESC", nativeQuery = true)
	public Page<Operation> findAllOperationByCoin(@Param("idPerson") Long id, @Param("typeCoin") String typeCoin,
			Pageable pageable);

	@Query(value = "SELECT * FROM operations WHERE person_id = :idPerson AND type_coin = :typeCoin AND type_operation = :typeOperation ORDER BY date DESC", nativeQuery = true)
	public Page<Operation> findAllOperationByTypeOperationAndCoin(@Param("idPerson") Long id,
			@Param("typeCoin") String typeCoin, @Param("typeOperation") String typeOperation, Pageable pageable);

	@Query(value = "SELECT * FROM operations WHERE person_id = :idPerson AND type_operation IN :typeOperations AND type_coin = :typeCoin "
			+ " AND date BETWEEN :dateFrom AND :dateTo AND amount BETWEEN :amountFrom AND :amountTo ORDER BY date DESC", nativeQuery = true)
	public Page<Operation> findAllByAdvancedSearch(@Param("idPerson") Long id,
			@Param("typeOperations") Collection<String> typeOperations, @Param("typeCoin") String typeCoin,
			@Param("dateFrom") Date dateFrom, @Param("dateTo") Date dateTo, @Param("amountFrom") BigDecimal amountFrom,
			@Param("amountTo") BigDecimal amountTo, Pageable pageable);

}
