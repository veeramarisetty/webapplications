package com.student.manager.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name="STUDENT_COURSE")
public class StudentCourse {

	private @Id @GeneratedValue Long id;
	
	@ManyToOne
	private Student student;
	
	@ManyToOne
	private Course course;
	
	private int marksScored;
	
	private String grade;
	
	public StudentCourse() {
		
	}

	@Override
	public String toString() {
		return "StudentCourse [student=" + student.getFirstName() + ", course=" + course.getName() + ", marksScored=" + marksScored + ", grade="
				+ grade + "]";
	}
	
	
}
