package com.app.ContactManagement.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.ContactManagement.model.Groupe;
import com.app.ContactManagement.model.User;
import com.app.ContactManagement.repository.GroupRepository;

@Service
public class GroupServiceImpl implements GroupService{
	
	@Autowired
	private GroupRepository groupRepository;

	@Override
	public Groupe addGroup(Groupe group) {
		
		return groupRepository.save(group);
	}

	@Override
	public List<Groupe> listOfGroups(User user) {
		
		return groupRepository.findByUser(user);
	}

}
