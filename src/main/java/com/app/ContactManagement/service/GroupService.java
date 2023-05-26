package com.app.ContactManagement.service;

import java.util.List;

import com.app.ContactManagement.model.ContactGroup;
import com.app.ContactManagement.model.User;

public interface GroupService {
	
	public ContactGroup addGroup(ContactGroup group);
	
	public List<ContactGroup> listOfGroups(User user);

}
