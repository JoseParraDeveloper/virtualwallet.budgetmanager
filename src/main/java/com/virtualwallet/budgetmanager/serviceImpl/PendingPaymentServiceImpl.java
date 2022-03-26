package com.virtualwallet.budgetmanager.serviceImpl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.virtualwallet.budgetmanager.entities.PendingPayment;
import com.virtualwallet.budgetmanager.exceptions.PendingPaymentNotFoundException;
import com.virtualwallet.budgetmanager.repository.IPendingPaymentRepository;
import com.virtualwallet.budgetmanager.service.IPendingPaymentService;

@Service
public class PendingPaymentServiceImpl implements IPendingPaymentService {

	@Autowired
	private IPendingPaymentRepository pendingPaymentRepository;

	@Override
	public void createPendingPayment(PendingPayment pendingPayment) {
		pendingPaymentRepository.save(pendingPayment);
	}

	@Override
	public PendingPayment findPendingPaymentEntityById(Long id) throws PendingPaymentNotFoundException {

		Optional<PendingPayment> pendingPayment = pendingPaymentRepository.findById(id);

		if (pendingPayment.isEmpty()) {
			throw new PendingPaymentNotFoundException("Pago pendiente no esta registrado");
		}

		return pendingPayment.get();
	}

	@Override
	public void deletePendingPaymentEntityById(Long id) throws PendingPaymentNotFoundException {
		Optional<PendingPayment> pendingPayment = pendingPaymentRepository.findById(id);

		if (pendingPayment.isEmpty()) {
			throw new PendingPaymentNotFoundException("Pago pendiente no esta registrado");
		}

		pendingPaymentRepository.deleteById(id);
	}

	@Override
	public Page<PendingPayment> listPendingPayments(Long id, Pageable pageable) {
		return pendingPaymentRepository.findPendingPaymentPerson(id, pageable);
	}

	@Override
	public Page<PendingPayment> findAllPendingPaymentByCoin(Long idPerson, String typeCoin, Pageable pageable) {
		return pendingPaymentRepository.findAllPendingPaymentByCoin(idPerson, typeCoin, pageable);
	}

}
