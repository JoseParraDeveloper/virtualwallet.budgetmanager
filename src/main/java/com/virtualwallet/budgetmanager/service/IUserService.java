package com.virtualwallet.budgetmanager.service;

import com.virtualwallet.budgetmanager.entities.User;

public interface IUserService {

	public User findById(Long id);

	public void saveUser(User user);

	public User findByUsername(String username);
}
