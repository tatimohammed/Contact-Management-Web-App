package com.app.ContactManagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.ContactManagement.model.Groupe;
import com.app.ContactManagement.model.User;

@Repository
public interface GroupRepository extends JpaRepository<Groupe, Long>{
	
	public List<Groupe> findByUser(User user);
	
	public List<Groupe> findByUserAndNameContaining(User user, String name);
	
	public Groupe findByid(Long id);
}
