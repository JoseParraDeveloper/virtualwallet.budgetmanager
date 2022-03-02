package com.virtualwallet.budgetmanager.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import com.virtualwallet.budgetmanager.entities.Person;
import com.virtualwallet.budgetmanager.exceptions.PersonNotFoundException;

public interface IPersonService {

	public void savePerson(Person personEntity);

	public Person getPersonById(Long id) throws PersonNotFoundException;

	public void deletePersonById(Long id) throws PersonNotFoundException;

	public Page<Person> getListPerson(Pageable pageable);

	public Person findByEmail(String email);

	public List<Person> getAllPerson();

	public Page<Person> getListPersonActivas(@Param("isEnabled") Boolean isEnabled, Pageable pageable);
}
