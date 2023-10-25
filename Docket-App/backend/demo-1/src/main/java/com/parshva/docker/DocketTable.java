package com.parshva.docker;

import java.sql.Time;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="docket_info")
public class DocketTable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="Name")
	private String name;
	
	@Column(name="Start Time")
	private Time startTime;
	
	@Column(name="End Time")
	private Time endTime;
	
	@Column(name="Working Hours")
	private int workingHours;
	
	@Column(name="Rate per hour")
	private int ratePerHour;
	
	@Column(name="Supplier Name")
	private String supplierName;
	
	@Column(name="Purchase Order Number")
	private String poNumber;
	
	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Time getStartTime() {
		return startTime;
	}

	public void setStartTime(Time startTime) {
		this.startTime = startTime;
	}

	public Time getEndTime() {
		return endTime;
	}

	public void setEndTime(Time endTime) {
		this.endTime = endTime;
	}

	public int getWorkingHours() {
		return workingHours;
	}

	public void setWorkingHours(int workingHours) {
		this.workingHours = workingHours;
	}

	public int getRatePerHour() {
		return ratePerHour;
	}

	public void setRatePerHour(int ratePerHour) {
		this.ratePerHour = ratePerHour;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public String getPoNumber() {
		return poNumber;
	}

	public void setPoNumber(String poNumber) {
		this.poNumber = poNumber;
	}
	
	
	@Override
	public String toString() {
		return "Docket Table : [ Name : " +name+ " , Start Time : " +startTime+ 
				" , End Time : " +endTime+ " , Number of Working Hours : " +workingHours+
				" , Rate per hour : " +ratePerHour+ " , Supplier Name : " +supplierName+
				" , Purchase Order Number : " +poNumber+ " ]";
	}

}
