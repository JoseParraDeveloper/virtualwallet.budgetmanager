package com.virtualwallet.budgetmanager.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.virtualwallet.budgetmanager.entities.PersonEntity;
import com.virtualwallet.budgetmanager.exceptions.PersonNotFoundException;
import com.virtualwallet.budgetmanager.repository.IPersonRepository;
import com.virtualwallet.budgetmanager.service.IPersonService;

@Service
public class PersonService implements IPersonService {

	@Autowired
	private IPersonRepository personRepository;

	@Override
	public void savePerson(PersonEntity personEntity) {
		personRepository.save(personEntity);

	}

	@Override
	public PersonEntity getPersonById(Long id) throws PersonNotFoundException {

		PersonEntity person = personRepository.findById(id)
				.orElseThrow(() -> new PersonNotFoundException("No Esta Registrado el Usuario en la Aplicación"));
		return person;
	}

	@Override
	public void deletePersonById(Long id) throws PersonNotFoundException {
		PersonEntity person = personRepository.findById(id)
				.orElseThrow(() -> new PersonNotFoundException("No Esta Registrado el Usuario en la Aplicación"));

		personRepository.deleteById(person.getId());
	}

	@Override
	public Page<PersonEntity> getListPerson(Pageable pageable) {

		return personRepository.findAll(pageable);
	}

	@Override
	public PersonEntity findByEmail(String email) {

		PersonEntity person = personRepository.findByEmail(email).orElse(null);
		return person;
	}

	@Override
	public PersonEntity findByUsername(String username) {
		PersonEntity person = personRepository.findByUsername(username).orElse(null);
		return person;
	}

}
