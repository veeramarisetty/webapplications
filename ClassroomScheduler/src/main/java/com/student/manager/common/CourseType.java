package com.student.manager.common;

public enum CourseType {
	
	CLASSROOM(1), LAB(2);
	
	private int courseValue;
	
	CourseType(int courseValue) {
		this.courseValue = courseValue;
	}
	
	public int getCourseValue() {
		return courseValue;
	}

}
