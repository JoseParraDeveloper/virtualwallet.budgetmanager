package com.virtualwallet.budgetmanager.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.virtualwallet.budgetmanager.entities.PendingPayment;
import com.virtualwallet.budgetmanager.exceptions.PendingPaymentNotFoundException;

public interface IPendingPaymentService {
	public Page<PendingPayment> listPendingPayments(Long id, Pageable pageable);

	public void createPendingPayment(PendingPayment pendingPayment);

	public PendingPayment findPendingPaymentEntityById(Long id) throws PendingPaymentNotFoundException;

	public void deletePendingPaymentEntityById(Long id) throws PendingPaymentNotFoundException;

	public Page<PendingPayment> findAllPendingPaymentByCoin(Long idPerson, String typeCoin, Pageable pageable);
}
