package com.app.ContactManagement.repository;

import java.sql.Date;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.ContactManagement.model.LoginCounter;
import com.app.ContactManagement.model.User;

@Repository
public interface LoginCounterRepository extends JpaRepository<LoginCounter, Long>{
	
	public LoginCounter findBydate(Date date);
	
	public List<LoginCounter> findByUserOrderByDateAsc(User user);
	
	public LoginCounter findBydateAndUser(Date date, User user);
	
}
