package com.student.manager.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name="STAFF_COURSE")
public class StaffCourse {

	private @Id @GeneratedValue Long id;
	
	@ManyToOne
	private Staff staff;
	
	@ManyToOne
	private Course course;
}
