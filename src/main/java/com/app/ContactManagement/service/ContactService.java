package com.app.ContactManagement.service;

import java.util.List;
import java.util.Set;

import com.app.ContactManagement.model.Contact;
import com.app.ContactManagement.model.User;

public interface ContactService {
	
	public Contact addContact(Contact c);
	
	public boolean isExist(String phone1, User user);
	
	public Set<Contact> findByUserId(User user);
	
	public Set<Contact> searchContacts(String query, User user);
	
	public void deleteContact(String id);

}
