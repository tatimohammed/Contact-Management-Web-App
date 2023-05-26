package com.app.ContactManagement.model;

import java.util.List;

import javax.persistence.*;

@Entity
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(updatable = false, insertable = false)
	private Long id;

	private String name;

	private String username;

	private String password;
	
	@OneToMany(mappedBy = "userId")
	private List<Contact> contacts;
	
	@OneToMany(mappedBy = "user")
	private List<ContactGroup> userGroups;
	
	@OneToMany(mappedBy = "user")
	private List<LoginCounter> logins;

	public User() {

	}

	public User(String name, String username, String password) {
		super();
		this.name = name;
		this.username = username;
		this.password = password;
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

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "User{" + "name='" + name + '\'' + ", login='" + username + '\'' + ", password=" + password + '}';
	}
}
