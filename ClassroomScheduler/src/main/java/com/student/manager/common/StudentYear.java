package com.student.manager.common;

public enum StudentYear {

	ONE("I"), TWO("II"), THREE("III"), FOUR("IV");
	
	private String yearNum;
	
	StudentYear(String semesterValue) {
		this.yearNum = yearNum;
	}
	
	public String getYear() {
		return yearNum;
	}
}
