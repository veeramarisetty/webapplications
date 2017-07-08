package com.student.manager.common;

public enum Semester {
	
	ONE(1), TWO(2);
	
	private int semesterValue;
	
	Semester(int semesterValue) {
		this.semesterValue = semesterValue;
	}
	
	public int getSemester() {
		return semesterValue;
	}

}
