package com.app.ContactManagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.ContactManagement.model.Contact;
import com.app.ContactManagement.model.ContactGroup;
import com.app.ContactManagement.model.Groupe;

@Repository
public interface ContactGroupRepository extends JpaRepository<ContactGroup, Long> {
	
	public List<ContactGroup> findBygroupe(Groupe groupe);
	
	public List<ContactGroup> findBycontact(Contact contact); 
	
	
}
