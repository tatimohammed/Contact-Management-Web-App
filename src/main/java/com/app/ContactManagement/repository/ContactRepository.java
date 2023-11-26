package com.app.ContactManagement.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.ContactManagement.model.Contact;
import com.app.ContactManagement.model.User;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {
	
	public Contact findContactByPhone1AndUserId(String phone1, User user);
	
	public Contact findByid(Long id);
	
	Set<Contact> findByUserIdOrderByLastNameAsc(User user);
	
	Set<Contact> findByUserIdAndLastNameContainingOrderByLastNameAsc(User user, String query);
	
	Set<Contact> findByUserIdAndPhone1ContainingOrderByLastNameAsc(User user, String query);
	
	Set<Contact> findByUserIdAndPhone2ContainingOrderByLastNameAsc(User user, String query);
	
}
