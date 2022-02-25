package com.virtualwallet.budgetmanager.repository;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.virtualwallet.budgetmanager.entities.AuthorityEntity;

public interface IAuthorityRepository extends JpaRepository<AuthorityEntity, Long> {
	@Query(value = "SELECT * FROM authorities WHERE id=:id", nativeQuery = true)
	Optional<AuthorityEntity> getByIdAuthority(@Param("id") Long id);

	@Modifying
	@Query(value = "INSERT INTO authorities_users(person_id, authority_id) VALUES (:idPerson,:idAuthority)", nativeQuery = true)
	@Transactional
	void insertAuthorityPerson(@Param("idPerson") Long idPerson, @Param("idAuthority") Long idAuthority);
}
