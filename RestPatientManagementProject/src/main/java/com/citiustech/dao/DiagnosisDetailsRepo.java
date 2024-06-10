package com.citiustech.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.citiustech.model.DiagnosisDetails;

public interface DiagnosisDetailsRepo extends JpaRepository<DiagnosisDetails, Integer> {

}
