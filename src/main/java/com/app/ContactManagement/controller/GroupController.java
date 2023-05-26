package com.app.ContactManagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.app.ContactManagement.model.Contact;
import com.app.ContactManagement.model.ContactGroup;
import com.app.ContactManagement.repository.ContactRepository;
import com.app.ContactManagement.repository.GroupRepository;
import com.app.ContactManagement.service.GroupServiceImpl;
import com.app.ContactManagement.service.MyUserDetails;
import com.app.ContactManagement.utils.AddContactToGroup;
import com.app.ContactManagement.utils.DeleteGroup;

@Controller
public class GroupController {
	
	@Autowired
	private GroupServiceImpl groupServiceImpl;
	
	@Autowired
	private ContactRepository contactRepository;
	
	@Autowired
	private GroupRepository groupRepository;

	@PostMapping("/addGroup")
	public String addGroup(@RequestParam(name = "groupname") String groupname, Model model) {
		
		MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		
		if (groupname != "") {
			ContactGroup group = new ContactGroup(groupname, userDetails.getUser());
			
			groupServiceImpl.addGroup(group);
		}
		
		
		
		List<ContactGroup> groups = groupServiceImpl.listOfGroups(userDetails.getUser());
		
		model.addAttribute("groups", groups);
		
		return "redirect:/group";
	}
	
	@PostMapping("/deleteGroup")
	public String deleteGroup(@ModelAttribute("DeleteGroup") DeleteGroup formData, Model model) {
        
        String groupId = formData.getGroupIdToDelete();
        System.out.println(groupId);
        
        ContactGroup group = groupRepository.findByid(Long.parseLong(groupId));
        
        List<Contact> contacts = contactRepository.findByGroupId(Long.parseLong(groupId));
        
        for (Contact contact: contacts) {
        	contact.setGroupId(null);
        }
        contactRepository.flush();
		
        groupRepository.delete(group);
        groupRepository.flush();
        
		return "redirect:/group";
	}
	
	@PostMapping("/form")
    public String processForm(@ModelAttribute("addContactToGroup") AddContactToGroup formData) {
        List<String> contactIds = formData.getContactsList();
        
        String groupId = formData.getGroupId();
        System.out.println(groupId);
        
        ContactGroup group = groupRepository.findByid(Long.parseLong(groupId));
        
        for (String contactId: contactIds) {
        	Contact contact = contactRepository.findByid(Long.parseLong(contactId));
        	contact.setGroupId(group);
        	
        }
        contactRepository.flush();
        
        return "redirect:/group";
    }
	
}
