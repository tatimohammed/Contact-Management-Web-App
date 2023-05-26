package com.app.ContactManagement.service;

import java.util.List;

import com.app.ContactManagement.model.Groupe;
import com.app.ContactManagement.model.User;

public interface GroupService {
	
	public Groupe addGroup(Groupe group);
	
	public List<Groupe> listOfGroups(User user);

}
