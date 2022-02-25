package com.virtualwallet.budgetmanager.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.virtualwallet.budgetmanager.entities.PersonEntity;
import com.virtualwallet.budgetmanager.exceptions.PersonNotFoundException;

public interface IPersonService {

	public void savePerson(PersonEntity personEntity);

	public PersonEntity getPersonById(Long id) throws PersonNotFoundException;

	public void deletePersonById(Long id) throws PersonNotFoundException;

	public Page<PersonEntity> getListPerson(Pageable pageable);

	public PersonEntity findByUsername(String username);

	public PersonEntity findByEmail(String email);
}
