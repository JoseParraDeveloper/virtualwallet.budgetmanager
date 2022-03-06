package com.virtualwallet.budgetmanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.virtualwallet.budgetmanager.entities.Operation;

@Repository
public interface IOperationRepository extends JpaRepository<Operation, Long> {

}
