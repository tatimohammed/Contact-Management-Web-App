package com.app.ContactManagement.model;

import java.util.List;

import javax.persistence.*;

@Entity
public class Groupe {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String name;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
	
	@OneToMany(mappedBy = "groupe")
	private List<ContactGroup> groupes;
	
	 

	public Groupe() {
		
	}

	public Groupe(String name, User user) {
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
