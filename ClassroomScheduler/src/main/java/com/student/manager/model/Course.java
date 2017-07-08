package com.student.manager.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.student.manager.common.CourseType;
import com.student.manager.common.Department;
import com.student.manager.common.ItemType;
import com.student.manager.common.Semester;
import com.student.manager.common.StudentYear;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Entity
@Table(name="COURSE")
public class Course {
	
	@Id
	@Column(name="id") @GeneratedValue private Long id;
	@NotNull
	private StudentYear year;
	@NotNull
	private Semester semester;
	@NotNull
	private String name;
	@NotNull
	private String description;
	
	private boolean active;
	@NotNull
	private CourseType courseType;
	@NotNull
	private int maximumMarks;
	@NotNull
	private int minimumPassMarks;

	private boolean manadatory;
	
	@NotNull
	private Department department;
	
	private Course(){
		
	}

	public Course(String name, String description, StudentYear year, Semester semester, boolean active,
			CourseType courseType, int maximumMarks, int minimumPassMarks, boolean manadatory, Department department) {
		super();
		this.year = year;
		this.semester = semester;
		this.name = name;
		this.description = description;
		this.active = active;
		this.courseType = courseType;
		this.maximumMarks = maximumMarks;
		this.minimumPassMarks = minimumPassMarks;
		this.manadatory = manadatory;
		this.department = department;
	}

	@Override
	public String toString() {
		return "Course [year=" + year + ", semester=" + semester + ", name=" + name + ", description=" + description
				+ ", active=" + active + ", courseType=" + courseType + ", maximumMarks=" + maximumMarks
				+ ", minimumPassMarks=" + minimumPassMarks + ", manadatory=" + manadatory + ", department=" + department
				+ "]";
	}
}
