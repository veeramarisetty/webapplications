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

import com.student.manager.model.Course;

/**
 * @author Veera Marisetty.
 */
@Component
@RepositoryEventHandler(Course.class)
public class CourseEventHandler {

	private final SimpMessagingTemplate websocket;

	private final EntityLinks entityLinks;

	@Autowired
	public CourseEventHandler(SimpMessagingTemplate websocket, EntityLinks entityLinks) {
		this.websocket = websocket;
		this.entityLinks = entityLinks;
	}

	@HandleAfterCreate
	public void newCourse(Course course) {
		this.websocket.convertAndSend(
				MESSAGE_PREFIX + "/newCourse", getPath(course));
	}

	@HandleAfterDelete
	public void deleteCourse(Course course) {
		this.websocket.convertAndSend(
				MESSAGE_PREFIX + "/deleteCourse", getPath(course));
	}

	@HandleAfterSave
	public void updateCourse(Course course) {
		this.websocket.convertAndSend(
				MESSAGE_PREFIX + "/updateCourse", getPath(course));
	}

	/**
	 * Take an {@link Student} and get the URI using Spring Data REST's {@link EntityLinks}.
	 *
	 * @param course
	 */
	private String getPath(Course course) {
		return this.entityLinks.linkForSingleResource(course.getClass(), course.getId()).toUri().getPath();
	}

}
