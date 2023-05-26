package com.app.ContactManagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.app.ContactManagement.model.Contact;
import com.app.ContactManagement.model.User;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {
	
	public Contact findContactByPhone1AndUserId(String phone1, User user);
	
	public Contact findByid(Long id);
	
	@Query("SELECT c FROM Contact c WHERE c.groupId.id = :groupId")
    List<Contact> findByGroupId(Long groupId);
	
	List<Contact> findByUserIdOrderByLastNameAsc(User user);
	
	List<Contact> findByUserIdAndLastNameContainingOrderByLastNameAsc(User user, String query);
	
	List<Contact> findByUserIdAndPhone1ContainingOrderByLastNameAsc(User user, String query);
	
	List<Contact> findByUserIdAndPhone2ContainingOrderByLastNameAsc(User user, String query);
	
}
