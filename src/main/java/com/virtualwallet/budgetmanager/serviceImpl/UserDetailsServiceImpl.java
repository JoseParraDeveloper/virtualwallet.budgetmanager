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

import com.virtualwallet.budgetmanager.entities.Authority;
import com.virtualwallet.budgetmanager.service.IUserService;

@Service
@Transactional
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private IUserService userService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		com.virtualwallet.budgetmanager.entities.User user = userService.findByUsername(username);

		if (user == null) {
			throw new UsernameNotFoundException("No existe el usuario");
		}

		List<GrantedAuthority> grantList = new ArrayList<GrantedAuthority>();

		for (Authority authority : user.getAuthority()) {

			GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(authority.getAuthority().toString());
			grantList.add(grantedAuthority);
		}

		UserDetails userDetails = (UserDetails) new User(user.getUsername(), user.getPassword(), grantList);
		return userDetails;

	}

}
