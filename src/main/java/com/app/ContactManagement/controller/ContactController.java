package com.app.ContactManagement.controller;

import java.security.Principal;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.app.ContactManagement.model.Contact;
import com.app.ContactManagement.model.ContactGroup;
import com.app.ContactManagement.model.Groupe;
import com.app.ContactManagement.model.Trash;
import com.app.ContactManagement.repository.ContactGroupRepository;
import com.app.ContactManagement.repository.ContactRepository;
import com.app.ContactManagement.repository.TrashRepository;
import com.app.ContactManagement.repository.UserRepository;
import com.app.ContactManagement.service.ContactServiceImpl;
import com.app.ContactManagement.service.GroupServiceImpl;
import com.app.ContactManagement.service.MyUserDetails;

@Controller
public class ContactController {

	@Autowired
	private ContactServiceImpl contactServiceImpl;

	@Autowired
	private ContactRepository contactRepository;
	
	@Autowired
	private GroupServiceImpl groupServiceImpl;
	
	@Autowired
	private ContactGroupRepository contactGroupRepository; 
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private TrashRepository trashRepository;
	
	@PostMapping("/addContact")
	public String showContactList(@RequestParam(name = "first_name") String fname,
			@RequestParam(name = "last_name") String lname, @RequestParam(name = "phone1") String phone1,
			@RequestParam(name = "phone2") String phone2, @RequestParam(name = "address") String address,
			@RequestParam(name = "email_professional") String email_professional,
			@RequestParam(name = "email_personal") String email_personal, @RequestParam(name = "gender") String gender,
			Model model, Principal principal) {

		MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();

		Contact c = new Contact(fname, lname, phone1, phone2, address, email_professional, email_personal, gender,
				userDetails.getUser());

		contactServiceImpl.addContact(c);
		
		Set<Contact> contacts = contactServiceImpl.findByUserId(userDetails.getUser());

		model.addAttribute("contacts", contacts);
		
		for(Contact ca: contacts) {
			if (ca.getLast_name() == c.getLast_name()) {
				Groupe group = new Groupe(ca.getLast_name(), userDetails.getUser());
				
				groupServiceImpl.addGroup(group);
				
				ContactGroup cg1 = new ContactGroup();
	        	cg1.setContact(c);
	        	cg1.setGroupe(group);
	        	cg1.setUser(userDetails.getUser());
	        	contactGroupRepository.save(cg1);
	        	ContactGroup cg2 = new ContactGroup();
	        	cg2.setContact(ca);
	        	cg2.setGroupe(group);
	        	cg2.setUser(userDetails.getUser());
	        	contactGroupRepository.save(cg2);
	        	break;
			}
		}

		return "redirect:/add";
	}
	
	@PostMapping("/updateContact")
	public String updateContact(@RequestParam(name="contactidtoupdate") String contactId, @ModelAttribute("contactToUpdate") Contact contact,
			Model model) {
		System.out.println("Contacteded: "+contactId);
		Contact c = contactRepository.findByid(Long.parseLong(contactId));
		c.setAddress(contact.getAddress());
		c.setEmail_personal(contact.getEmail_personal());
		c.setEmail_professional(contact.getEmail_professional());
		c.setFirst_name(contact.getFirst_name());
		c.setGender(contact.getGender());
		c.setLast_name(contact.getLast_name());
		c.setPhone1(contact.getPhone1());
		c.setPhone2(contact.getPhone2());
		
		contactRepository.flush();

		return "redirect:/update";
	}
	
	@GetMapping("/restore")
	public String restore(@RequestParam(name = "trashId") String trashId, Model model) {
		Trash t = trashRepository.findByid(Long.parseLong(trashId));

		Contact c = new Contact();
		c.setAddress(t.getAddress());
		c.setEmail_personal(t.getEmail_personal());
		c.setEmail_professional(t.getEmail_professional());
		c.setFirst_name(t.getFirst_name());
		c.setGender(t.getGender());
		c.setLast_name(t.getLastName());
		c.setPhone1(t.getPhone1());
		c.setPhone2(t.getPhone2());
		c.setUserId(userRepository.findByid(t.getUserId()));

		contactRepository.save(c);
		trashRepository.delete(t);
		contactRepository.flush();
		trashRepository.flush();

		return "redirect:/delete";
	}
	
}
