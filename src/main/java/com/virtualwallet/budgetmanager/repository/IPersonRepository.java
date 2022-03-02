package com.virtualwallet.budgetmanager.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.virtualwallet.budgetmanager.entities.Person;

@Repository
public interface IPersonRepository extends JpaRepository<Person, Long> {

	public Optional<Person> findByEmail(String email);

	@Query(value = "SELECT * FROM people WHERE enabled=:isEnabled", nativeQuery = true)
	public Page<Person> getListPersonActivas(@Param("isEnabled") Boolean isEnabled, Pageable pageable);
}
