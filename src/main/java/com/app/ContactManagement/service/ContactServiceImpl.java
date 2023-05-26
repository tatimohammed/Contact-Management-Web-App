package com.app.ContactManagement.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.ContactManagement.model.Contact;
import com.app.ContactManagement.model.User;
import com.app.ContactManagement.repository.ContactRepository;

@Service
public class ContactServiceImpl implements ContactService{
	
	@Autowired
	private ContactRepository contactRepository;

	@Override
	public Contact addContact(Contact c) {
		
		return contactRepository.save(c);
	}

	@Override
	public boolean isExist(String phone1, User user) {
		
		Contact c = contactRepository.findContactByPhone1AndUserId(phone1, user);
		if (c != null) {
			return true;
		}
		
		return false;
	}

	@Override
	public List<Contact> findByUserId(User user) {
		
		return contactRepository.findByUserIdOrderByLastNameAsc(user);
	}

}
