package com.citiustech.model;

public class DiagnosisDetails {

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
