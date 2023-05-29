package com.app.ContactManagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.app.ContactManagement.model.Trash;
import com.app.ContactManagement.repository.TrashRepository;
import com.app.ContactManagement.service.MyUserDetails;

@Controller
public class TrashController {
	
	@Autowired
	private TrashRepository trashRepository;
	
	
	@GetMapping("/emptyTrash")
	public String emptyTrash() {
		
		MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		List<Trash> trashs = trashRepository.findByuserId(userDetails.getUser().getId());
		
		for(Trash t: trashs) {
			trashRepository.delete(t);
		}
		
		return "redirect:/delete";
	}
}
