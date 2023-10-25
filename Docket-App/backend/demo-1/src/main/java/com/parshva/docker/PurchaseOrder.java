package com.parshva.docker;

public class PurchaseOrder {
	
	private String pONum;
	private String pODesc;
	public String getpONum() {
		return pONum;
	}
	public void setpONum(String pONum) {
		this.pONum = pONum;
	}
	@Override
	public String toString() {
		return "PurchaseOrder [pONum=" + pONum + ", pODesc=" + pODesc + "]";
	}
	public String getpODesc() {
		return pODesc;
	}
	public void setpODesc(String pODesc) {
		this.pODesc = pODesc;
	}
	
	

}
