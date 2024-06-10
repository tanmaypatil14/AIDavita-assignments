package com.citiustech.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class EquipmentDetails {

	@Id
	private int equipmentId;
	private String equipmentName;
	private boolean inUsed;
	private String allocationDateTime;

	public EquipmentDetails() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int getEquipmentId() {
		return equipmentId;
	}

	public String getEquipmentName() {
		return equipmentName;
	}

	public String getAllocationDateTime() {
		return allocationDateTime;
	}

	public boolean isInUsed() {
		return inUsed;
	}

	@Override
	public String toString() {
		return "EquipmentDetails [equipmentId=" + equipmentId + ", equipmentName=" + equipmentName + ", inUsed="
				+ inUsed + ", orderDateTime=" + allocationDateTime + "]";
	}

}
