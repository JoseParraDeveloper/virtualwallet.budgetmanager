package com.virtualwallet.budgetmanager.serviceImpl;

import java.util.ArrayList;
import java.util.List;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.virtualwallet.budgetmanager.entities.AuthorityEntity;
import com.virtualwallet.budgetmanager.entities.PersonEntity;
import com.virtualwallet.budgetmanager.service.IPersonService;

@Service
@Transactional
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private IPersonService personService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		PersonEntity appPerson = personService.findByUsername(username);

		if (appPerson == null) {
			throw new UsernameNotFoundException("No existe el usuario");
		}

		List<GrantedAuthority> grantList = new ArrayList<GrantedAuthority>();

		for (AuthorityEntity authority : appPerson.getAuthority()) {

			GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(authority.getAuthority().toString());
			grantList.add(grantedAuthority);
		}

		UserDetails personUser = (UserDetails) new User(appPerson.getUsername(), appPerson.getPassword(), grantList);
		return personUser;

	}

}
