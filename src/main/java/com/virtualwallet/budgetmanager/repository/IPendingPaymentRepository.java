package com.virtualwallet.budgetmanager.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.virtualwallet.budgetmanager.entities.PendingPayment;

@Repository
public interface IPendingPaymentRepository extends JpaRepository<PendingPayment, Long> {

	@Query(value = "SELECT * FROM pending_payments WHERE person_id = :idPerson ORDER BY date_expiration ASC", nativeQuery = true)
	public Page<PendingPayment> findPendingPaymentPerson(@Param("idPerson") Long id, Pageable pageable);

	@Query(value = "SELECT * FROM pending_payments WHERE person_id = :idPerson AND type_coin = :typeCoin ORDER BY date_expiration DESC", nativeQuery = true)
	public Page<PendingPayment> findAllPendingPaymentByCoin(@Param("idPerson") Long id,
			@Param("typeCoin") String typeCoin, Pageable pageable);

}
