package com.student.manager.controller;

import static com.student.manager.controller.WebSocketConfiguration.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.HandleAfterCreate;
import org.springframework.data.rest.core.annotation.HandleAfterDelete;
import org.springframework.data.rest.core.annotation.HandleAfterSave;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.hateoas.EntityLinks;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import com.student.manager.model.Student;
import com.student.manager.model.StudentCourse;

/**
 * @author Veera Marisetty.
 */
@Component
@RepositoryEventHandler(StudentCourse.class)
public class StudentCourseEventHandler {

	private final SimpMessagingTemplate websocket;

	private final EntityLinks entityLinks;

	@Autowired
	public StudentCourseEventHandler(SimpMessagingTemplate websocket, EntityLinks entityLinks) {
		this.websocket = websocket;
		this.entityLinks = entityLinks;
	}

	@HandleAfterCreate
	public void newStudentCourse(StudentCourse studentCourse) {
		this.websocket.convertAndSend(
				MESSAGE_PREFIX + "/newStudentCourse", getPath(studentCourse));
	}

	@HandleAfterDelete
	public void deleteStudentCourse(StudentCourse studentCourse) {
		this.websocket.convertAndSend(
				MESSAGE_PREFIX + "/deleteStudentCourse", getPath(studentCourse));
	}

	@HandleAfterSave
	public void updateStudentCourse(StudentCourse studentCourse) {
		this.websocket.convertAndSend(
				MESSAGE_PREFIX + "/updateStudentCourse", getPath(studentCourse));
	}

	/**
	 * Take an {@link StudentCourse} and get the URI using Spring Data REST's {@link EntityLinks}.
	 *
	 * @param student
	 */
	private String getPath(StudentCourse studentCourse) {
		return this.entityLinks.linkForSingleResource(studentCourse.getClass(),
				studentCourse.getId()).toUri().getPath();
	}

}
