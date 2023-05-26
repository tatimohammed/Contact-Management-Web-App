package com.app.ContactManagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.ContactManagement.model.Trash;

@Repository
public interface TrashRepository extends JpaRepository<Trash, Long>{
	
	public List<Trash> findByuserId(Long u);
	
	public Trash findByid(Long id);

}
