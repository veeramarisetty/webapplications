package com.student.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.student.manager.model.Student;
import com.student.manager.model.Users;
import com.student.manager.repository.UserRepository;

/**
 * @author Veera Marisetty.
 */
@Component
@RepositoryEventHandler(Student.class)
public class SpringDataRestEventHandler {

	private final UserRepository userRepository;

	@Autowired
	public SpringDataRestEventHandler(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@HandleBeforeCreate
	public void applyUserInformationUsingSecurityContext(Student student) {

		String name = SecurityContextHolder.getContext().getAuthentication().getName();
		Users user = this.userRepository.findByName(name);
		if (user == null) {
			Users newUser = new Users();
			newUser.setName(name);
			newUser.setRoles(new String[]{"ROLE_MANAGER"});
			user = this.userRepository.save(newUser);
		}
	}
}
