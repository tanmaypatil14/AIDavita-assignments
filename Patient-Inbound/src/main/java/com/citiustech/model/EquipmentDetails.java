package com.citiustech.model;

public class EquipmentDetails {

	private int equipmentId;
	private String equipmentName;
	private int quantity;
	private String orderDateTime;

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

	public int getQuantity() {
		return quantity;
	}

	public String getOrderDateTime() {
		return orderDateTime;
	}

	@Override
	public String toString() {
		return "EquipmentDetails [equipmentId=" + equipmentId + ", equipmentName=" + equipmentName + ", quantity="
				+ quantity + ", orderDateTime=" + orderDateTime + "]";
	}

}
