package com.app.ContactManagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.ContactManagement.model.ContactGroup;
import com.app.ContactManagement.model.User;

@Repository
public interface GroupRepository extends JpaRepository<ContactGroup, Long>{
	
	public List<ContactGroup> findByUser(User user);
	
	public List<ContactGroup> findByUserAndNameContaining(User user, String name);
	
	public ContactGroup findByid(Long id);
}
