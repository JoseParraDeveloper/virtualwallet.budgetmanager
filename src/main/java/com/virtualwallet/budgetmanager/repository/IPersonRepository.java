package com.virtualwallet.budgetmanager.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.virtualwallet.budgetmanager.entities.PersonEntity;

@Repository
public interface IPersonRepository extends JpaRepository<PersonEntity, Long> {

	public Optional<PersonEntity> findByUsername(String username);

	public Optional<PersonEntity> findByEmail(String email);
}
