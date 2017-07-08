package com.student.manager;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.student.manager.common.CourseType;
import com.student.manager.common.Department;
import com.student.manager.common.Gender;
import com.student.manager.common.Semester;
import com.student.manager.common.StudentYear;
import com.student.manager.common.UserType;
import com.student.manager.model.Course;
import com.student.manager.model.Student;
import com.student.manager.model.StudentCourse;
import com.student.manager.model.Users;
import com.student.manager.repository.CourseRepository;
import com.student.manager.repository.StudentRepository;
import com.student.manager.repository.UserRepository;

/**
 * @author Veera Marisetty.
 */
@Component
public class DatabaseLoader implements CommandLineRunner {

	private final StudentRepository students;
	private final UserRepository users;
	private final CourseRepository courses;

	@Autowired
	public DatabaseLoader(StudentRepository studentRepository,
			UserRepository userRepository, CourseRepository courseRepository) {

		this.students = studentRepository;
		this.users = userRepository;
		this.courses = courseRepository;
	}

	@Override
	public void run(String... strings) throws Exception {
		
		createUsers();
		
		SecurityContextHolder.getContext().setAuthentication(
			new UsernamePasswordAuthenticationToken("rama", "pass",
				AuthorityUtils.createAuthorityList("ROLE_MANAGER")));

		SecurityContextHolder.getContext().setAuthentication(
			new UsernamePasswordAuthenticationToken("ravi", "pass",
				AuthorityUtils.createAuthorityList("ROLE_MANAGER")));

		SecurityContextHolder.getContext().setAuthentication(
				new UsernamePasswordAuthenticationToken("raju", "pass",
					AuthorityUtils.createAuthorityList("ROLE_STUDENT")));
		
		SecurityContextHolder.clearContext();
		
		createStudents();
	}

	private void createStudents() {

		Course c1 = new Course("OS", "Operation Systems", StudentYear.THREE,
				Semester.ONE, true, CourseType.CLASSROOM, 100, 40, true, Department.CSE);
		
		Course c2 = new Course("C", "C Programming", StudentYear.THREE,
				Semester.ONE, true, CourseType.CLASSROOM, 100, 40, true, Department.CSE);
	
		this.courses.save(c1);
		this.courses.save(c2);
		
//		StudentCourse sc1s1 = new StudentCourse();
//		sc1s1.setCourse(c1);
//		StudentCourse sc2s1 = new StudentCourse();
//		sc2s1.setCourse(c2);
		
		Student s1 = new Student("raju", "babu", Gender.MALE,
				"raju@bm.org", StudentYear.THREE, Semester.ONE, Department.CSE, 
				Date.valueOf(LocalDate.of(2013, 06, 1)), 2017);
		
//		sc1s1.setStudent(s1);
//		sc2s1.setStudent(s1);
//		Collection<StudentCourse> scl = new ArrayList<>();
//		scl.add(sc1s1);
//		scl.add(sc2s1);
//		s1.setCurrentCourses(scl);
		this.students.save(s1);

		Student s2 = new Student("hari", "babu", Gender.MALE,
				"hari@bm.org", StudentYear.THREE, Semester.ONE, Department.CSE, 
				Date.valueOf(LocalDate.of(2013, 06, 2)), 2017);
//		s2.setCurrentCourses(scl);
		this.students.save(s2);
//		
		Student s3 = new Student("satish", "babu", Gender.MALE,
				"satish@bm.org", StudentYear.THREE, Semester.ONE, Department.CSE, 
				Date.valueOf(LocalDate.of(2013, 06, 4)), 2017);
//		s3.setCurrentCourses(scl);
		this.students.save(s3);
//		
		Student s4 = new Student("naresh", "babu", Gender.MALE, 
				"naresh@bm.org", StudentYear.THREE, Semester.ONE, Department.CSE, 
				Date.valueOf(LocalDate.of(2013, 06, 3)), 2017);
//		s4.setCurrentCourses(scl);
		this.students.save(s4);
	}

	private void createUsers() {
		this.users.save(new Users("rama", "pass", UserType.STAFF, "ROLE_MANAGER"));
		this.users.save(new Users("ravi", "pass", UserType.STAFF, "ROLE_MANAGER"));
		this.users.save(new Users("naresh", "pass", UserType.STUDENT, "ROLE_STUDENT"));
		this.users.save(new Users("satish", "pass", UserType.STUDENT, "ROLE_STUDENT"));
		this.users.save(new Users("hari", "pass", UserType.STUDENT, "ROLE_STUDENT"));
		this.users.save(new Users("raju", "pass", UserType.STUDENT, "ROLE_STUDENT"));

	}
}