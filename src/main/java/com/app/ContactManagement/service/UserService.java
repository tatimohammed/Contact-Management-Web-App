package com.app.ContactManagement.service;

import com.app.ContactManagement.model.User;

public interface UserService {
	
	public User addUser(User u); 
	
	public boolean isExist(String username);

}
