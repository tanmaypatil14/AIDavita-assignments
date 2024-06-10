package com.citiustech.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.citiustech.model.EquipmentDetails;

public interface EquipmentDetailsRepo extends JpaRepository<EquipmentDetails, Integer> {

}
