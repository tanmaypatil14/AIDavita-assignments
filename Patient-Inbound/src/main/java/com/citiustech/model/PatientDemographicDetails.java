package com.citiustech.model;


public class PatientDemographicDetails {

	private int patientId;
	private String firstName;
	private String lastName;
	private String dob;
	private int age;
	private String gender;
	private String contactNumber;
	private String role;

	public PatientDemographicDetails() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int getPatientId() {
		return patientId;
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
		return "PatientDemographicDetails [patientId=" + patientId + ", firstName=" + firstName + ", lastName="
				+ lastName + ", dob=" + dob + ", age=" + age + ", gender=" + gender + ", contactNumber=" + contactNumber
				+ ", role=" + role + "]";
	}

}
