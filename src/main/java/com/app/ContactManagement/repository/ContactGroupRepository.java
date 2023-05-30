package com.app.ContactManagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.ContactManagement.model.Contact;
import com.app.ContactManagement.model.ContactGroup;
import com.app.ContactManagement.model.Groupe;
import com.app.ContactManagement.model.User;

@Repository
public interface ContactGroupRepository extends JpaRepository<ContactGroup, Long> {
	
	public List<ContactGroup> findBygroupeAndUser(Groupe groupe, User user);
	
	public List<ContactGroup> findBycontact(Contact contact); 
	
	public ContactGroup findBygroupeAndUserAndContact(Groupe groupe, User user, Contact contact);
	
}
