package com.app.ContactManagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.app.ContactManagement.model.User;
import com.app.ContactManagement.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserRepository userRepository;
	
	
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Override
	public User addUser(User u) {
		
		u.setPassword(bCryptPasswordEncoder.encode(u.getPassword()));
		
		
		return userRepository.save(u);
	}

	@Override
	public boolean isExist(String username) {
		
		User u = userRepository.findUserByUsername(username);
		if (u != null) {
			return true;
		}
		
		return false;
	}

}
