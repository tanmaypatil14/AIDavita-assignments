package com.citiustech.model;

import java.io.Serializable;

public class Patient implements Serializable {

	private static final long serialVersionUID = -548650746559484171L;

	private int patientId;
	private String firstName;
	private String lastName;
	private int age;
	private String gender;

	public Patient() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Patient(int patientId, String firstName, String lastName, int age, String gender) {
		super();
		this.patientId = patientId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.age = age;
		this.gender = gender;
	}

	public int getPatientId() {
		return patientId;
	}

	public void setPatientId(int patientId) {
		this.patientId = patientId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	@Override
	public String toString() {
		return "Patient [patientId=" + patientId + ", firstName=" + firstName + ", lastName=" + lastName + ", age="
				+ age + ", gender=" + gender + "]";
	}

}
