package com.student.manager.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.security.access.prepost.PreAuthorize;

import com.student.manager.model.Student;

/**
 * @author Veera Marisetty
 */
public interface StudentRepository extends PagingAndSortingRepository<Student, Long> {

	@Override
	Student save(@Param("student") Student student);

	@Override
	void delete(@Param("id") Long id);

	@Override
	void delete(@Param("student") Student student);

}
