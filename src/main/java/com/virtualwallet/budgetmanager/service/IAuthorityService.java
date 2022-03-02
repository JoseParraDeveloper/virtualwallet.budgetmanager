package com.virtualwallet.budgetmanager.service;

import java.util.List;

import com.virtualwallet.budgetmanager.entities.Authority;
import com.virtualwallet.budgetmanager.exceptions.AuthorityNotFoundException;

public interface IAuthorityService {

	public void saveAuthority(Authority authorityEntity);

	public Authority getByIdAuthority(Long id) throws AuthorityNotFoundException;

	public void insertAuthorityPerson(Long idPerson, Long idAuthority);

	public List<Authority> getAllAuthorities();
}
