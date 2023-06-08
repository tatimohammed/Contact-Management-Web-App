package com.app.ContactManagement.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.ContactManagement.model.Contact;
import com.app.ContactManagement.model.ContactGroup;
import com.app.ContactManagement.model.Trash;
import com.app.ContactManagement.model.User;
import com.app.ContactManagement.repository.ContactGroupRepository;
import com.app.ContactManagement.repository.ContactRepository;
import com.app.ContactManagement.repository.TrashRepository;
import com.app.ContactManagement.utils.NLP;

@Service
public class ContactServiceImpl implements ContactService {

	@Autowired
	private ContactRepository contactRepository;
	
	@Autowired
	private ContactGroupRepository contactGroupRepository;
	
	@Autowired
	private TrashRepository trashRepository;

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
	public Set<Contact> findByUserId(User user) {

		return contactRepository.findByUserIdOrderByLastNameAsc(user);
	}

	@Override
	public Set<Contact> searchContacts(String query, User user) {
		// Regex for Last Name
		String lastNameRegex = "^[a-zA-Z]*$";
		Pattern lastNamePattern = Pattern.compile(lastNameRegex);
		// Matcher for Last Name
		Matcher lastNameMatcher = lastNamePattern.matcher(query);

		// Regex for phone number
		String phoneRegex = "^[0-9]*$";
		Pattern phonePattern = Pattern.compile(phoneRegex);
		// Matcher for Last Name
		Matcher phoneMatcher = phonePattern.matcher(query);
		
		Set<Contact> contacts = null;
		if (lastNameMatcher.matches()) {
			contacts = contactRepository.findByUserIdAndLastNameContainingOrderByLastNameAsc(user, query);
			if (contacts.isEmpty()) {
				System.out.println("NLP");
				Set<Contact> all = findByUserId(user);
				List<Contact> result = new ArrayList<>();
				for(Contact c : all) {
					if(NLP.minEditDistance(c.getLast_name().toLowerCase(), query)<=3){
						System.out.println("min edit distance is "+
								NLP.minEditDistance(c.getLast_name().toLowerCase(), query));
						result.add(c);
						}
				}
				List<Contact> contactsNlp = new ArrayList<>(result);
				Collections.sort(contactsNlp, Comparator.comparingInt(c -> 
						NLP.minEditDistance(c.getLast_name().toLowerCase(), query)));
				
				contacts = new HashSet<>(contactsNlp);
				
			}
		} else if (phoneMatcher.matches()) {
			Set<Contact> contacts1 = contactRepository.findByUserIdAndPhone1ContainingOrderByLastNameAsc(user, query);
			
			Set<Contact> contacts2 = contactRepository.findByUserIdAndPhone2ContainingOrderByLastNameAsc(user, query);
			contacts = new HashSet<>(contacts1);
			contacts.addAll(contacts2);
			
		} 
		return contacts;
	}

	@Override
	public void deleteContact(String id) {
		Contact c = contactRepository.findByid(Long.parseLong(id));
		List<ContactGroup> cg = contactGroupRepository.findBycontact(c);
		for(ContactGroup con : cg) {
			con.setContact(null);
		}
		contactGroupRepository.flush();
		Trash t = new Trash();
		t.setFirst_name(c.getFirst_name());
		t.setLastName(c.getLast_name());
		t.setAddress(c.getAddress());
		t.setEmail_personal(c.getEmail_personal());
		t.setEmail_professional(c.getEmail_professional());
		t.setGender(c.getGender());
		t.setUserId(c.getUserId().getId());
		t.setPhone1(c.getPhone1());
		t.setPhone2(c.getPhone2());

		trashRepository.save(t);
		contactRepository.delete(c);
		contactRepository.flush();
		trashRepository.flush();
		
	}
	

}
