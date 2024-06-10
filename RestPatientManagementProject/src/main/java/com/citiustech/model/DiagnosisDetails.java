package com.citiustech.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class DiagnosisDetails {

	@Id
	@Column(name = "diagnosis_id")
	private int diagnosisId;
	private String diagnosis;
	private String description;

	public DiagnosisDetails() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int getDiagnosisId() {
		return diagnosisId;
	}

	public String getDiagnosis() {
		return diagnosis;
	}

	public String getDescription() {
		return description;
	}

	@Override
	public String toString() {
		return "DiagnosisDetails [diagnosisId=" + diagnosisId + ", diagnosis=" + diagnosis + ", description="
				+ description + "]";
	}

}
