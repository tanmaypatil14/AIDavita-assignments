package com.citiustech.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.citiustech.model.TreatmentDetails;

public interface TreatmentDetailsRepo extends JpaRepository<TreatmentDetails, Integer> {
	
	@Query("SELECT t FROM TreatmentDetails t WHERE t.patientDemographicDetails.patientId = :id")
	public List<TreatmentDetails> treatmentByPatientId(@Param("id") int id);


}
