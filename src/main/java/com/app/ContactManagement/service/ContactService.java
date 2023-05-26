package com.app.ContactManagement.service;

import java.util.List;

import com.app.ContactManagement.model.Contact;
import com.app.ContactManagement.model.User;

public interface ContactService {
	
	public Contact addContact(Contact c);
	
	public boolean isExist(String phone1, User user);
	
	public List<Contact> findByUserId(User user);

}
