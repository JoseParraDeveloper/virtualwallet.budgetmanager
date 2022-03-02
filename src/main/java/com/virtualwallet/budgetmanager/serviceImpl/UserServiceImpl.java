package com.virtualwallet.budgetmanager.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.virtualwallet.budgetmanager.entities.User;
import com.virtualwallet.budgetmanager.repository.IUserRepository;
import com.virtualwallet.budgetmanager.service.IUserService;

@Service
public class UserServiceImpl implements IUserService {

	@Autowired
	private IUserRepository userRepository;

	@Override
	public User findById(Long id) {
		return userRepository.getById(id);
	}

	@Override
	public void saveUser(User user) {
		userRepository.save(user);

	}

	@Override
	public User findByUsername(String username) {
		return userRepository.findByUsername(username).orElse(null);
	}

}
