package com.citiustech.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.citiustech.dao.PatientDetailsRepo;
import com.citiustech.dao.TreatmentDetailsRepo;
import com.citiustech.exception.TreatmentDetailsNotFoundException;
import com.citiustech.model.PatientDemographicDetails;
import com.citiustech.model.TreatmentDetails;

@Service
public class TreatmentDetailsServiceImpl implements TreatmentDetailsService {

	@Autowired
	private PatientDetailsRepo detailsRepo;

	@Autowired
	private TreatmentDetailsRepo treatmentRepo;

	@Override
	public PatientDemographicDetails getPatientById(int patientId) {

		System.out.println(patientId);

		Optional<PatientDemographicDetails> byPatientId = detailsRepo.findById(patientId);
		System.out.println(byPatientId.get());

		// TODO Auto-generated method stub
		return byPatientId.get();
	}

	@Override
	public List<TreatmentDetails> getTreatmentById(int patientId) {

		List<TreatmentDetails> treatmentById = Optional.ofNullable(treatmentRepo.treatmentByPatientId(patientId)).get();
		System.out.println(treatmentById);

		return treatmentById;
	}

}
