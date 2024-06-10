package com.citiustech.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.citiustech.exception.TreatmentDetailsNotFoundException;
import com.citiustech.model.PatientDemographicDetails;
import com.citiustech.model.TreatmentDetails;
import com.citiustech.service.TreatmentDetailsService;

@RestController
@RequestMapping(path = "/treatment")
public class HospitalController {

	@Autowired
	private TreatmentDetailsService detailsService;

	@GetMapping(path = "/patient")
	public PatientDemographicDetails getPatientById(@RequestParam int patientId) {
		System.out.println(patientId);

		PatientDemographicDetails patientById = detailsService.getPatientById(patientId);
		return patientById;
	}

	@GetMapping(path = "/{patientId}")
	public ResponseEntity<?> getTreatmentById(@PathVariable("patientId") int patientId) {
		System.out.println(patientId);

		List<TreatmentDetails> treatmentById = detailsService.getTreatmentById(patientId);

		if (!treatmentById.isEmpty() && treatmentById.size() > 0) {

			return new ResponseEntity<List<TreatmentDetails>>(treatmentById, HttpStatus.OK);

		} else {

			String message = "Treatment details does not exist for patient with id : " + patientId;

			return new ResponseEntity<String>(message, HttpStatus.NOT_FOUND);
		}

	}

}
