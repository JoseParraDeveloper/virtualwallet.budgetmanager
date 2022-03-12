package com.virtualwallet.budgetmanager.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.virtualwallet.budgetmanager.entities.User;
import com.virtualwallet.budgetmanager.service.IUserService;

@Component
public class PersonAuthenticationUtil {
	@Autowired
	private IUserService userService;

	public User personAuthentication() {

		User user = null;

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null) {
			UserDetails userDetail = (UserDetails) auth.getPrincipal();
			user = userService.findByUsername(userDetail.getUsername());
		}

		return user;

	}

}
