package com.student.manager.model;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.student.manager.common.Department;

import lombok.Data;

/**
 * @author Veera Marisetty
 */
@Data
@Entity
@Table(name="STAFF")
public class Staff {

	private @Id @GeneratedValue Long id;
	private String firstName;
	private String lastName;
	private Department department;
	private Date joinDate;
	private @Version @JsonIgnore Long version;

	private Staff() {}

}
