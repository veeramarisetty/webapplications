package com.student.manager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * @author Veera Marisetty.
 */
@SpringBootApplication
@Configuration
@EnableJpaRepositories
@EnableAutoConfiguration
@ComponentScan({"com.student.manager.model", "com.student.manager.repository",
	"com.student.manager.service", "com.student.manager.controller",
	"com.student.manager"})
public class StudentManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(StudentManagerApplication.class, args);
	}
}
// end::code[]