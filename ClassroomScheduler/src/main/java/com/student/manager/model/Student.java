package com.student.manager.model;

import java.sql.Date;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Version;

import lombok.Data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.student.manager.common.Department;
import com.student.manager.common.Gender;
import com.student.manager.common.Semester;
import com.student.manager.common.StudentYear;

/**
 * @author Veera Marisetty
 */
@Data
@Entity
@Table(name="STUDENT")
public class Student {

	private @Id @GeneratedValue Long id;
	private String firstName;
	private String lastName;
	private Gender gender;
	private String email;
	private StudentYear currentYear;
	private Semester currentSemester;
	private Department department;
	private Date joinDate;
	private int graduationYear;
	
	private @Version @JsonIgnore Long version;
	
	private Student() {}

	public Student(String firstName, String lastName, Gender gender, String email, StudentYear currentYear,
			Semester currentSemester, Department department, Date joinDate, int graduationYear) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.gender = gender;
		this.email = email;
		this.currentYear = currentYear;
		this.currentSemester = currentSemester;
		this.department = department;
		this.joinDate = joinDate;
		this.graduationYear = graduationYear;
	}

	@Override
	public String toString() {
		return "Student firstName=" + firstName + ", lastName=" + lastName + ", gender=" + gender
				+ ", email=" + email + ", currentYear=" + currentYear + ", currentSemester=" + currentSemester
				+ ", department=" + department + ", joinDate=" + joinDate + ", graduationYear=" + graduationYear
				+ ", version=" + version + "]";
	}

	
}