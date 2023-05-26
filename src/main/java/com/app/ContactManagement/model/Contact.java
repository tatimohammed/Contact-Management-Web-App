package com.app.ContactManagement.model;

import javax.persistence.*;


@Entity(name = "Contact")
public class Contact {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(updatable = false, insertable = false)
	private Long id;

	private String first_name;

	private String lastName;

	private String phone1;

	private String phone2;

	private String address;

	private String email_professional;

	private String email_personal;

	private String gender;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User userId;
	
	@ManyToOne
	@JoinColumn(name = "group_id")
	private ContactGroup groupId;
	
	

	public User getUserId() {
		return userId;
	}

	public void setUserId(User userId) {
		this.userId = userId;
	}

	public Contact() {

	}

	public Contact(String first_name, String lastName, String phone1, String phone2, String address,
			String email_professional, String email_personal, String gender, User userId) {
		super();
		this.first_name = first_name;
		this.lastName = lastName;
		this.phone1 = phone1;
		this.phone2 = phone2;
		this.address = address;
		this.email_professional = email_professional;
		this.email_personal = email_personal;
		this.gender = gender;
		this.userId = userId;
	}

	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}

	public String getFirst_name() {
		return first_name;
	}

	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	public String getLast_name() {
		return lastName;
	}

	public void setLast_name(String last_name) {
		this.lastName = last_name;
	}

	public String getPhone1() {
		return phone1;
	}

	public void setPhone1(String phone1) {
		this.phone1 = phone1;
	}

	public ContactGroup getGroupId() {
		return groupId;
	}

	public void setGroupId(ContactGroup groupId) {
		this.groupId = groupId;
	}

	public String getPhone2() {
		return phone2;
	}

	public void setPhone2(String phone2) {
		this.phone2 = phone2;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmail_professional() {
		return email_professional;
	}

	public void setEmail_professional(String email_professional) {
		this.email_professional = email_professional;
	}

	public String getEmail_personal() {
		return email_personal;
	}

	public void setEmail_personal(String email_personal) {
		this.email_personal = email_personal;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}
}
