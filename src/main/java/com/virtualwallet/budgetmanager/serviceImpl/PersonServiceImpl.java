package com.virtualwallet.budgetmanager.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.virtualwallet.budgetmanager.entities.Person;
import com.virtualwallet.budgetmanager.exceptions.PersonNotFoundException;
import com.virtualwallet.budgetmanager.repository.IPersonRepository;
import com.virtualwallet.budgetmanager.service.IPersonService;

@Service
public class PersonServiceImpl implements IPersonService {

	@Autowired
	private IPersonRepository personRepository;

	@Override
	public void savePerson(Person personEntity) {
		personRepository.save(personEntity);

	}

	@Override
	public Person getPersonById(Long id) throws PersonNotFoundException {

		Person person = personRepository.findById(id)
				.orElseThrow(() -> new PersonNotFoundException("No Esta Registrado el Usuario en la Aplicación"));
		return person;
	}

	@Override
	public void deletePersonById(Long id) throws PersonNotFoundException {
		Person person = personRepository.findById(id)
				.orElseThrow(() -> new PersonNotFoundException("No Esta Registrado el Usuario en la Aplicación"));

		personRepository.deleteById(person.getId());
	}

	@Override
	public Page<Person> getListPerson(Pageable pageable) {

		return personRepository.findAll(pageable);
	}

	@Override
	public Person findByEmail(String email) {

		Person person = personRepository.findByEmail(email).orElse(null);
		return person;
	}

	@Override
	public List<Person> getAllPerson() {

		return personRepository.findAll();
	}

	@Override
	public Page<Person> getListPersonActivas(Boolean isEnabled, Pageable pageable) {

		return personRepository.getListPersonActivas(isEnabled, pageable);
	}

}
