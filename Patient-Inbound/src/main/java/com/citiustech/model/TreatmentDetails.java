package com.citiustech.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TreatmentDetails {

	@JsonProperty
	private int treatmentId;
	@JsonProperty
	private String treatmentName;
	@JsonProperty
	private String treatmentStartDateTime;
	@JsonProperty
	private String treatmentEndDateTime;

	@JsonProperty
	private NurseDetails nurseDetails;

	@JsonProperty
	private DiagnosisDetails diagnosisDetails;

	@JsonProperty
	private EquipmentDetails equipmentDetails;

	@JsonProperty
	private PatientDemographicDetails patientDemographicDetails;

	public TreatmentDetails() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int getTreatmentId() {
		return treatmentId;
	}

	public String getTreatmentName() {
		return treatmentName;
	}

	public String getTreatmentStartDateTime() {
		return treatmentStartDateTime;
	}

	public String getTreatmentEndDateTime() {
		return treatmentEndDateTime;
	}

	public NurseDetails getNurseDetails() {
		return nurseDetails;
	}

	public DiagnosisDetails getDiagnosisDetails() {
		return diagnosisDetails;
	}

	public EquipmentDetails getEquipmentDetails() {
		return equipmentDetails;
	}

	public PatientDemographicDetails getPatientDemographicDetails() {
		return patientDemographicDetails;
	}

	@Override
	public String toString() {
		return "TreatmentDetails [treatmentId=" + treatmentId + ", treatmentName=" + treatmentName
				+ ", treatmentStartDateTime=" + treatmentStartDateTime + ", treatmentEndDateTime="
				+ treatmentEndDateTime + ", nurseDetails=" + nurseDetails + ", diagnosisDetails=" + diagnosisDetails
				+ ", equipmentDetails=" + equipmentDetails + ", patientDemographicDetails=" + patientDemographicDetails
				+ "]";
	}

}
