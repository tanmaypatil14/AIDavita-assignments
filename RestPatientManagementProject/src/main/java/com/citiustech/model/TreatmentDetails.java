package com.citiustech.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class TreatmentDetails {

	@Id
	private int treatmentId;
	private String treatmentName;
	private String treatmentStartDateTime;
	private String treatmentEndDateTime;

	@ManyToOne
	@JoinColumn(name = "nurseId")
	private NurseDetails nurseDetails;
//	private List<NurseDetails> nurseDetails = new ArrayList<NurseDetails>();

	@ManyToOne
	@JoinColumn(name = "diagnosisId")
	private DiagnosisDetails diagnosisDetails;
//	private List<DiagnosisDetails> diagnosisDetails = new ArrayList<>();

	@ManyToOne
	@JoinColumn(name = "equipmentId")
	private EquipmentDetails equipmentDetails;
//	private List<EquipmentDetails> equipmentDetails = new ArrayList<EquipmentDetails>();

	@ManyToOne
	@JoinColumn(name = "patientId")
	private PatientDemographicDetails patientDemographicDetails;
//	private List<PatientDemographicDetails> patientDemographicDetails = new ArrayList<PatientDemographicDetails>();

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
