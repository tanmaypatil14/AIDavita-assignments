package com.citiustech.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class NurseDetails {

	@Id
	@Column(name = "nurse_id")
	private int nurseId;
	@Column(name = "first_name")
	private String firstName;
	@Column(name = "last_name")
	private String lastName;
	private String dob;
	private int age;
	private String gender;
	@Column(name = "contact_number")
	private String contactNumber;
	private String role;

	public NurseDetails() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int getNurseId() {
		return nurseId;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getDob() {
		return dob;
	}

	public int getAge() {
		return age;
	}

	public String getGender() {
		return gender;
	}

	public String getContactNumber() {
		return contactNumber;
	}

	public String getRole() {
		return role;
	}

	@Override
	public String toString() {
		return "NurseDetails [nurseId=" + nurseId + ", firstName=" + firstName + ", lastName=" + lastName + ", dob="
				+ dob + ", age=" + age + ", gender=" + gender + ", contactNumber=" + contactNumber + ", role=" + role
				+ "]";
	}

}
