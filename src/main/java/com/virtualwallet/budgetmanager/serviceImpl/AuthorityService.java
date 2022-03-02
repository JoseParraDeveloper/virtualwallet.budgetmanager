package com.virtualwallet.budgetmanager.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.virtualwallet.budgetmanager.entities.Authority;
import com.virtualwallet.budgetmanager.exceptions.AuthorityNotFoundException;
import com.virtualwallet.budgetmanager.repository.IAuthorityRepository;
import com.virtualwallet.budgetmanager.service.IAuthorityService;

@Service
public class AuthorityService implements IAuthorityService {

	@Autowired
	private IAuthorityRepository authorityRepository;

	@Override
	public Authority getByIdAuthority(Long id) throws AuthorityNotFoundException {

		Authority authority = authorityRepository.getByIdAuthority(id)
				.orElseThrow(() -> new AuthorityNotFoundException("Rol con " + id + "  No Existe."));

		return authority;
	}

	@Override
	public void insertAuthorityPerson(Long idPerson, Long idAuthority) {
		authorityRepository.insertAuthorityPerson(idPerson, idAuthority);

	}

	@Override
	public List<Authority> getAllAuthorities() {

		return authorityRepository.findAll();
	}

	@Override
	public void saveAuthority(Authority authorityEntity) {

		authorityRepository.save(authorityEntity);
	}

}
