package com.student.manager.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.ToString;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.student.manager.common.UserType;

/**
 * @author Veera Marisetty
 */
@Data
@ToString(exclude = "password")
@Entity
@Table(name="USER")
public class Users {

	public static final PasswordEncoder PASSWORD_ENCODER = new BCryptPasswordEncoder();

	private @Id @GeneratedValue Long id;

	private String name;

	private @JsonIgnore String password;

	private String[] roles;
	
	private UserType userType;

	public void setPassword(String password) {
		this.password = PASSWORD_ENCODER.encode(password);
	}

	public Users() {}

	public Users(String name, String password, UserType userType, String... roles) {

		this.name = name;
		this.setPassword(password);
		this.userType = userType;
		this.roles = roles;
	}

}
