package com.citiustech.service;

import java.util.List;

import com.citiustech.exception.TreatmentDetailsNotFoundException;
import com.citiustech.model.PatientDemographicDetails;
import com.citiustech.model.TreatmentDetails;

public interface TreatmentDetailsService {
	
	public PatientDemographicDetails getPatientById(int patientId);

	public List<TreatmentDetails> getTreatmentById(int patientId);

}
