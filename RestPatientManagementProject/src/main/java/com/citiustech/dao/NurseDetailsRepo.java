package com.citiustech.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.citiustech.model.NurseDetails;

public interface NurseDetailsRepo extends JpaRepository<NurseDetails, Integer> {

}
