package com.app.ContactManagement.model;

import java.util.List;

import javax.persistence.*;

@Entity
public class ContactGroup {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String name;
	
	@OneToMany(mappedBy = "groupId")
	private List<Contact> contacts;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
	
	
	
	

	public ContactGroup() {
		
	}

	public ContactGroup(String name, User user) {
		super();
		this.name = name;
		this.user = user;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	

	
	
	
	
	

}
