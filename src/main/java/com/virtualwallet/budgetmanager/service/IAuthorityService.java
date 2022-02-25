package com.virtualwallet.budgetmanager.service;

import java.util.List;

import com.virtualwallet.budgetmanager.entities.AuthorityEntity;
import com.virtualwallet.budgetmanager.exceptions.AuthorityNotFoundException;

public interface IAuthorityService {

	public void saveAuthority(AuthorityEntity authorityEntity);

	public AuthorityEntity getByIdAuthority(Long id) throws AuthorityNotFoundException;

	public void insertAuthorityPerson(Long idPerson, Long idAuthority);

	public List<AuthorityEntity> getAllAuthorities();
}
